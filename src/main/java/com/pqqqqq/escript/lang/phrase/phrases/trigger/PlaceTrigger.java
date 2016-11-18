package com.pqqqqq.escript.lang.phrase.phrases.trigger;

import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.exception.UnknownRegistryTypeException;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.script.Script;
import com.pqqqqq.escript.lang.trigger.Trigger;
import com.pqqqqq.escript.lang.trigger.cause.Causes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;

import java.util.ArrayList;
import java.util.List;
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
            Syntax.compile("when|if a|an? $PlaceTypes is|are? place|placed:"),
            Syntax.compile("on place|placed|placement of? a|an? $PlaceTypes:"),
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
        LiteralStore placeTypes = ctx.getLiteral("PlaceTypes", (Object) null).asStore();
        List<BlockType> blockTypes = new ArrayList<>();

        placeTypes.getListModule().literalStream().forEach(type -> blockTypes.add(Sponge.getRegistry().getType(BlockType.class, type.asString()).
                orElseThrow(() -> new UnknownRegistryTypeException("Unknown block type: %s", type.asString()))));

        Trigger.from(ctx.getLine().getRawScript(), (properties) -> {
            if (blockTypes.isEmpty()) {
                return true;
            } else {
                Optional<BlockSnapshot> block = properties.getVariable("Block", BlockSnapshot.class);
                return block.isPresent() && blockTypes.contains(block.get().getState().getType());
            }
        }, Causes.PLACE);
        return Result.success();
    }
}
