package com.pqqqqq.escript.lang.trigger.cause;

import com.pqqqqq.escript.lang.registry.RegistryEntry;
import com.pqqqqq.escript.lang.script.Properties;
import com.pqqqqq.escript.lang.trigger.Trigger;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * A cause is a type of action that occurs in minecraft
 * {@link Trigger Triggers} make use of causes, with any special properties attached
 * Each cause with also have a list of triggers that use this cause, and when activated will activate each trigger
 * </pre>
 */
public class Cause implements RegistryEntry {
    private final String[] names;
    private final Set<Trigger> triggers = new HashSet<>();

    protected Cause(String... names) {
        this.names = names;
    }

    /**
     * Gets an array of names that are valid for this cause
     *
     * @return the names
     */
    public String[] getNames() {
        return names;
    }

    /**
     * Checks if this name is valid for the given cause
     *
     * @param check the name to check
     * @return true if its valid to this cause
     */
    public boolean hasName(String check) {
        for (String name : names) {
            if (name.equalsIgnoreCase(check)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the {@link Set} of {@link Trigger}s to be triggered with this cause is activated
     *
     * @return the trigger set
     */
    public Set<Trigger> getTriggers() {
        return triggers;
    }

    /**
     * Triggers the cause, which in turn triggers all of its {@link Trigger triggers}
     */
    public void trigger() { // TODO variable stuff
        getTriggers().forEach(Trigger::trigger);
    }

    /**
     * Triggers the cause based on the given {@link Properties properties}, which in turn triggers all of its {@link Trigger triggers}
     *
     * @param properties the properties
     */
    public void trigger(Properties properties) {
        getTriggers().forEach(trigger -> trigger.trigger(properties));
    }
}
