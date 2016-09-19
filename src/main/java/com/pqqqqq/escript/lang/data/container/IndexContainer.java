package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;

/**
 * Created by Kevin on 2016-09-19.
 * <pre>
 * An index {@link DatumContainer container}, which gets the index of a literal
 * If the literal is an array, it gets the literal at the given index
 * Otherwise, the literal is treated as a string, and retrieves the character at the given index
 * </pre>
 */
public class IndexContainer implements DatumContainer {
    private final DatumContainer container;
    private final DatumContainer index;

    /**
     * Creates a new index container with the given {@link DatumContainer container and index}
     *
     * @param container the container (array or string)
     * @param index     the index
     */
    public IndexContainer(DatumContainer container, DatumContainer index) {
        this.container = container;
        this.index = index;
    }

    /**
     * Gets the {@link DatumContainer container}
     *
     * @return the container
     */
    public DatumContainer getContainer() {
        return container;
    }

    /**
     * Gets the {@link DatumContainer unresolved index}
     *
     * @return the index
     */
    public DatumContainer getIndex() {
        return index;
    }

    @Override
    public Literal resolve(Context ctx) {
        Literal container = getContainer().resolve(ctx);
        Literal index = getIndex().resolve(ctx);

        return container.fromIndex(index);
    }
}
