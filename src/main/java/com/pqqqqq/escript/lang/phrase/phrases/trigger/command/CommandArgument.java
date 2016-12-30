package com.pqqqqq.escript.lang.phrase.phrases.trigger.command;

import com.pqqqqq.escript.lang.util.string.StringTransformer;

/**
 * A command argument, which specifies optionality, and whether to continue through the entire rest of the arguments (strargs)
 */
public class CommandArgument {
    private final String name;
    private final boolean required;
    private final boolean strargs;

    public static CommandArgument from(String argument) {
        StringTransformer transformer = StringTransformer.from(argument);
        boolean strarg = transformer.removeEnd("..."); // Check strargs first

        // Try required
        if (transformer.remove("<", ">")) {
            return new CommandArgument(transformer.getCurrentResult(), true, strarg);
        }

        // Try optional
        if (transformer.remove("[", "]")) {
            return new CommandArgument(transformer.getCurrentResult(), false, strarg);
        }

        throw new IllegalArgumentException(String.format("Invalid argument '%s', must be wrapped in <> or [].", argument));
    }

    private CommandArgument(String name, boolean required, boolean strargs) {
        this.name = name;
        this.required = required;
        this.strargs = strargs;
    }

    /**
     * Gets the name of this argument
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets if the argument is required for the command
     *
     * @return true if required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Gets if this argument is a strargs argument.
     * Strarg arguments will "eat up" the rest of the unused argument passed, imploding (joining) them into one single argument string.
     * <p>
     * <p>For this reason, there can only be 1 strarg argument, and it must be the last argument.
     *
     * @return true if a strarg argument
     */
    public boolean isStrargs() {
        return strargs;
    }

    @Override
    public String toString() {
        return name + ": {required=" + required + ", strargs=" + strargs + "}";
    }
}
