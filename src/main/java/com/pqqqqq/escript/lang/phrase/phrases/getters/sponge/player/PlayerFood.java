package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.data.serializer.primitive.IntegerSerializer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player food phrase, which gets the player's food level
 * Some examples:
 *      <code>player's food level
 *      player hunger
 *      food of player
 *      player's hunger
 *      food of "Joe"</code>
 * </pre>
 */
public class PlayerFood implements ValuePhrase {
    private static final PlayerFood INSTANCE = new PlayerFood();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("${player}Player food|hunger|starvation level|lvl?"),
            Syntax.compile("the? food|hunger|starvation level|lvl? of ${player}Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerFood instance() {
        return INSTANCE;
    }

    private PlayerFood() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromStore(player.getFoodData().foodLevel(), player, IntegerSerializer.instance()));
    }
}
