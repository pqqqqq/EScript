package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player name phrase, which gets the player's health
 * Some examples:
 *      <code>player's name
 *      player name
 *      name of player
 *      player's name</code>
 * </pre>
 */
public class PlayerName implements Phrase {
    private static final PlayerName INSTANCE = new PlayerName();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Player name|username|user"),
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerName instance() {
        return INSTANCE;
    }

    private PlayerName() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(ctx.getScript().getProperties().getPlayer().getName());
    }
}
