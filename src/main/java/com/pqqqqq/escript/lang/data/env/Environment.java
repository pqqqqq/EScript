package com.pqqqqq.escript.lang.data.env;

import java.util.Optional;

/**
 * An environment, where {@link EnvironmentEntry environment entries} can be stored.
 * Environments link string keys (or names) to each value in a K,V map fashion.
 *
 * @param <T> the type of the value
 * @param <I> the identifier type
 * @param <V> the environment entry type
 */
public interface Environment<T, I, V extends EnvironmentEntry<I>> {

    /**
     * Retrieves the {@link EnvironmentEntry entry} with the given ID
     *
     * @param id the id
     * @return the value
     */
    Optional<V> getValue(I id);

    /**
     * Checks a {@link EnvironmentEntry entry} with the given id is in this environment
     *
     * @param id the id
     * @return true if a value exists with the id
     */
    boolean contains(I id);

    /**
     * Removes the {@link EnvironmentEntry entry} with the given id from this environment
     *
     * @param id the id of the value
     * @return true if a value was found and removed
     */
    boolean remove(I id);

    /**
     * Creates a new {@link EnvironmentEntry entry} with the given id
     *
     * @param id the id
     * @return the new value
     */
    default V create(I id) {
        return create(id, null);
    }

    /**
     * Creates a new {@link EnvironmentEntry entry} with the given id and value
     *
     * @param id the id
     * @param value the value
     * @return the new value
     */
    V create(I id, T value);

    /**
     * Creates a {@link EnvironmentEntry entry}, or gets one if the given id if it already exists
     *
     * @param id the id
     * @return the value
     */
    default V createOrGet(I id) {
        return getValue(id).orElseGet(() -> create(id));
    }
}
