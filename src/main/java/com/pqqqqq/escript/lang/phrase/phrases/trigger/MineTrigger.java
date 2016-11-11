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
 * The mine trigger phrase, which fires when a player breaks a block.
 * Some ways of doing this:
 *
 *     <code>on mine of stone:
 *     when stone is mined:
 *     if stone is mined:
 *     if mined stone:</code>
 * </pre>
 */
public class MineTrigger implements Phrase {
    private static final MineTrigger INSTANCE = new MineTrigger();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("when|if $MineTypes is? mine|mined|broken|break|breaked:"),
            Syntax.compile("on mine|mining|brake|broken of? $MineTypes:")

            /*Pattern.compile("^(when|if)(\\s+?)(?<MineType>\\S+?)(\\s+?)(is(\\s*?))?mine(d)?:$", Pattern.CASE_INSENSITIVE),
            Pattern.compile("^on(\\s+?)(mine|mining)(\\s+?)(of(\\s*?))?(?<MineType>\\S+?):$", Pattern.CASE_INSENSITIVE)*/
    };

    /**
     * Gets the main mine trigger instance
     *
     * @return the instance
     */
    public static MineTrigger instance() {
        return INSTANCE;
    }

    private MineTrigger() {
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
        LiteralStore mineTypes = ctx.getLiteral("MineTypes", (Object) null).asStore();
        List<BlockType> blockTypes = new ArrayList<>();

        mineTypes.getListModule().literalStream().forEach(type -> blockTypes.add(Sponge.getRegistry().getType(BlockType.class, type.asString()).
                orElseThrow(() -> new UnknownRegistryTypeException("Unknown block type: %s", type.asString()))));

        Trigger.from(ctx.getLine().getRawScript(), (properties) -> {
            if (blockTypes.isEmpty()) {
                return true;
            } else {
                Optional<BlockSnapshot> block = properties.getVariable("Block", BlockSnapshot.class);
                return block.isPresent() && blockTypes.contains(block.get().getState().getType());
            }
        }, Causes.MINE);
        return Result.success();
    }
}
