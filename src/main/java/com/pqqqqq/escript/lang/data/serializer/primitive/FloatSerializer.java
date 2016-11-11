package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;

/**
 * The float {@link PrimitiveSerializer primitive serializer}.
 */
public class FloatSerializer implements PrimitiveSerializer<Float> {
    private static final FloatSerializer INSTANCE = new FloatSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static FloatSerializer instance() {
        return INSTANCE;
    }

    private FloatSerializer() {
    }

    @Override
    public Float deserialize(Literal value) {
        return value.asNumber().floatValue();
    }

    @Override
    public Class<Float> getCorrespondingClass() {
        return Float.class;
    }
}
