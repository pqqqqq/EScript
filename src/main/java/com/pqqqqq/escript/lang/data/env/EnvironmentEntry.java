package com.pqqqqq.escript.lang.data.env;

/**
 * An environment entry, which specifies tagging for {@link Environment environments} and discerns
 * certain elements unique to environments.
 *
 * @param <I> the identifier's type
 */
public interface EnvironmentEntry<I> {

    /**
     * Gets the id of this entry
     *
     * @return the id
     */
    I getId();

    /**
     * Gets the containing {@link Environment} for this entry
     *
     * @return the environment
     */
    Environment getEnvironment();
}
