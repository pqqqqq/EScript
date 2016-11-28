package com.pqqqqq.escript.lang.data.mutable.variable;

import com.pqqqqq.escript.lang.data.Datum;
import com.pqqqqq.escript.lang.data.env.Environment;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * An {@link Environment environment}, where {@link Variable variables} can be stored.
 * Variable environments are abstract, and therefore must be a superclass of some other class.
 */
public abstract class VariableEnvironment implements Environment<Datum, Variable> {
    private final Set<Variable> variables = new HashSet<>();

    @Override
    public Optional<Variable> getValue(String name) {
        return variables.stream().filter(variable -> variable.getName().equals(name)).findFirst();
    }

    @Override
    public boolean contains(String name) {
        return variables.stream().anyMatch(variable -> variable.getName().equals(name)); // getVariable(name).isPresent() is possible, but may be more expensive
    }

    @Override
    public boolean remove(String name) {
        return variables.removeIf(variable -> variable.getName().equals(name));
    }

    @Override
    public Variable create(String name, Datum value) {
        Variable variable = new Variable(name, this, value);
        this.variables.add(variable);
        return variable;
    }

    /**
     * Creates a {@link MutableValue mutable value} with the given value, or if one already exists sets the preexisting one to that value
     *
     * @param name  the name of the value
     * @param value the new value
     * @return the value, ensured to have the given value
     */
    public Variable createOrSet(String name, Datum value) {
        Optional<Variable> variable = getValue(name);

        if (!variable.isPresent()) {
            return create(name, value);
        } else {
            variable.get().setValue(value);
            return variable.get();
        }
    }

    /**
     * Creates a bus of {@link MutableValue mutable values} with their respected values, or if they already exist sets the preexisting one to that value
     *
     * @param valueBus the value {@link Map bus}
     */
    public void createOrSet(Map<String, Datum> valueBus) {
        valueBus.forEach(this::createOrSet);
    }
}
