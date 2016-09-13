package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kevin on 2016-09-13.
 * <p>
 * <pre>
 * A list {@link DatumContainer container}, dedicated to resolving lists at runtime
 * </pre>
 */
public class ListContainer implements DatumContainer {
    private final Collection<DatumContainer> items;

    /**
     * Creates a new list containers with the given items
     *
     * @param items the items
     */
    public ListContainer(Collection<DatumContainer> items) {
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
        List<Literal> resolved = new ArrayList<>();
        items.stream().map(container -> container.resolve(ctx)).forEach(resolved::add);
        return Literal.fromObject(resolved);
    }
}
