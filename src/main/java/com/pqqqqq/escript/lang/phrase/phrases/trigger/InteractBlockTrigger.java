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
 * The interact block trigger phrase, which fires when a player interacts with a block (or the air).
 * Some ways of doing this:
 *
 *     <code>on left click of "minecraft:stone":
 *     when a block is right clicked:</code>
 * </pre>
 */
public class InteractBlockTrigger implements Phrase {
    private static final InteractBlockTrigger INSTANCE = new InteractBlockTrigger();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("on ^Click click of? a|an? $Blocks?:"),
            Syntax.compile("when|if a|an? $Blocks is|are? ^Click click|clicked:"),
    };

    /**
     * Gets the main interact block trigger instance
     *
     * @return the instance
     */
    public static InteractBlockTrigger instance() {
        return INSTANCE;
    }

    private InteractBlockTrigger() {
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
        LiteralStore interactTypes = ctx.getLiteral("Blocks", (Object) null).asStore();
        String click = ctx.getStrarg("Click");

        List<BlockType> blockTypes = new ArrayList<>();

        interactTypes.getListModule().literalStream().forEach(type -> blockTypes.add(Sponge.getRegistry().getType(BlockType.class, type.asString()).
                orElseThrow(() -> new UnknownRegistryTypeException("Unknown block type: %s", type.asString()))));

        Trigger.builder().script(ctx.getLine().getRawScript()).causes(Causes.INTERACT_BLOCK).predicate((properties) -> {
            if (blockTypes.isEmpty()) {
                return true;
            } else {
                Optional<BlockSnapshot> block = properties.getVariable("Block", BlockSnapshot.class);
                Optional<String> interaction = properties.getVariable("Interaction", String.class);

                if (!block.isPresent() || !interaction.isPresent()) {
                    return false;
                }

                return interaction.get().equalsIgnoreCase(click) && blockTypes.contains(block.get().getState().getType());
            }
        }).build();
        return Result.success();
    }
}
