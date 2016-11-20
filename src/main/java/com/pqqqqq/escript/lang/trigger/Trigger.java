package com.pqqqqq.escript.lang.trigger;

import com.pqqqqq.escript.lang.file.RawScript;
import com.pqqqqq.escript.lang.script.Properties;
import com.pqqqqq.escript.lang.script.Script;
import com.pqqqqq.escript.lang.trigger.cause.Cause;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.pqqqqq.escript.lang.util.Assertions.assertNotNull;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * A trigger bridges a {@link RawScript raw script} to an array of {@link Cause causes}
 * </pre>
 */
public class Trigger {
    private final RawScript rawScript;
    private final Cause[] causes;
    private final Predicate<Properties> predicate;
    private final Consumer<Script> successConsumer;

    /**
     * Creates a new trigger builder
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    private Trigger(RawScript rawScript, Cause[] causes, Predicate<Properties> predicate, Consumer<Script> successConsumer) {
        this.rawScript = rawScript;
        this.predicate = predicate;
        this.causes = causes;
        this.successConsumer = successConsumer;
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
     * Gets the {@link Script script success} {@link Consumer consumer} for this trigger.
     * Success consumers are {@link Consumer#accept(Object) invoked} when the {@link #getPredicate() predicate}
     * of the trigger {@link Predicate#test(Object) is successfully tested}, <b>or</b> if no predicate exists.
     *
     * @return the success consumer
     */
    public Consumer<Script> getSuccessConsumer() {
        return successConsumer;
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
        if (properties == null || getPredicate().test(properties)) {
            Script script = rawScript.toScript(properties);
            if (getSuccessConsumer() != null) {
                successConsumer.accept(script); // Use consumer
            }

            script.execute();
        }
    }

    /**
     * The {@link Trigger trigger} builder
     */
    public static class Builder {
        private RawScript rawScript = null;
        private Cause[] causes = null;

        private Predicate<Properties> predicate = null;
        private Consumer<Script> successConsumer = null;

        private Builder() {
        }

        /**
         * Sets the {@link RawScript raw script} for the builder.
         *
         * @param rawScript the new raw script
         * @return this builder, for chaining
         */
        public Builder script(RawScript rawScript) {
            this.rawScript = rawScript;
            return this;
        }

        /**
         * <strong>Sets</strong> (ergo doesn't add) the {@link Cause causes} for the builder.
         *
         * @param causes the new causes
         * @return this builder, for chaining
         */
        public Builder causes(Cause... causes) {
            this.causes = causes;
            return this;
        }

        /**
         * Sets the {@link Predicate predicate} for this trigger.
         *
         * @param predicate the new predicate
         * @return this builder, for chaining
         */
        public Builder predicate(Predicate<Properties> predicate) {
            this.predicate = predicate;
            return this;
        }

        /**
         * Sets the {@link Script script} {@link Consumer consumer}
         *
         * @param successConsumer the new script consumer
         * @return this builder, for chaining
         */
        public Builder scriptConsumer(Consumer<Script> successConsumer) {
            this.successConsumer = successConsumer;
            return this;
        }

        /**
         * Builds the new {@link Trigger trigger}.
         * The {@link #script(RawScript) script} and {@link #causes(Cause...) causes} must be specified before this method.
         *
         * @return the new trigger
         */
        public Trigger build() {
            Trigger newTrigger = new Trigger(assertNotNull(rawScript, "Raw script cannot be null"), assertNotNull(causes, "At least one cause must be specified."), predicate, successConsumer);
            rawScript.setTrigger(Optional.of(newTrigger)); // Set script's trigger

            // Add trigger to each cause
            for (Cause cause : causes) {
                cause.getTriggers().add(newTrigger);
            }

            return newTrigger;
        }
    }
}
