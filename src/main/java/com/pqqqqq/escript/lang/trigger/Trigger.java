package com.pqqqqq.escript.lang.trigger;

import com.pqqqqq.escript.lang.file.RawScript;
import com.pqqqqq.escript.lang.script.Properties;

import java.util.Optional;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * A trigger bridges a {@link RawScript raw script} to an array of {@link Cause cause}
 * </pre>
 */
public class Trigger {
    private final RawScript rawScript;
    private final Cause[] causes;

    public static Trigger from(RawScript rawScript, Cause... causes) {
        Trigger trigger = new Trigger(rawScript, causes);
        rawScript.setTrigger(Optional.of(trigger));

        // Add trigger to each cause
        for (Cause cause : causes) {
            cause.getTriggers().add(trigger);
        }

        return trigger;
    }

    private Trigger(RawScript rawScript, Cause... causes) {
        this.rawScript = rawScript;
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
        rawScript.toScript().execute();
    }

    /**
     * Triggers the cause with the given {@link Properties properties}
     *
     * @param properties the properties
     */
    public void trigger(Properties properties) {
        rawScript.toScript(properties).execute();
    }
}
