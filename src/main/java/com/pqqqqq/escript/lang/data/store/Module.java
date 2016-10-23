package com.pqqqqq.escript.lang.data.store;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.mutable.SimpleMutableValue;

/**
 * An interface, denoting a storage module. In general there are two modules: maps and list modules
 */
public interface Module<K> {

    /**
     * Gets the {@link MutableValue mutable literal value} for the given key
     *
     * @param key the key
     * @return the mutable value, or null
     */
    MutableValue<Literal> get(K key);

    /**
     * <pre>
     * Gets the {@link Literal literal value} for the given key.
     * This is equivalent to calling:
     * <code>get(key).getValue()</code>
     * </pre>
     *
     * @param key the key
     * @return the literal value
     */
    default Literal getLiteral(K key) {
        return get(key).getValue();
    }

    /**
     * Attempts to retrieve the {@link MutableValue mutable value}, according to {@link #get(Object)}
     * If the value cannot be retrieved, the entry is created with the default value specified.
     *
     * @param key the key
     * @param def the default value of the created value if creation is necessary
     * @return the created, or retrieved value
     */
    default MutableValue<Literal> getOrCreate(K key, Literal def) {
        MutableValue<Literal> value = get(key);
        if (value == null) {
            value = SimpleMutableValue.from(def);
            add(key, value);
        }

        return value;
    }

    /**
     * <pre>
     * Adds a K,V pair of {@link MutableValue mutable literal value} for the given key.
     * EVERY entry added (including those during instantiation) that is present in the map must have been added by this method.
     * </pre>
     *
     * @param key   the key
     * @param value the value
     * @return the value that was added
     */
    MutableValue<Literal> add(K key, MutableValue<Literal> value);

    /**
     * <pre>
     * Adds a new K,V pair according to {@link #add(Object, MutableValue)}
     * This wraps the given literal in a {@link SimpleMutableValue}
     * </pre>
     *
     * @param key   the key
     * @param value the literal value
     * @return the value that was added
     */
    default MutableValue<Literal> add(K key, Literal value) {
        return add(key, SimpleMutableValue.from(value));
    }

    /**
     * Attempts to remove a K,V pair with the given key
     *
     * @param key the key
     * @return the mutable value that was removed, or null if none
     */
    MutableValue<Literal> remove(K key);

    /**
     * Gets the size of the map (number of entries)
     *
     * @return the size
     */
    int size();

    /**
     * Checks if the module is empty.
     * This is analogous to calling <code>size() == 0</code>
     *
     * @return true if empty
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Checks if the store contains the given {@link Literal literal value}
     *
     * @param value the value
     * @return true if the value is within the store
     */
    boolean contains(Literal value);

    /**
     * Checks if the store contains the given key
     *
     * @param key the key
     * @return true if the store has the key
     */
    boolean contains(K key);
}
