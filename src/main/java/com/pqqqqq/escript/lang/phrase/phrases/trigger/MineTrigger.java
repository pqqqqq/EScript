package com.pqqqqq.escript.lang.phrase.phrases.trigger;

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
            Syntax.compile("when|if $MineType is? mine|mined|broken|break|breaked", true),
            Syntax.compile("on mine|mining|brake|broken of? $MineType", true)

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
        String mineType = ctx.getLiteral("MineType").asString();
        BlockType blockType = Sponge.getRegistry().getType(BlockType.class, mineType).orElseThrow(() -> new UnknownRegistryTypeException("Unknown block type: %s", mineType));

        Trigger.from(ctx.getLine().getRawScript(), (properties) -> {
            Optional<BlockSnapshot> block = properties.getVariable("Block", BlockSnapshot.class);
            return block.isPresent() && block.get().getState().getType().equals(blockType);
        }, Causes.MINE);
        return Result.success();
    }
}
