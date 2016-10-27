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
 * The player leggings phrase, which gets the player's leggings
 * Some examples:
 *      <code>player's leggings
 *      player leggings
 *      leggings of player
 *      player's leggings
 *      leggings of "Joe"</code>
 * </pre>
 */
public class PlayerLeggings implements ValuePhrase {
    private static final PlayerLeggings INSTANCE = new PlayerLeggings();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("player's|person's|player|person leggings|legs"),
            Syntax.compile("leggings|legs of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerLeggings instance() {
        return INSTANCE;
    }

    private PlayerLeggings() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromConsumer(() -> player.getLeggings().orElse(null), player::setLeggings));
    }
}
