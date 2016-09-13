package com.pqqqqq.escript.lang.registry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * A class that catalogues a given type
 *
 * @param <T> the registry entry type
 */
public abstract class Registry<T extends RegistryEntry> implements Iterable<T> {
    private final Class<? extends T> typeClass;
    private final List<T> registry = new ArrayList<>();

    @SuppressWarnings("unchecked") // Reflection = bleh
    protected Registry(Class<? extends T> typeClass) {
        this.typeClass = typeClass;

        // Populate registry
        for (Field field : getClass().getFields()) { // Use reflection to find given types
            try {
                if (Modifier.isStatic(field.getModifiers())) { // Field must be static
                    Object fieldValue = field.get(null);
                    if (getTypeClass().isInstance(fieldValue)) {
                        registry.add((T) fieldValue);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the generic type's class
     *
     * @return the type class
     */
    public Class<? extends T> getTypeClass() {
        return typeClass;
    }

    /**
     * Returns the registry {@link List} for the given type
     *
     * @return the registry list
     */
    public List<T> registry() {
        return registry;
    }

    @Override
    public Iterator<T> iterator() {
        return registry.iterator();
    }
}
