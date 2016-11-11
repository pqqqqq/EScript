package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;

/**
 * The self {@link PrimitiveSerializer primitive serializer}. This just targets itself (literal).
 */
public class SelfSerializer implements PrimitiveSerializer<Literal> {
    private static final SelfSerializer INSTANCE = new SelfSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static SelfSerializer instance() {
        return INSTANCE;
    }

    private SelfSerializer() {
    }

    @Override
    public Literal deserialize(Literal value) {
        return value;
    }

    @Override
    public Literal serialize(Literal value) {
        return value;
    }

    @Override
    public Class<Literal> getCorrespondingClass() {
        return Literal.class;
    }
}
