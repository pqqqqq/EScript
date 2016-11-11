package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;

/**
 * The long {@link PrimitiveSerializer primitive serializer}.
 */
public class LongSerializer implements PrimitiveSerializer<Long> {
    private static final LongSerializer INSTANCE = new LongSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static LongSerializer instance() {
        return INSTANCE;
    }

    private LongSerializer() {
    }

    @Override
    public Long deserialize(Literal value) {
        return value.asNumber().longValue();
    }

    @Override
    public Class<Long> getCorrespondingClass() {
        return Long.class;
    }
}
