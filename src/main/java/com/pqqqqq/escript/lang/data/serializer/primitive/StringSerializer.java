package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;

/**
 * The String {@link PrimitiveSerializer primitive serializer}.
 */
public class StringSerializer implements PrimitiveSerializer<String> {
    private static final StringSerializer INSTANCE = new StringSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static StringSerializer instance() {
        return INSTANCE;
    }

    private StringSerializer() {
    }

    @Override
    public String deserialize(Literal value) {
        return value.asString();
    }

    @Override
    public Class<String> getCorrespondingClass() {
        return String.class;
    }
}
