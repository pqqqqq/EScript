package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;

/**
 * The boolean {@link PrimitiveSerializer primitive serializer}.
 */
public class BooleanSerializer implements PrimitiveSerializer<Boolean> {
    private static final BooleanSerializer INSTANCE = new BooleanSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static BooleanSerializer instance() {
        return INSTANCE;
    }

    private BooleanSerializer() {
    }

    @Override
    public Boolean deserialize(Literal value) {
        return value.asBoolean();
    }

    @Override
    public Class<Boolean> getCorrespondingClass() {
        return Boolean.class;
    }
}
