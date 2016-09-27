package com.pqqqqq.escript.lang.phrase.getters.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.getters.sponge.ValuePhrase;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player saturation phrase, which gets the player's saturation level
 * Some examples:
 *      <code>player's saturation level
 *      player saturation
 *      saturation of player
 *      player's saturation
 *      saturation of "Joe"</code>
 * </pre>
 */
public class PlayerSaturation implements ValuePhrase {
    private static final PlayerSaturation INSTANCE = new PlayerSaturation();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("player's|person's|player|person saturation level|lvl?"),
            Syntax.compile("saturation level|lvl? of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerSaturation instance() {
        return INSTANCE;
    }

    private PlayerSaturation() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(player.getFoodData().saturation(), player);
    }
}
