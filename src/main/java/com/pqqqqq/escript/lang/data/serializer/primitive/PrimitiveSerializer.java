package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.serializer.Serializer;

/**
 * The {@link Literal literal} cast serializer.
 * This serializer simply makes use of the literal's casting methods for given types.
 */
public interface PrimitiveSerializer<T> extends Serializer<T> {

    @Override
    default Literal serialize(T value) {
        return Literal.fromObject(value); // Simple, use fromObject
    }
}
