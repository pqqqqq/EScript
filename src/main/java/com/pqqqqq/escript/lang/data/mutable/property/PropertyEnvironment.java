package com.pqqqqq.escript.lang.data.mutable.property;

import com.pqqqqq.escript.lang.data.env.Environment;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * An {@link Environment environment}, where {@link Property properties} can be stored.
 * Property environments are abstract, and therefore must be a superclass of some other class.
 * <p>
 * <p>Properties are NOT case-sensitive.
 */
public abstract class PropertyEnvironment implements Environment<Object, Property> {
    private final Set<Property> properties = new HashSet<>();

    @Override
    public Optional<Property> getValue(String name) {
        return properties.stream().filter(property -> property.getName().equals(name)).findFirst();
    }

    /**
     * Retrieves the {@link Property property}'s value with the given name
     *
     * @param name the name
     * @param <T>  the property value's generic type
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getValue(String name, Class<T> clazz) {
        return properties.stream().filter(property -> property.getName().equalsIgnoreCase(name)).findFirst().filter(property -> clazz.isInstance(property.getRawValue())).map(clazz::cast);
    }

    @Override
    public boolean contains(String name) {
        return properties.stream().anyMatch(property -> property.getName().equalsIgnoreCase(name));
    }

    @Override
    public boolean remove(String name) {
        return properties.removeIf(property -> property.getName().equalsIgnoreCase(name));
    }

    @Override
    public Property create(String name, Object value) {
        Property property = new Property(name, this, value);
        this.properties.add(property);
        return property;
    }

    /**
     * Creates all properties in a given property {@link Map bus}
     *
     * @param propertyBus the property bus
     */
    public void createAll(Map<String, Object> propertyBus) {
        propertyBus.forEach(this::create);
    }
}
