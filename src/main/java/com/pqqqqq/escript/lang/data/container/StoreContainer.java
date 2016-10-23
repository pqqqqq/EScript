package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.line.Context;

import java.util.Collection;

/**
 * Created by Kevin on 2016-09-13.
 * <p>
 * <pre>
 * A store {@link DatumContainer container}, dedicated to resolving stores at runtime
 * </pre>
 */
public class StoreContainer implements DatumContainer {
    private final Collection<DatumContainer> items;

    /**
     * Creates a new store container with the given items
     *
     * @param items the items
     */
    public StoreContainer(Collection<DatumContainer> items) {
        this.items = items;
    }

    /**
     * Gets the {@link Collection collection} of containers
     *
     * @return the containers
     */
    public Collection<DatumContainer> getItems() {
        return items;
    }

    @Override
    public Literal resolve(Context ctx) {
        LiteralStore store = LiteralStore.empty();
        items.stream().map(container -> container.resolve(ctx)).forEach(store::add);
        return Literal.fromObject(store);
    }
}
