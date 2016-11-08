package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player health phrase, which gets the player's health
 * Some examples:
 *      <code>player's health
 *      player health
 *      health of player
 *      player's hp
 *      health of "Joe"</code>
 * </pre>
 */
public class PlayerHealth implements ValuePhrase {
    private static final PlayerHealth INSTANCE = new PlayerHealth();
    private static final Syntax[] SYNTAXES = {
            // Syntax.compile("$Player? health|hp|hitpoints"), THIS SCREWS UP PRINTS AND MESSAGES
            Syntax.compile("player's|person's|player|person health|hp|hitpoints"),
            Syntax.compile("the? health|hp|hitpoints of $Player")

            /*Pattern.compile("^(player|person)(\\'s)?(\\s+?)(health|hp|hitpoints)$", Pattern.CASE_INSENSITIVE),
            Pattern.compile("^(health|hp|hitpoints)(\\s+?)of(\\s+?)(player|person)$", Pattern.CASE_INSENSITIVE)*/
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerHealth instance() {
        return INSTANCE;
    }

    private PlayerHealth() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromStore(player.getHealthData().health(), player));
    }
}
