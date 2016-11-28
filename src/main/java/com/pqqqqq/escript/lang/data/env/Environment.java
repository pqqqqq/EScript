package com.pqqqqq.escript.lang.data.env;

import java.util.Optional;

/**
 * An environment, where {@link EnvironmentEntry environment entries} can be stored.
 * Environments link string keys (or names) to each value in a K,V map fashion.
 *
 * @param <T> the type of the value
 * @param <V> the environment entry type
 */
public interface Environment<T, V extends EnvironmentEntry> {

    /**
     * Retrieves the {@link EnvironmentEntry entry} with the given name
     *
     * @param name the name
     * @return the value
     */
    Optional<V> getValue(String name);

    /**
     * Checks a {@link EnvironmentEntry entry} with the given name is in this environment
     *
     * @param name the name
     * @return true if a value exists with the name
     */
    boolean contains(String name);

    /**
     * Removes the {@link EnvironmentEntry entry} with the given name from this environment
     *
     * @param name the name of the value
     * @return true if a value was found and removed
     */
    boolean remove(String name);

    /**
     * Creates a new {@link EnvironmentEntry entry} with the given name
     *
     * @param name the name
     * @return the new value
     */
    default V create(String name) {
        return create(name, null);
    }

    /**
     * Creates a new {@link EnvironmentEntry entry} with the given name and value
     *
     * @param name  the name
     * @param value the value
     * @return the new value
     */
    V create(String name, T value);

    /**
     * Creates a {@link EnvironmentEntry entry}, or gets one if the given name if it already exists
     *
     * @param name the name
     * @return the value
     */
    default V createOrGet(String name) {
        return getValue(name).orElseGet(() -> create(name));
    }
}
