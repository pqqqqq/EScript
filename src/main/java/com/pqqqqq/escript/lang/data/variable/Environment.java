package com.pqqqqq.escript.lang.data.variable;

import com.pqqqqq.escript.lang.data.Datum;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * An environment, where {@link Variable variables} can be stored
 * Environments are abstract, and therefore must be a superclass of some other class
 * </pre>
 */
public abstract class Environment {
    private final Set<Variable> variables = new HashSet<>();

    /**
     * Retrieves the {@link Variable} with the given name
     *
     * @param name the name
     * @return the variable
     */
    public Optional<Variable> getVariable(String name) {
        return variables.stream().filter(variable -> variable.getName().equals(name)).findFirst();
    }

    /**
     * Checks a variable with the given name is in this environment
     *
     * @param name the name
     * @return true if a variable exists with the name
     */
    public boolean contains(String name) {
        return variables.stream().anyMatch(variable -> variable.getName().equals(name)); // getVariable(name).isPresent() is possible, but may be more expensive
    }

    /**
     * Removes the variable with the given name from this environment
     *
     * @param name the name of the variable
     * @return true if a variable was found and removed
     */
    public boolean remove(String name) {
        return variables.removeIf(variable -> variable.getName().equals(name));
    }

    /**
     * Creates a new {@link Variable variable} with the given name
     *
     * @param name the name
     * @return the new variable
     */
    public Variable create(String name) {
        return create(name, null);
    }

    /**
     * Creates a new {@link Variable} with the given name and value
     *
     * @param name  the name
     * @param value the value
     * @return the new variable
     */
    public Variable create(String name, Datum value) {
        Variable variable = new Variable(name, this, value);
        this.variables.add(variable);
        return variable;
    }

    /**
     * Creates a variable with the given value, or if one already exists sets the preexisting one to that value
     *
     * @param name  the name of the variable
     * @param value the new value
     * @return the variable, ensured to have the given value
     */
    public Variable createOrSet(String name, Datum value) {
        Optional<Variable> variable = getVariable(name);

        if (!variable.isPresent()) {
            return create(name, value);
        } else {
            variable.get().setValue(value);
            return variable.get();
        }
    }

    /**
     * Creates a variable, or gets one if the given name if it already exists
     *
     * @param name the name
     * @return the variable
     */
    public Variable createOrGet(String name) {
        return getVariable(name).orElseGet(() -> create(name));
    }
}
