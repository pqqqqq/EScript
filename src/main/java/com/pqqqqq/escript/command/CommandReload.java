package com.pqqqqq.escript.command;

import com.pqqqqq.escript.lang.Main;
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
 * Command to reload scripts
 */
public class CommandReload implements CommandExecutor {
    private static final CommandReload INSTANCE = new CommandReload();

    private CommandReload() {
    }

    public static CommandSpec build() {
        return CommandSpec.builder().executor(INSTANCE).description(Text.of(TextColors.AQUA, "Reloads the config and scripts.")).permission("escript.reload").build();
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        long time = System.currentTimeMillis();
        Main.instance().init(); // Reinitialize main

        commandSource.sendMessage(Text.of(TextColors.AQUA, "Scripts reloaded in: " + (System.currentTimeMillis() - time) + "ms"));
        return CommandResult.success();
    }
}
