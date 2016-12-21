package com.pqqqqq.escript.lang.data.serializer.block;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

/**
 * The {@link BlockSnapshot block snapshot} serializer
 */
public class BlockSnapshotSerializer implements Serializer<BlockSnapshot> {
    private static final BlockSnapshotSerializer INSTANCE = new BlockSnapshotSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static BlockSnapshotSerializer instance() {
        return INSTANCE;
    }

    private BlockSnapshotSerializer() {
    }

    @Override
    public Literal serialize(BlockSnapshot value) {
        Optional<Location<World>> location = value.getLocation();
        if (!location.isPresent()) {
            return Literal.EMPTY;
        }

        BlockState state = value.getState();

        LiteralStore store = LiteralStore.empty();
        store.addAll(Serializers.LOCATION.serialize(location.get()).asStore());
        store.addAll(Serializers.BLOCK_STATE.serialize(state).asStore());

        return Literal.fromObject(store);
    }

    @Override
    public BlockSnapshot deserialize(Literal value) {
        Location<World> location = Serializers.LOCATION.deserialize(value);
        BlockState state = Serializers.BLOCK_STATE.deserialize(value);

        return state.snapshotFor(location);
    }

    @Override
    public Class<BlockSnapshot> getCorrespondingClass() {
        return BlockSnapshot.class;
    }
}
