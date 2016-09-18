package com.pqqqqq.escript.lang.phrase.trigger;

import com.pqqqqq.escript.lang.exception.UnknownRegistryTypeException;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import com.pqqqqq.escript.lang.script.Script;
import com.pqqqqq.escript.lang.trigger.Trigger;
import com.pqqqqq.escript.lang.trigger.cause.Causes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;

import java.util.Optional;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The place trigger phrase, which fires when a player places a block.
 * Some ways of doing this:
 *
 *     <code>on place of stone:
 *     when stone is placed:
 *     if stone is placed:
 *     if placed stone:</code>
 * </pre>
 */
public class PlaceTrigger implements Phrase {
    private static final PlaceTrigger INSTANCE = new PlaceTrigger();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("when|if $PlaceType is? place|placed", true),
            Syntax.compile("on place|placed|placement of? $PlaceType", true)
    };

    /**
     * Gets the main place trigger instance
     *
     * @return the instance
     */
    public static PlaceTrigger instance() {
        return INSTANCE;
    }

    private PlaceTrigger() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Script.State getRunState() {
        return Script.State.COMPILE_TIME; // Run at compile time
    }

    @Override
    public Result execute(Context ctx) {
        String placeType = ctx.getLiteral("PlaceType").asString();
        BlockType blockType = Sponge.getRegistry().getType(BlockType.class, placeType).orElseThrow(() -> new UnknownRegistryTypeException("Unknown block type: %s", placeType));

        Trigger.from(ctx.getLine().getRawScript(), (properties) -> {
            Optional<BlockSnapshot> block = properties.getVariable("Block", BlockSnapshot.class);
            return block.isPresent() && block.get().getState().getType().equals(blockType);
        }, Causes.PLACE);
        return Result.success();
    }
}
