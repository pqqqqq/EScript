package com.pqqqqq.escript;

import com.google.inject.Inject;
import com.pqqqqq.escript.command.CommandEScript;
import com.pqqqqq.escript.event.MainEvents;
import com.pqqqqq.escript.lang.Main;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * The main plugin class
 */
@Plugin(id = EScript.ID, name = EScript.NAME, version = EScript.VERSION, authors = EScript.AUTHORS, description = EScript.DESCRIPTION)
public class EScript {
    public static final String ID = "escript";
    public static final String NAME = "EScript";
    public static final String VERSION = "0.0.1-SNAPSHOT";
    public static final String AUTHORS = "Pqqqqq";
    public static final String DESCRIPTION = "An easy scripting plugin for Sponge.";

    @Inject
    private Game game;

    @Inject
    private Logger logger;

    @Inject
    public EScript(Logger logger) {
        this.logger = logger;
    }

    @Listener
    public void init(GameInitializationEvent event) {
        // Initialisation
        Main.instance().init();

        // Register commands
        CommandManager commandService = game.getCommandManager();
        commandService.register(this, CommandEScript.build(), "script", "easyscript", "es", "esc");

        // Register events
        EventManager eventManager = game.getEventManager();
        eventManager.registerListeners(this, new MainEvents());
    }
}
