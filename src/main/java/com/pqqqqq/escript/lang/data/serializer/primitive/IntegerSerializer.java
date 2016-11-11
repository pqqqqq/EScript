package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;

/**
 * The integer {@link PrimitiveSerializer primitive serializer}.
 */
public class IntegerSerializer implements PrimitiveSerializer<Integer> {
    private static final IntegerSerializer INSTANCE = new IntegerSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static IntegerSerializer instance() {
        return INSTANCE;
    }

    private IntegerSerializer() {
    }

    @Override
    public Integer deserialize(Literal value) {
        return value.asNumber().intValue();
    }

    @Override
    public Class<Integer> getCorrespondingClass() {
        return Integer.class;
    }
}
