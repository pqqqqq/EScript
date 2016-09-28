package com.pqqqqq.escript.lang.data.serializer;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.registry.RegistryEntry;

/**
 * Created by Kevin on 2016-09-27.
 * <pre>
 * A datum serializer, which will take a object and {@link #serialize(Object) serializes} it into a {@link Literal literal}
 * The serializer will also {@link #deserialize(Literal) deserialize} a {@link Literal literal} into the given value.
 * </pre>
 *
 * @param <T> the type this serialize is responsible for
 */
public interface Serializer<T> extends RegistryEntry {

    /**
     * <pre>
     * Serializes the value into a {@link Literal literal}. Serialization depends on the type.
     * Commonly, serialization will create a string or list.
     * </pre>
     *
     * @param value the value to serialize
     * @return the new literal
     */
    Literal serialize(T value);

    /**
     * <pre>
     * Deserializes the {@link Literal literal} into the value. The literal will need to contain the type applicable to the type.
     * Each type has a different required serialization.
     * </pre>
     *
     * @param value the literal
     * @return the deserialized value
     */
    T deserialize(Literal value);

    /**
     * Gets the corresponding class for this serializer
     *
     * @return the class
     */
    Class<? extends T> getCorrespondingClass();

    /**
     * Checks if the passed object is applicable to be {@link #serialize(Object) serialized} by this serializer.
     *
     * @param object the object
     * @return true if serializable
     */
    default boolean isApplicable(Object object) {
        return getCorrespondingClass().isInstance(object);
    }
}
