package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.block;

import com.pqqqqq.escript.EScript;
import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.world.Location;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The block type phrase, which gets the block type at the given location
 * Some examples:
 *      <code>block type at {"world", 0, 0, 0}
 *      type at </code>
 * </pre>
 */
public class BlockType implements ValuePhrase {
    private static final BlockType INSTANCE = new BlockType();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("block? type at|of ${block, location}Location")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static BlockType instance() {
        return INSTANCE;
    }

    private BlockType() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Location location = ctx.getSerialized("Location", Serializers.LOCATION);
        return Result.valueSuccess(LinkedMutableValue.fromFunction(() -> location.getBlockType(), (blockType) -> location.setBlockType(blockType, EScript.instance().getPluginCause()), Serializers.BLOCK_TYPE));
    }
}
