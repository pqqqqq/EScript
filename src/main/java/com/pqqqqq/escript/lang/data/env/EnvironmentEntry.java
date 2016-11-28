package com.pqqqqq.escript.lang.data.env;

/**
 * An environment entry, which specifies tagging for {@link Environment environments} and discerns
 * certain elements unique to environments.
 */
public interface EnvironmentEntry {

    /**
     * Gets the name of this entry
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the containing {@link Environment} for this entry
     *
     * @return the environment
     */
    Environment getEnvironment();
}
