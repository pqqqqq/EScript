package com.pqqqqq.escript.lang.phrase.phrases.action.sponge.player;

import com.pqqqqq.escript.EScript;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The close inventory phrase, which cl;oses the player's current inventory
 * Examples:
 *      <code>close player's inventory
 *      player close inventory
 *      close inventory of player
 *      close inventory of "Joe"</code>
 * </pre>
 */
public class PlayerCloseInventory implements Phrase {
    private static final PlayerCloseInventory INSTANCE = new PlayerCloseInventory();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("close|exit ${player}Player inventory|inv"),
            Syntax.compile("close|exit inventory|inv of|for ${player}Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerCloseInventory instance() {
        return INSTANCE;
    }

    private PlayerCloseInventory() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        ctx.getPlayer("Player").closeInventory(EScript.instance().getPluginCause());
        return Result.success();
    }
}
