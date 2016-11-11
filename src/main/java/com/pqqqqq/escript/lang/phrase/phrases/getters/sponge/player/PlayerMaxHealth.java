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
 * The player max health phrase, which gets the player's maximum health
 * Some examples:
 *      <code>player's max health
 *      player maximum health
 *      maximum health of player
 *      player's max hp
 *      max health of "Joe"</code>
 * </pre>
 */
public class PlayerMaxHealth implements ValuePhrase {
    private static final PlayerMaxHealth INSTANCE = new PlayerMaxHealth();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Player max|maximum health|hp|hitpoints"),
            Syntax.compile("the? max|maximum health|hp|hitpoints of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerMaxHealth instance() {
        return INSTANCE;
    }

    private PlayerMaxHealth() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromStore(player.getHealthData().maxHealth(), player, DoubleSerializer.instance()));
    }
}
