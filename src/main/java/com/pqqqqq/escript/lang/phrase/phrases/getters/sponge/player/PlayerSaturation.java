package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.data.serializer.primitive.DoubleSerializer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
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
            Syntax.compile("$Player saturation level|lvl?"),
            Syntax.compile("the? saturation level|lvl? of $Player")
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
        return Result.valueSuccess(LinkedMutableValue.fromStore(player.getFoodData().saturation(), player, DoubleSerializer.instance()));
    }
}
