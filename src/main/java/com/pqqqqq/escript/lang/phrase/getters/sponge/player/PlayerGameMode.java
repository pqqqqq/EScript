package com.pqqqqq.escript.lang.phrase.getters.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player game mode phrase, which gets the player's game mode
 * Some examples:
 *      <code>player's gamemode
 *      player gamemode
 *      gamemode of player
 *      player's gamemode
 *      gamemode of "Joe"</code>
 * </pre>
 */
public class PlayerGameMode implements Phrase {
    private static final PlayerGameMode INSTANCE = new PlayerGameMode();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("player's|person's|player|person gamemode"), // TODO allow "game mode"?
            Syntax.compile("gamemode of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerGameMode instance() {
        return INSTANCE;
    }

    private PlayerGameMode() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(ctx.getPlayer("Player").gameMode().get().getName());
    }
}
