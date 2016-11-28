package com.pqqqqq.escript.lang.data.mutable.property;

import com.pqqqqq.escript.lang.data.Datum;
import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.env.Environment;
import com.pqqqqq.escript.lang.data.env.EnvironmentEntry;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.line.Context;

import static com.pqqqqq.escript.lang.util.Assertions.assertNotNull;

/**
 * A property is a value retrieved from a Sponge trigger.
 * Properties have Object values, and {@link Serializer#serialize(Object) serialized} {@link Literal literal} values.
 * <p>
 * <p>Properties enforce literal {@link MutableValue value} as a generic type, but their original values can be retrieved.
 *
 * @param <T> the property's raw type
 */
public class Property<T> implements EnvironmentEntry, Datum {
    private final String name;
    private final PropertyEnvironment environment;

    private final T rawValue;
    private final Serializer<T> serializer;
    private final Literal value;

    protected Property(String name, PropertyEnvironment environment, T value) {
        this.name = assertNotNull(name, "Name cannot be null.");
        this.environment = assertNotNull(environment, "Environment cannot be null.");
        this.rawValue = assertNotNull(value, "Property value cannot be null.");

        this.serializer = Serializers.instance().getSerializer(value).get();
        this.value = this.serializer.serialize(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public Literal resolve(Context ctx) {
        return value;
    }

    /**
     * Gets the raw value for this property.
     *
     * @return the raw value
     */
    public T getRawValue() {
        return rawValue;
    }

    /**
     * Gets the {@link Serializer literal serializer}
     *
     * @return the serializer
     */
    public Serializer<T> getSerializer() {
        return serializer;
    }
}
