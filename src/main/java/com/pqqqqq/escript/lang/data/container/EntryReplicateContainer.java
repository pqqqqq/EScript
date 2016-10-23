package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.store.map.EntryReplicate;
import com.pqqqqq.escript.lang.line.Context;

/**
 * A entry replicate {@link DatumContainer container}, dedicated to resolving entry replicates at runtime
 */
public class EntryReplicateContainer implements DatumContainer {
    private final String key;
    private final DatumContainer value;

    /**
     * <pre>
     * Creates a new entry replicate with the given key and value
     * Keys are resolved at compile time, as they are not sequenced
     * Values are resolved later
     * </pre>
     *
     * @param key   the key
     * @param value the value
     */
    public EntryReplicateContainer(String key, DatumContainer value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Literal resolve(Context ctx) {
        EntryReplicate entry = EntryReplicate.from(key, value.resolve(ctx));
        return Literal.fromObject(entry);
    }
}
