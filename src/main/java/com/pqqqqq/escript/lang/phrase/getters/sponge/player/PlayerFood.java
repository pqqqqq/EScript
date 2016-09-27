package com.pqqqqq.escript.lang.phrase.getters.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.getters.sponge.ValuePhrase;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
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
            Syntax.compile("player's|person's|player|person food|hunger|starvation level|lvl?"),
            Syntax.compile("food|hunger|starvation level|lvl? of $Player")
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
        return Result.valueSuccess(player.getFoodData().foodLevel(), player);
    }
}