package com.pqqqqq.escript.lang.data.store;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.Pointer;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.mutable.SimpleMutableValue;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.data.store.list.ListModule;
import com.pqqqqq.escript.lang.data.store.list.SimpleListModule;
import com.pqqqqq.escript.lang.data.store.map.EntryReplicate;
import com.pqqqqq.escript.lang.data.store.map.MapModule;
import com.pqqqqq.escript.lang.data.store.map.SimpleMapModule;
import com.pqqqqq.escript.lang.util.Copyable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A storage module for {@link Literal literals}. This contains a {@link Module map module} and a {@link Module list module}.
 */
public class LiteralStore implements Copyable<LiteralStore> {
    private static final Supplier<Literal> EMPTY_STORE_LITERAL = () -> Literal.fromObject(empty());

    private final MapModule mapModule;
    private final ListModule listModule;

    /**
     * Creates a new empty literal store
     *
     * @return the new instance
     */
    public static LiteralStore empty() {
        return builder().build();
    }

    /**
     * Gets the {@link Supplier supplier} for empty literal store creation
     *
     * @return the supplier
     */
    public static Supplier<Literal> emptyLiteral() {
        return EMPTY_STORE_LITERAL;
    }

    /**
     * Creates a new literal store builder
     *
     * @return the new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    private LiteralStore(MapModule mapModule, ListModule listModule) {
        this.mapModule = mapModule;
        this.listModule = listModule;
    }

    /**
     * Gets the {@link MapModule map module}.
     * The map module is a module with String keys.
     *
     * @return the map module
     */
    public MapModule getMapModule() {
        return mapModule;
    }

    /**
     * Gets the {@link ListModule list module}.
     * A list module has Integer keys (ordinals).
     *
     * @return the list module
     */
    public ListModule getListModule() {
        return listModule;
    }

    /**
     * Attempts to retrieve the {@link MutableValue mutable value} from one of the {@link MapModule map} or {@link ListModule list} modules of this literal store.
     * The method takes a {@link Literal literal} key, and determines whether to retrieve data from the map or list according to the following criteria:
     * <p>
     * <ul>
     * <li>If the literal has a {@link Pointer pointer} value, it pertains to a <strong>list</strong> module</li>
     * <li>If the literal has a value that CAN be parsed into an number, it pertains to a <strong>list</strong> module</li>
     * <li>Otherwise, the key pertains to a <strong>map</strong> value</li>
     * </ul>
     *
     * @param key the literal key
     * @return the mutable value, or null if none found
     */
    public MutableValue<Literal> get(Literal key) {
        if (key.isPointer()) { // If it's a pointer, it has to do with the list
            return get(key.asPointer().resolve(this)); // Recursive
        } else {
            try { // We'll try to consider the index a number. Since keys can't start with an integer, this should work well
                return getListModule().get(key.asNumber().intValue());
            } catch (NumberFormatException e) { // Otherwise, treat it as a map index
                return getMapModule().get(key.asString());
            }
        }
    }

    /**
     * Attempts to retrieve the {@link MutableValue mutable value}, according to {@link #get(Literal)}
     * If the value cannot be retrieved, the entry is created with the default value specified.
     *
     * @param key the literal key
     * @param def the default value, if creation is necessary
     * @return the (new or existing) mutable value
     */
    public MutableValue<Literal> getOrCreate(Literal key, Literal def) {
        // Instead of calling the get() method and finding out whether to add to list or map, just rewrite code in this case with subtle differences

        if (key.isPointer()) { // If it's a pointer, it has to do with the list
            return getOrCreate(key.asPointer().resolve(this), def); // Recursive
        } else {
            try { // We'll try to consider the index a number. Since keys can't start with an integer, this should work
                return getListModule().getOrCreate(key.asNumber().intValue(), def);
            } catch (NumberFormatException e) { // Otherwise, treat it as a map index
                return getMapModule().getOrCreate(key.asString(), def);
            }
        }
    }

    /**
     * Collapses all entries of this store from the {@link ListModule list module} into the {@link MapModule map module} based on the keyMap given.
     * Collapsing will therefore attempt to streamline all data possible.
     * <p>
     * <p>Each key in the key map is enumerated, and its value in the map is checked. If there is (1) no entry at that key <strong>OR</strong>
     * (2) the value at that key is {@link Literal#EMPTY}, then the list module is {@link ListModule#pop() popped} and the value is set
     * at the map key.
     * <p>
     * <p>Importantly, collapsing the store will only go as far as its keyMap takes it. That means if there are <i>x</i> entries in the list module,
     * and the size of the keyMap is less than <i>x</i>, there <strong>will</strong> still be values in the list.
     * <p>
     * <p>A {@link Copyable#copy()} of the literal store is created, and even if the list module is empty, a copy will be returned.
     *
     * @param keyMap the key map
     * @return the new literal store
     */
    public LiteralStore collapseMap(String... keyMap) {
        LiteralStore copy = copy();

        // Ensure we're not doing this for nothing
        if (copy.getListModule().isEmpty()) {
            return copy; // We would be.
        }

        for (String key : keyMap) {
            MutableValue<Literal> value = copy.getMapModule().getOrCreate(key, Literal.EMPTY);
            if (value.getValue().isEmpty()) {
                MutableValue<Literal> pop = copy.getListModule().pop();
                if (pop == null) {
                    break; // No reason to continue if list is empty
                } else {
                    value.setValue(pop);
                }
            }
        }

        return copy;
    }

    /**
     * Attempts to add to one of the {@link MapModule map} or {@link ListModule list} modules of this literal store.
     * The method takes a {@link Literal literal} value, and adds the value to either the map or list module based on the following criteria:
     * <p>
     * <ul>
     * <li>If the literal's value is a {@link EntryReplicate map entry replicate}, then the replicate will be added to the <strong>map</strong> module</li>
     * <li>Otherwise, the literal will be added to the <strong>list</strong> module</li>
     * </ul>
     *
     * @param value the literal value
     * @return the added value
     */
    public MutableValue<Literal> add(Literal value) {
        if (value.isType(EntryReplicate.class)) { // If it's an entry replicate, we'll treat it as a map entry
            EntryReplicate entryReplicate = value.rawAs(EntryReplicate.class);
            return getMapModule().add(entryReplicate.getKey(), entryReplicate.getValue());
        } else { // Otherwise, it's a simple list entry
            return getListModule().add(value);
        }
    }

    /**
     * Checks if the given {@link Literal literal key} is present in either the map or list modules.
     * This is analogous to <code>{@link #get(Literal)} != null</code>
     *
     * @param key the key
     * @return true if the key is within the store
     */
    public boolean contains(Literal key) {
        return get(key) != null;
    }

    /**
     * Adds all the list and map module components from another store to this one.
     * Using this method will {@link Copyable#copy()} all entries passed through.
     *
     * @param other the other store
     */
    public void addAll(LiteralStore other) {
        other.getListModule().forEach(item -> getListModule().add(item.copy())); // Adds/clones list module
        other.getMapModule().forEach((k, v) -> getMapModule().add(k, v.copy())); // Adds/clones map module
    }

    /**
     * Check if both the list and map modules are {@link Module#isEmpty() empty}
     *
     * @return true if both are empty
     */
    public boolean isEmpty() {
        return getListModule().isEmpty() && getMapModule().isEmpty();
    }

    @Override
    public LiteralStore copy() {
        // Just make an empty store and add it directly
        LiteralStore clone = empty();
        clone.addAll(this);
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("{"); // Add opening brace

        if (!getListModule().isEmpty()) {
            String listString = getListModule().toString();
            builder.append(StringUtils.removeEnd(StringUtils.removeStart(listString, "{"), "}")); // Append list module first, remove braces
        }

        if (!getMapModule().isEmpty()) {
            String listString = getMapModule().toString();
            builder.append(StringUtils.removeEnd(StringUtils.removeStart(listString, "{"), "}")); // Append map module last, remove braces
        }

        return builder.append("}").toString(); // Add closing brace
    }

    /**
     * The literal store builder
     */
    public static class Builder {
        private List<MutableValue<Literal>> list = new ArrayList<>();
        private Map<String, MutableValue<Literal>> map = new HashMap<>();

        private Builder() {
        }

        /**
         * Adds the collection of {@link MutableValue mutable values} to the list
         *
         * @param values the mutable values to add
         * @return this builder, for chaining
         */
        public Builder listMV(Iterable<MutableValue<Literal>> values) {
            values.forEach(list::add);
            return this;
        }

        /**
         * Adds the collection of {@link Literal literals} to the list
         *
         * @param literals the literals to add
         * @return this builder, for chaining
         */
        public Builder listL(Iterable<Literal> literals) {
            literals.forEach(literal -> list.add(SimpleMutableValue.from(literal, Serializers.SELF)));
            return this;
        }

        /**
         * Adds the map of {@link MutableValue mutable values} to the larger map module
         *
         * @param values the mutable value K,V's to add
         * @return this builder, for chaining
         */
        public Builder mapMV(Map<String, MutableValue<Literal>> values) {
            map.putAll(values);
            return this;
        }

        /**
         * Adds the map of {@link Literal literals} to the larger map module
         *
         * @param literals the literal K,V's to add
         * @return this builder, for chaining
         */
        public Builder mapL(Map<String, Literal> literals) {
            literals.forEach((k, v) -> map.put(k, SimpleMutableValue.from(v, Serializers.SELF)));
            return this;
        }

        /**
         * Builds the new literal store
         *
         * @return the new literal store instance
         */
        public LiteralStore build() {
            return new LiteralStore(SimpleMapModule.from(map), SimpleListModule.from(list));
        }
    }
}
