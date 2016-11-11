package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.data.serializer.ItemStackSerializer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player boots phrase, which gets the player's boots
 * Some examples:
 *      <code>player's boots
 *      player boots
 *      boots of player
 *      player's boots
 *      boots of "Joe"</code>
 * </pre>
 */
public class PlayerBoots implements ValuePhrase {
    private static final PlayerBoots INSTANCE = new PlayerBoots();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Player boots|feet|shoes"),
            Syntax.compile("the? boots|feet|shoes of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerBoots instance() {
        return INSTANCE;
    }

    private PlayerBoots() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromConsumer(() -> player.getBoots().orElse(null), player::setBoots, ItemStackSerializer.instance()));
    }
}
