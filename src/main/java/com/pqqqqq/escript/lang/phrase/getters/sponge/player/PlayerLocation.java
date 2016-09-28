package com.pqqqqq.escript.lang.phrase.getters.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.getters.sponge.ValuePhrase;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

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
            Syntax.compile("player's|person's|player|person loc|location|position|pos"),
            Syntax.compile("loc|location|position|pos of $Player")
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
        return Result.valueSuccess(player::getLocation, player::setLocation);
    }

    @Override
    public Class<?> getCorrespondingClass() {
        return Location.class;
    }
}
