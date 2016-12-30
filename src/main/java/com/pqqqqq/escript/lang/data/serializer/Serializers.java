package com.pqqqqq.escript.lang.data.serializer;

import com.pqqqqq.escript.lang.data.Datum;
import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.serializer.block.BlockSnapshotSerializer;
import com.pqqqqq.escript.lang.data.serializer.block.BlockStateSerializer;
import com.pqqqqq.escript.lang.data.serializer.block.LocationSerializer;
import com.pqqqqq.escript.lang.data.serializer.entity.PlayerSerializer;
import com.pqqqqq.escript.lang.data.serializer.item.ItemStackSerializer;
import com.pqqqqq.escript.lang.data.serializer.primitive.*;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.registry.Registry;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.item.ItemType;

import java.util.Optional;

/**
 * Created by Kevin on 2016-09-27.
 * A {@link Registry registry} of {@link Serializer serializers}
 */
public class Serializers extends Registry<Serializer> {
    // REGISTRY \\

    // PRIMITIVES
    public static final PrimitiveSerializer<Literal> SELF = SelfSerializer.instance();
    public static final PrimitiveSerializer<Datum> DATUM = DatumSerializer.instance();
    public static final PrimitiveSerializer<Double> DOUBLE = DoubleSerializer.instance();
    public static final PrimitiveSerializer<Boolean> BOOLEAN = BooleanSerializer.instance();
    public static final PrimitiveSerializer<Float> FLOAT = FloatSerializer.instance();
    public static final PrimitiveSerializer<Integer> INTEGER = IntegerSerializer.instance();
    public static final PrimitiveSerializer<Long> LONG = LongSerializer.instance();
    public static final PrimitiveSerializer<LiteralStore> STORE = StoreSerializer.instance();
    public static final PrimitiveSerializer<String> STRING = StringSerializer.instance();

    public static final LocationSerializer LOCATION = LocationSerializer.instance();
    public static final ItemStackSerializer ITEM_STACK = ItemStackSerializer.instance();

    public static final BlockStateSerializer BLOCK_STATE = BlockStateSerializer.instance();
    public static final BlockSnapshotSerializer BLOCK_SNAPSHOT = BlockSnapshotSerializer.instance();

    public static final PlayerSerializer PLAYER = PlayerSerializer.instance();

    // CATALOG TYPES
    public static final CatalogSerializer<GameMode> GAME_MODE = () -> GameMode.class;
    public static final CatalogSerializer<ItemType> ITEM_TYPE = () -> ItemType.class;
    public static final CatalogSerializer<BlockType> BLOCK_TYPE = () -> BlockType.class;
    public static final CatalogSerializer<HandType> HAND_TYPE = () -> HandType.class;
    public static final CatalogSerializer<EntityType> ENTITY_TYPE = () -> EntityType.class;

    // END REGISTRY \\

    private static final Serializers INSTANCE = new Serializers();

    /**
     * Gets the main serializers instance
     *
     * @return the main instance
     */
    public static Serializers instance() {
        return INSTANCE;
    }

    private Serializers() {
        super(Serializer.class);
    }

    /**
     * Attempts to find the corresponding serializer for the given object
     * @param object the object
     * @param <T> the object's generic type
     * @return the serializer
     */

    @SuppressWarnings("unchecked")
    public <T> Optional<Serializer<T>> getSerializer(T object) {
        if (object == null) {
            return Optional.empty();
        }

        return registry().stream().filter((serializer) -> serializer.isApplicable(object)).findFirst().map(serializer -> (Serializer<T>) serializer);
    }

    /**
     * Attempts to {@link Serializer#serialize(Object) serialize} the object using the serializer registry
     *
     * @param object the object to serialize
     * @return the serialized {@link Literal literal}, or {@link Optional#empty()}
     */

    public Optional<Literal> serialize(Object object) {
        return getSerializer(object).map(serializer -> serializer.serialize(object));
    }
}
