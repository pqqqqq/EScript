package com.pqqqqq.escript.command;

import com.pqqqqq.escript.EScript;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by Kevin on 2016-09-10.
 * The main EScript command class
 */
public class CommandEScript implements CommandExecutor {
    private static final CommandEScript INSTANCE = new CommandEScript();

    private CommandEScript() {
    }

    public static CommandSpec build() {
        return CommandSpec.builder().executor(INSTANCE).description(Text.of(TextColors.AQUA, "Main plugin command"))
                .child(CommandReload.build(), "reload").build();
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        commandSource.sendMessage(Text.of(TextColors.GREEN, EScript.NAME, TextColors.WHITE, " V", EScript.VERSION, TextColors.GREEN, " created by: ", TextColors.WHITE, EScript.AUTHORS));
        commandSource.sendMessage(Text.of(TextColors.RED, "/script <reload>"));
        return CommandResult.success();
    }
}
