package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;

/**
 * The double {@link PrimitiveSerializer primitive serializer}.
 */
public class DoubleSerializer implements PrimitiveSerializer<Double> {
    private static final DoubleSerializer INSTANCE = new DoubleSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static DoubleSerializer instance() {
        return INSTANCE;
    }

    private DoubleSerializer() {
    }

    @Override
    public Double deserialize(Literal value) {
        return value.asNumber();
    }

    @Override
    public Class<Double> getCorrespondingClass() {
        return Double.class;
    }
}
