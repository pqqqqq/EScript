package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.store.LiteralStore;

/**
 * The store {@link PrimitiveSerializer primitive serializer}.
 */
public class StoreSerializer implements PrimitiveSerializer<LiteralStore> {
    private static final StoreSerializer INSTANCE = new StoreSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static StoreSerializer instance() {
        return INSTANCE;
    }

    private StoreSerializer() {
    }

    @Override
    public LiteralStore deserialize(Literal value) {
        return value.asStore();
    }

    @Override
    public Class<LiteralStore> getCorrespondingClass() {
        return LiteralStore.class;
    }
}
