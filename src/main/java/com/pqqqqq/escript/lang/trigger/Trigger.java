package com.pqqqqq.escript.lang.trigger;

import com.pqqqqq.escript.lang.file.RawScript;
import com.pqqqqq.escript.lang.script.Properties;
import com.pqqqqq.escript.lang.trigger.cause.Cause;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * A trigger bridges a {@link RawScript raw script} to an array of {@link Cause causes}
 * </pre>
 */
public class Trigger {
    private final RawScript rawScript;
    private final Predicate<Properties> predicate;
    private final Cause[] causes;

    /**
     * Creates a trigger from a {@link RawScript raw script} and an array of {@link Cause causes}
     *
     * @param rawScript the raw script
     * @param causes    the causes
     * @return the new trigger instance
     */
    public static Trigger from(RawScript rawScript, Cause... causes) {
        return from(rawScript, (value) -> true, causes); // Always true predicate
    }

    /**
     * Creates a trigger from a {@link RawScript raw script}, a {@link Properties properties} {@link Predicate predicate} and an array of {@link Cause causes}
     *
     * @param rawScript the raw script
     * @param predicate the properties predicate; the trigger will only trigger is this accepts the properties inputted
     * @param causes    the causes
     * @return the new trigger instance
     */
    public static Trigger from(RawScript rawScript, Predicate<Properties> predicate, Cause... causes) {
        Trigger trigger = new Trigger(rawScript, predicate, causes);
        rawScript.setTrigger(Optional.of(trigger));

        // Add trigger to each cause
        for (Cause cause : causes) {
            cause.getTriggers().add(trigger);
        }

        return trigger;
    }

    private Trigger(RawScript rawScript, Predicate<Properties> predicate, Cause... causes) {
        this.rawScript = rawScript;
        this.predicate = predicate;
        this.causes = causes;
    }

    /**
     * Gets the {@link RawScript raw script} that this trigger represents
     *
     * @return the raw script
     */
    public RawScript getRawScript() {
        return rawScript;
    }

    /**
     * Gets the {@link Properties properties} {@link Predicate predicate}
     *
     * @return the predicate
     */
    public Predicate<Properties> getPredicate() {
        return predicate;
    }

    /**
     * Gets an array of {@link Cause causes} associated with this trigger
     *
     * @return an array of causes
     */
    public Cause[] getCauses() {
        return causes;
    }

    /**
     * Triggers the cause
     */
    public void trigger() {
        trigger(null); // This way the other trigger method is responsible for all checks
    }

    /**
     * Triggers the cause with the given {@link Properties properties}, in accordance with the {@link #getPredicate() properties predicate}
     *
     * @param properties the properties
     */
    public void trigger(Properties properties) {
        if (getPredicate().test(properties)) {
            rawScript.toScript(properties).execute();
        }
    }
}
