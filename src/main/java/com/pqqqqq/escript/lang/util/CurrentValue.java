package com.pqqqqq.escript.lang.util;

import java.util.Optional;

/**
 * Created by Kevin on 2016-09-27.
 * A bounded value that is current, and is always kept up to date
 *
 * @param <T> the type of the value
 */
public interface CurrentValue<T> {

    /**
     * Returns an {@link Optional optional} type for this value
     *
     * @return the type, or {@link Optional#empty()}
     */
    Optional<T> get();

    /**
     * <pre>
     * Sets the current value.
     * Any offering/data manipulation to data holders should be done here as well.
     * </pre>
     *
     * @param value the new value
     * @return true if the value was set successfully
     */
    boolean set(T value);

    /**
     * Checks if the value is present
     *
     * @return true if present
     */
    default boolean isPresent() {
        return get().isPresent();
    }
}
