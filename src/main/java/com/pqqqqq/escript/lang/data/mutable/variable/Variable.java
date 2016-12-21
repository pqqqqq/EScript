package com.pqqqqq.escript.lang.data.mutable.variable;

import com.google.common.base.Objects;
import com.pqqqqq.escript.lang.data.Datum;
import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.env.EnvironmentEntry;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.data.serializer.Serializers;

import java.util.Optional;

import static com.pqqqqq.escript.lang.util.Assertions.assertNotNull;

/**
 * A {@link MutableValue mutable value} that stores an instance of a {@link Datum}
 */
public class Variable implements MutableValue<Datum>, EnvironmentEntry<String> {
    private final String name;
    private final VariableEnvironment environment;
    private Datum value;

    protected Variable(String name, VariableEnvironment environment) {
        this(name, environment, null);
    }

    protected Variable(String name, VariableEnvironment environment, Datum value) {
        this.name = assertNotNull(name, "Name cannot be null.");
        this.environment = assertNotNull(environment, "Environment cannot be null.");
        setValue(value);
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public VariableEnvironment getEnvironment() {
        return environment;
    }

    @Override
    public Datum getValue() {
        return value;
    }

    @Override
    public boolean setValue(Datum value) {
        this.value = Optional.ofNullable(value).orElse(Literal.EMPTY); // Fixed unchecked cast? How?
        return true;
    }

    @Override
    public Serializer<Datum> getSerializer() {
        return Serializers.DATUM;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Variable)) {
            return false;
        }

        Variable other = (Variable) obj;
        return other.getId().equals(name) && other.getEnvironment().equals(environment); // Variables in different environments can have the same name
    }

    @Override
    public int hashCode() { // Important for sets/maps
        return Objects.hashCode(this.name, this.environment); // Variables in different environments can have the same name
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this) // Easy toString
                .add("Name", getId())
                .add("Environment", getEnvironment().getClass().getName())
                .add("Value", value.toString())
                .toString();
    }

    @Override
    public MutableValue<Datum> copy() {
        throw new AbstractMethodError("Variables are not copyable");
    }
}
