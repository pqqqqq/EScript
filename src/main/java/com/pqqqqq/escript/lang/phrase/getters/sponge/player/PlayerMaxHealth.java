package com.pqqqqq.escript.lang.phrase.getters.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.getters.ValuePhrase;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
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
            Syntax.compile("player's|person's|player|person max|maximum health|hp|hitpoints"),
            Syntax.compile("max|maximum health|hp|hitpoints of $Player")
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
        return Result.valueSuccess(player.getHealthData().maxHealth(), player);
    }
}
