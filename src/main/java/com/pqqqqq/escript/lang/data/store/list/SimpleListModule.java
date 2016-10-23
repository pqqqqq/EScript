package com.pqqqqq.escript.lang.data.store.list;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.mutable.SimpleMutableValue;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.data.store.Module;
import com.pqqqqq.escript.lang.data.store.map.SimpleMapModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kevin on 2016-10-14.
 * <pre>
 * A literal list. This is a {@link LiteralStore literal store} implementation with Integer keys.
 * This class will account for ALL base-1 discrepancies
 * </pre>
 */
public final class SimpleListModule implements ListModule {
    private final List<MutableValue<Literal>> list;

    /**
     * Creates an empty literal list instance
     *
     * @return the new instance
     */
    public static SimpleListModule from() {
        return new SimpleListModule();
    }

    /**
     * Creates a new literal list from the given collection of {@link MutableValue mutable values}
     *
     * @param col the collection
     * @return the new list
     */
    public static SimpleListModule from(Collection<MutableValue<Literal>> col) {
        SimpleListModule list = from();
        col.forEach(list.list::add); // EVERY value has to go through this
        return list;
    }

    /**
     * Creates a new literal list from the given literal list
     *
     * @param oldList the list
     * @return the new list
     */
    public static SimpleListModule from(SimpleListModule oldList) {
        SimpleListModule list = from();
        oldList.forEach(list::add); // EVERY value has to go through this
        return list;
    }

    /**
     * Creates a new literal list from the given collection of literals
     *
     * @param col the collection
     * @return the new list
     */
    public static SimpleListModule fromLiterals(Collection<? extends Literal> col) {
        SimpleListModule list = from();
        col.stream().map(SimpleMutableValue::from).forEach(list::add); // EVERY value has to go through this
        return list;
    }

    private SimpleListModule() { // Can only create empty lists through constructor
        this.list = new ArrayList<>();
    }

    @Override
    public MutableValue<Literal> add(MutableValue<Literal> value) {
        list.add(value);
        return value;
    }

    @Override
    public MutableValue<Literal> get(Integer key) {
        try {
            return list.get(--key); // Base-1
        } catch (IndexOutOfBoundsException e) {
            return null; // Don't care for these
        }
    }

    @Override
    public MutableValue<Literal> add(Integer key, MutableValue<Literal> value) {
        // We have to ensure we have the right capacity, otherwise we'll fill it with mutable values with empty literal values

        if (--key >= size()) { // Account for base-1
            for (int i = size(); i <= key; i++) {
                list.add(SimpleMutableValue.from(Literal.EMPTY));
            }
        }

        list.set(key, value);
        return value;
    }

    @Override
    public MutableValue<Literal> remove(Integer key) {
        return list.remove((int) --key); // Base-1
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Literal value) {
        return list.contains(value);
    }

    @Override
    public boolean contains(Integer key) {
        return list.size() > key;
    }

    @Override
    public Iterator<MutableValue<Literal>> iterator() {
        return list.iterator();
    }

    @Override
    public List<Literal> toImmutable() {
        List<Literal> immutable = new ArrayList<>();
        list.stream().map(MutableValue::getValue).forEach(immutable::add);
        return immutable;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    /**
     * Maps the list into a {@link Module map module}.
     * The key for each {@link MutableValue mutable value} is given by a String map, linked by its ordinal.
     * There are no repercussions for unequal sizing of the map and list, once one ends the mapping procedure will halt.
     * <p>
     * <p>
     * For example, for a list: <i>{"minecraft:stone", 5, 0}</i>
     * And a string map: <i>["type", "amount", "data"]</i>
     * Would return the given map: <i>{type="minecraft:stone", amount=5, data=0}</i>
     * <p>
     * <p>
     * This is the reciprocal of calling {@link SimpleMapModule#flatten(String...)} for a map.
     *
     * @param stringMap the string map
     * @return the new literal map
     */
    public Module<String> map(String... stringMap) {
        SimpleMapModule map = SimpleMapModule.from();
        for (int i = 0; i < Math.min(stringMap.length, size()); i++) {
            map.add(stringMap[i], get(i));
        }

        return map;
    }
}
