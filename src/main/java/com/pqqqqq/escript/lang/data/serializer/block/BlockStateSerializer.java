package com.pqqqqq.escript.lang.data.serializer.block;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.data.store.map.MapModule;
import com.pqqqqq.escript.lang.exception.SerializationException;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;

/**
 * The {@link BlockState block state} serializer
 */
public class BlockStateSerializer implements Serializer<BlockState> {
    private static final BlockStateSerializer INSTANCE = new BlockStateSerializer();
    private static final String[] MAPPER = {"type"};

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static BlockStateSerializer instance() {
        return INSTANCE;
    }

    private BlockStateSerializer() {
    }

    @Override
    public Literal serialize(BlockState value) {
        LiteralStore store = LiteralStore.empty();
        MapModule mapModule = store.getMapModule();

        mapModule.add("type", value.getType().getId());
        // TODO traits
        return Literal.fromObject(store);
    }

    @Override
    public BlockState deserialize(Literal value) {
        // TODO traits
        LiteralStore store = value.asStore().collapseMap(MAPPER);
        MapModule mapModule = store.getMapModule();

        if (mapModule.size() < 1) { // We'll allow lenience with having more
            throw new SerializationException("Value \"%s\" has incorrect number of arguments for a BlockState (<1)", value.asString());
        }

        Literal type = mapModule.getLiteral("type");
        BlockType blockType = Serializers.BLOCK_TYPE.deserialize(type);

        return blockType.getDefaultState();
    }

    @Override
    public Class<BlockState> getCorrespondingClass() {
        return BlockState.class;
    }
}
