package com.pqqqqq.escript.lang.data.store.map;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.store.LiteralStore;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

/**
 * A literal map entry replicate. This is only used as a reference, and will be deconstructed when put into a {@link LiteralStore literal store}
 */
public class EntryReplicate {
    private final String key;
    private final Literal value;

    /**
     * Creates a new literal map entry
     *
     * @param key   the key
     * @param value the value
     * @return the new entry
     */
    public static EntryReplicate from(String key, Literal value) {
        checkState(key.matches("^[a-zA-z](.*?)"), "Map entries must begin with an alphabetical character");
        return new EntryReplicate(key, value);
    }

    private EntryReplicate(String key, Literal value) {
        this.key = key;
        this.value = Optional.ofNullable(value).orElse(Literal.EMPTY);
    }

    /**
     * The string key
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * The literal value
     *
     * @return the value
     */
    public Literal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof EntryReplicate && ((EntryReplicate) obj).getKey().equals(key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return key + "=" + value.asString();
    }
}
