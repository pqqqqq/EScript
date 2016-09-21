package com.pqqqqq.escript.lang.phrase.action.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The kick phrase, which kicks the given player
 * Examples:
 *      <code>
 *          kick "Hello!"
 *          kick player "Hello!"
 *          kick player "Hello!" for "Disruptive."
 *      </code>
 * </pre>
 */
public class PlayerKick implements Phrase {
    private static final PlayerKick INSTANCE = new PlayerKick();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("kick player? $Player? for|reason? $Reason*"),
            Syntax.compile("kick player? $Player? for reason $Reason")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerKick instance() {
        return INSTANCE;
    }

    private PlayerKick() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        String reason = ctx.getLiteral("Reason", null).asString();

        if (reason.isEmpty()) {
            player.kick();
        } else {
            player.kick(Text.of(reason));
        }

        return Result.success();
    }
}
