package com.pqqqqq.escript.lang.data.mutable;

import com.pqqqqq.escript.lang.data.serializer.Serializer;

/**
 * Created by Kevin on 2016-10-12.
 * <pre>
 * A simple {@link MutableValue mutable value}, where the type is stored as a single field.
 * Serializers are optional (and immutable).
 * </pre>
 */
public final class SimpleMutableValue<T> implements MutableValue<T> {
    private T value;
    private final Serializer<T> serializer;

    /**
     * Creates a new simple mutable value from the given value
     *
     * @param value the value
     * @param <T>   the value's generic type
     * @return the new instance
     */
    public static <T> SimpleMutableValue<T> from(T value) {
        return from(value, null);
    }

    /**
     * Creates a new simple mutable value from the given value, and {@link Serializer serializer}
     *
     * @param value      the value
     * @param serializer the serializer
     * @param <T>        the value's generic type
     * @return the new instance
     */
    public static <T> SimpleMutableValue<T> from(T value, Serializer<T> serializer) {
        return new SimpleMutableValue<>(value, serializer);
    }

    private SimpleMutableValue(T value, Serializer<T> serializer) {
        this.value = value;
        this.serializer = serializer;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean setValue(T value) {
        this.value = value;
        return true;
    }

    @Override
    public Serializer<T> getSerializer() {
        return serializer;
    }

    @Override
    public SimpleMutableValue<T> copy() {
        return from(value, serializer);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
