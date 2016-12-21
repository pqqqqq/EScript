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
public abstract class PropertyEnvironment implements Environment<Object, PropertyType, Property<?>> {
    private final Set<Property<?>> properties = new HashSet<>();

    @Override
    public Optional<Property<?>> getValue(PropertyType id) {
        return properties.stream().filter(property -> property.getId().equals(id)).findFirst();
    }

    /**
     * Retrieves the {@link Property property}'s value with the given id
     *
     * @param id the id
     * @param <T>  the property value's generic type
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getValue(PropertyType id, Class<T> clazz) {
        return properties.stream().filter(property -> property.getId().equals(id)).findFirst().filter(property -> clazz.isInstance(property.getRawValue())).map(Property::getRawValue).map(clazz::cast);
    }

    @Override
    public boolean contains(PropertyType id) {
        return properties.stream().anyMatch(property -> property.getId().equals(id));
    }

    @Override
    public boolean remove(PropertyType id) {
        return properties.removeIf(property -> property.getId().equals(id));
    }

    @Override
    public Property create(PropertyType id, Object value) {
        Property property = new Property(id, this, value);
        this.properties.add(property);
        return property;
    }

    /**
     * Creates all properties in a given property {@link Map bus}
     *
     * @param propertyBus the property bus
     */
    public void createAll(Map<PropertyType, Object> propertyBus) {
        propertyBus.forEach(this::create);
    }
}
