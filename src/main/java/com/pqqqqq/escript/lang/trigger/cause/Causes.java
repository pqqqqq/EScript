package com.pqqqqq.escript.lang.trigger.cause;

import com.pqqqqq.escript.lang.registry.Registry;
import com.pqqqqq.escript.lang.trigger.Trigger;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * A {@link Registry registry} of {@link Cause causes}
 */
public class Causes extends Registry<Cause> {
    // REGISTRY \\

    public static final Cause MINE = new Cause("mine");

    // END REGISTRY \\

    private static final Causes INSTANCE = new Causes();

    /**
     * Gets the main causes instance
     *
     * @return the main instance
     */
    public static Causes instance() {
        return INSTANCE;
    }

    private Causes() {
        super(Cause.class);
    }

    /**
     * Reloads all the causes by clearing their {@link Trigger triggers}
     */
    public void reload() {
        for (Cause cause : this) {
            cause.getTriggers().clear();
        }
    }
}
