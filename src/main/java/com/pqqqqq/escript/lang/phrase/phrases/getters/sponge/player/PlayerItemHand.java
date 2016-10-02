package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

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
            Syntax.compile("player's|person's|player|person item? in* @HandType hand"), // TODO allow "hand item"?
            Syntax.compile("item? in* @HandType hand of $Player")
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
        HandType handType = ctx.getSerialized("HandType", HandType.class, HandTypes.MAIN_HAND);
        Player player = ctx.getPlayer("Player");

        return Result.valueSuccess(() -> player.getItemInHand(handType).orElse(null), (stack) -> {
            player.setItemInHand(handType, stack);
            return true;
        });
    }

    @Override
    public Class<?> getCorrespondingClass() {
        return ItemStack.class;
    }
}
