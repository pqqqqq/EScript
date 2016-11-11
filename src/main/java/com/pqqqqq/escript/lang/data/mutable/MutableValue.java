package com.pqqqqq.escript.lang.data.mutable;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.util.Copyable;

/**
 * Created by Kevin on 2016-09-27.
 * A bounded value that is current, mutable and is always kept up to date
 *
 * @param <T> the type of the value
 */
public interface MutableValue<T> extends Copyable<MutableValue<T>> {

    /**
     * Returns a generic type for this value
     *
     * @return the type, or null
     */
    T getValue();

    /**
     * <pre>
     * Sets the current value.
     * Any offering/data manipulation to data holders should be done here as well.
     * </pre>
     *
     * @param value the new value
     * @return true if the value was set successfully
     */
    boolean setValue(T value);

    /**
     * Attempts to set this mutable value's value from a {@link Literal literal}.
     * If a {@link Serializer serializer} is specified for this mutable value, via {@link #getSerializer()}, then
     * the literal will be {@link Serializer#deserialize(Literal) deserialized}.
     * Otherwise, the nested {@link Literal#getValue() value} of the literal will be used, which may cause {@link ClassCastException errors}.
     *
     * @param literal the literal
     * @return true if the value was set successfully
     */
    default boolean setValueFromLiteral(Literal literal) {
        Serializer<T> serializer = getSerializer();
        if (serializer != null) {
            return setValue(serializer.deserialize(literal));
        } else {
            // We're doing it old school (may error)
            return setValue((T) literal.getValue().orElse(null));
        }
    }

    /**
     * Sets the current value by offering a {@link MutableValue mutable value}, {@link Copyable#copy() copying} the value in the process.
     *
     * @param mutableValue the value
     * @return true if the value was set successfully
     */
    default boolean setValue(MutableValue<? extends T> mutableValue) {
        return setValue(mutableValue.copy().getValue());
    }

    /**
     * Checks if the value is present (not null)
     *
     * @return true if present
     */
    default boolean isPresent() {
        return getValue() != null;
    }

    /**
     * Gets the generic type's {@link Serializer serializer}
     *
     * @return the serializer, or null if the value is not serializable
     */
    default Serializer<T> getSerializer() {
        return null;
    }
}
