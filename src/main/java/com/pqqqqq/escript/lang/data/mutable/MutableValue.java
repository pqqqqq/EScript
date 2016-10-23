package com.pqqqqq.escript.lang.data.mutable;

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
