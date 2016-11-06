package com.pqqqqq.escript.lang.phrase.phrases.trigger.command;

import com.pqqqqq.escript.lang.data.Datum;
import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.script.Script;
import com.pqqqqq.escript.lang.trigger.Trigger;
import com.pqqqqq.escript.lang.trigger.cause.Causes;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.event.Cancellable;

import java.util.*;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The command trigger phrase, which fires when a specific command is performed.
 * Some ways of doing this:
 *
 *     <code>on command "/command":
 *     when command "/command [player] &lt;message&gt; is performed:</code>
 * </pre>
 */
public class CommandTrigger implements Phrase {
    private static final CommandTrigger INSTANCE = new CommandTrigger();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("when|if command $Command is? performed|said|executed*:"),
            Syntax.compile("on command $Command:")
    };

    /**
     * Gets the main command trigger instance
     *
     * @return the instance
     */
    public static CommandTrigger instance() {
        return INSTANCE;
    }

    private CommandTrigger() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Script.State getRunState() {
        return Script.State.COMPILE_TIME; // Run at compile time
    }

    @Override
    public Result execute(Context ctx) {
        // Let's do some parsing here (during compilation)
        String fullCommand = StringUtils.removeStart(ctx.getLiteral("Command").asString(), "/"); // Need to remove slash
        int firstSpace = fullCommand.indexOf(' '); // Gets where to substring

        String command = firstSpace == -1 ? fullCommand : fullCommand.substring(0, firstSpace);
        String argumentsString = firstSpace == -1 ? "" : fullCommand.substring(firstSpace + 1);

        String[] split = argumentsString.split("\\s+"); // Ignore multiple spaces together
        int requiredCount = 0;

        List<CommandArgument> arguments = new ArrayList<>();
        for (String argument : split) {
            CommandArgument arg = CommandArgument.from(argument);
            if (arg.isRequired()) {
                requiredCount++; // Update required count
            }

            arguments.add(arg);
        }

        final int finalRequiredCount = requiredCount; // I have no idea why this is necessary (lambda)

        // Run-time predicate
        Trigger.from(ctx.getLine().getRawScript(), (properties) -> {
            Optional<String> commandTest = properties.getVariable("Command", String.class);
            Optional<String> argumentsTest = properties.getVariable("Arguments", String.class);

            if (!commandTest.isPresent() || !argumentsTest.isPresent()) {
                return false;
            } else {
                if (!command.equalsIgnoreCase(commandTest.get())) { // Check basic command
                    return false;
                }

                String[] splitTest = argumentsTest.get().split("\\s+");
                Deque<String> testQueue = new ArrayDeque<>(Arrays.asList(splitTest)); // Turn split test into a queue

                int optionalCount = 0; // Keep track of optionals, they cant pass the threshold
                int optionalThreshold = testQueue.size() - finalRequiredCount; // The threshold

                Map<String, Datum> variableBus = new HashMap<>();
                for (CommandArgument argument : arguments) {
                    try {
                        if (!argument.isRequired()) { // Optional
                            if (++optionalCount > optionalThreshold) {
                                variableBus.put(argument.getName(), Literal.EMPTY);
                                continue; // No optional argument today
                            }
                        }

                        String value = "";
                        do { // Do-while loop, because it should do at least one iteration
                            value += testQueue.poll() + " ";
                        } while (argument.isStrargs() && !testQueue.isEmpty());

                        variableBus.put(argument.getName(), Literal.fromObject(value.trim())); // Put into variable bus
                    } catch (NoSuchElementException e) { // Thrown by poll when queue is empty
                        return false;
                    }
                }

                properties.getVariableBus().putAll(variableBus); // We're good to deploy our variable bus
                ((Cancellable) properties.getEvent()).setCancelled(true); // Cancel if we've got one
                return true;
            }
        }, Causes.COMMAND);
        return Result.success();
    }
}
