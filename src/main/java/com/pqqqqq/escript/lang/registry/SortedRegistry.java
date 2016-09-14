package com.pqqqqq.escript.lang.registry;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kevin on 2016-09-14.
 * <p>
 * <pre>
 * A sorted {@link Registry registry}, whose registry is {@link Collections#sort(List) sorted naturally}.
 * The {@link RegistryEntry registry entries} therefore must be {@link Comparable comparable}
 * </pre>
 */
public abstract class SortedRegistry<T extends RegistryEntry & Comparable> extends Registry<T> {

    @SuppressWarnings("unchecked")
    protected SortedRegistry(Class<? extends T> typeClass) {
        super(typeClass);
        Collections.sort(registry());
    }
}
