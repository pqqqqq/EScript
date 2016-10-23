package com.pqqqqq.escript.lang.data.store.map;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.data.store.Module;
import com.pqqqqq.escript.lang.data.store.list.SimpleListModule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Kevin on 2016-10-14.
 * <pre>
 * A literal map. This is a {@link LiteralStore literal store} implementation with String keys
 * </pre>
 */
public final class SimpleMapModule implements MapModule {
    private final Map<String, MutableValue<Literal>> map;

    /**
     * Creates a new, empty literal map
     *
     * @return the new instance
     */
    public static SimpleMapModule from() {
        return new SimpleMapModule();
    }

    /**
     * Creates a new literal map from the existing {@link Map map}
     *
     * @param map the map
     * @return the new instance
     */
    public static SimpleMapModule from(Map<String, MutableValue<Literal>> map) {
        return new SimpleMapModule(map);
    }

    private SimpleMapModule() {
        this.map = new HashMap<>();
    }

    private SimpleMapModule(Map<String, MutableValue<Literal>> map) {
        this();
        map.forEach(this::add); // EVERY value must go through this
    }

    @Override
    public MutableValue<Literal> get(String key) {
        return map.get(key);
    }

    @Override
    public MutableValue<Literal> add(String key, MutableValue<Literal> value) {
        checkState(key.matches("^[a-zA-z](.*?)"), "Map entries must begin with an alphabetical character");
        map.put(key, value);
        return value;
    }

    @Override
    public MutableValue<Literal> remove(String key) {
        return map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean contains(Literal value) {
        return map.containsValue(value);
    }

    @Override
    public boolean contains(String key) {
        return map.containsValue(key);
    }

    @Override
    public Iterator<Map.Entry<String, MutableValue<Literal>>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    /**
     * Flattens this map into a {@link Module list module}.
     * Flattening the map will create a list that places each value at the ordinal specified by the String map.
     * <p>
     * <p>
     * For example, for a map: <i>{key="value", key2="value2"}</i>
     * And a string map: <i>["key2", "key"]</i>
     * Would return the following list: {"value2", "value"}
     * <p>
     * <p>
     * This is the reciprocal of calling {@link SimpleListModule#map(String...)} for a list.
     *
     * @param stringMap the string map
     * @return the new literal list
     * @throws IllegalArgumentException if a key given in the String map is not present in the map
     */
    public Module<Integer> flatten(String... stringMap) {
        SimpleListModule list = SimpleListModule.from();
        for (String string : stringMap) {
            MutableValue<Literal> value = Optional.ofNullable(get(string)).orElseThrow(() -> new IllegalArgumentException(String.format("Missing key during flattening: %s", string)));
            list.add(value);
        }

        return list;
    }
}