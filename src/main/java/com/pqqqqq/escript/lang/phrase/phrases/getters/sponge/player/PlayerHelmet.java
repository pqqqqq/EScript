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
 * The player helmet phrase, which gets the player's helmet
 * Some examples:
 *      <code>player's helmet
 *      player helmet
 *      helmet of player
 *      player's helmet
 *      helmet of "Joe"</code>
 * </pre>
 */
public class PlayerHelmet implements ValuePhrase {
    private static final PlayerHelmet INSTANCE = new PlayerHelmet();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("player's|person's|player|person helmet"),
            Syntax.compile("helmet of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerHelmet instance() {
        return INSTANCE;
    }

    private PlayerHelmet() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromConsumer(() -> player.getHelmet().orElse(null), player::setHelmet));
    }
}
