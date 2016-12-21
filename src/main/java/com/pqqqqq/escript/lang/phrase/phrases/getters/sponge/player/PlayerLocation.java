package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.data.serializer.block.LocationSerializer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player location phrase, which gets the player's location
 * Some examples:
 *      <code>player's location
 *      player loc
 *      location of player
 *      player's position
 *      position of "Joe"</code>
 * </pre>
 */
public class PlayerLocation implements ValuePhrase {
    private static final PlayerLocation INSTANCE = new PlayerLocation();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Player loc|location|position|pos"),
            Syntax.compile("the? loc|location|position|pos of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerLocation instance() {
        return INSTANCE;
    }

    private PlayerLocation() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromFunction(player::getLocation, player::setLocation, LocationSerializer.instance()));
    }
}
