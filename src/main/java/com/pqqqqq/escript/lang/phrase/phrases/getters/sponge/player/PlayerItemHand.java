package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.data.serializer.item.ItemStackSerializer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player hand item phrase, which gets the player's item in-hand
 * Some examples:
 *      <code>player's hand
 *      player item in hand
 *      item hand of player
 *      player's hand
 *      item hand of "Joe"</code>
 * </pre>
 */
public class PlayerItemHand implements ValuePhrase {
    private static final PlayerItemHand INSTANCE = new PlayerItemHand();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("${player}Player item? in* $@HandType hand"), // TODO allow "hand item"?
            Syntax.compile("the? item? in* $@HandType hand of ${player}Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerItemHand instance() {
        return INSTANCE;
    }

    private PlayerItemHand() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");

        boolean offHand = ctx.getLiteral("HandType", "main").asString().equalsIgnoreCase("off");
        HandType handType = (offHand ? HandTypes.OFF_HAND : HandTypes.MAIN_HAND); // Makes more sense to have "off hand THAN off_hand hand"

        return Result.valueSuccess(LinkedMutableValue.fromConsumer(() -> player.getItemInHand(handType).orElse(null), (stack) -> player.setItemInHand(handType, stack), ItemStackSerializer.instance()));
    }
}
