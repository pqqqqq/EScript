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
 * The player exhaustion phrase, which gets the player's exhaustion level
 * Some examples:
 *      <code>player's exhaustion level
 *      player exhaustion
 *      exhaustion of player
 *      player's exhaust
 *      exhaust of "Joe"</code>
 * </pre>
 */
public class PlayerExhaustion implements ValuePhrase {
    private static final PlayerExhaustion INSTANCE = new PlayerExhaustion();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("player's|person's|player|person exhaust|exhaustion level|lvl?"),
            Syntax.compile("exhaust|exhaustion level|lvl? of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerExhaustion instance() {
        return INSTANCE;
    }

    private PlayerExhaustion() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");

        return Result.valueSuccess(LinkedMutableValue.fromStore(player.getFoodData().exhaustion(), player));
    }
}
