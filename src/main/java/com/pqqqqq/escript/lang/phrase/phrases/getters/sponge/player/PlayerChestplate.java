package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.data.serializer.item.ItemStackSerializer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player chestplate phrase, which gets the player's chestplate
 * Some examples:
 *      <code>player's chestplate
 *      player chestplate
 *      chestplate of player
 *      player's chestplate
 *      chestplate of "Joe"</code>
 * </pre>
 */
public class PlayerChestplate implements ValuePhrase {
    private static final PlayerChestplate INSTANCE = new PlayerChestplate();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("${player}Player chestplate|chest|body"),
            Syntax.compile("the? chestplate|chest|body of ${player}Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerChestplate instance() {
        return INSTANCE;
    }

    private PlayerChestplate() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromConsumer(() -> player.getChestplate().orElse(null), player::setChestplate, ItemStackSerializer.instance()));
    }
}
