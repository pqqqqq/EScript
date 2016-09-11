package com.pqqqqq.escript.lang.data.variable;

import com.google.common.base.Objects;
import com.pqqqqq.escript.lang.data.Datum;
import com.pqqqqq.escript.lang.data.Literal;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-09-02.
 *
 * <pre>
 * A memory section that stores an instance of a {@link Datum}
 * </pre>
 *
 * @param <T> the datum type
 */
public class Variable<T extends Datum> {
    private final String name;
    private final Environment environment;
    private T value;

    protected Variable(String name, Environment environment) {
        this(name, environment, null);
    }


    protected Variable(String name, Environment environment, T value) {
        this.name = checkNotNull(name, "Name cannot be null.");
        this.environment = checkNotNull(environment, "Environment cannot be null.");
        setValue(value);
    }

    /**
     * Gets the name of this variable
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the containing {@link Environment} for this variable
     *
     * @return the environment
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Gets the generic {@link Datum} value
     *
     * @return the varible's value
     */
    public T getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public void setValue(T value) {
        this.value = Optional.ofNullable(value).orElse((T) Literal.EMPTY); // Fixed unchecked cast? How?
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Variable)) {
            return false;
        }

        Variable other = (Variable) obj;
        return other.getName().equals(name) && other.getEnvironment().equals(environment); // Variables in different environments can have the same name
    }

    @Override
    public int hashCode() { // Important for sets/maps
        return Objects.hashCode(this.name, this.environment); // Variables in different environments can have the same name
    }
}
