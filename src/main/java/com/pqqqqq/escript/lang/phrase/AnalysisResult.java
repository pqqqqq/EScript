package com.pqqqqq.escript.lang.phrase;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.Sequencer;
import com.pqqqqq.escript.lang.data.container.DatumContainer;
import com.pqqqqq.escript.lang.exception.UnknownSymbolException;
import com.pqqqqq.escript.lang.phrase.syntax.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * An analysis result, containing a phrase and the pattern used to match it.
 */
public class AnalysisResult {
    private final Phrase phrase;
    private final Map<Component.ArgumentComponent, String> strargs;
    private final Map<Component.ArgumentComponent, DatumContainer> containers;

    /**
     * Creates a new builder instance
     *
     * @return the new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    private AnalysisResult(Phrase phrase, Map<Component.ArgumentComponent, String> strargs, Map<Component.ArgumentComponent, DatumContainer> containers) {
        this.phrase = phrase;
        this.strargs = strargs;
        this.containers = containers;
    }

    /**
     * Gets the {@link Phrase phrase}
     *
     * @return the phrase
     */
    public Phrase getPhrase() {
        return phrase;
    }

    /**
     * Gets the strargs {@link Map map}
     *
     * @return the map
     */
    public Map<Component.ArgumentComponent, String> getStrargs() {
        return strargs;
    }

    /**
     * Gets the {@link DatumContainer container} {@link Map map}
     *
     * @return the map
     */
    public Map<Component.ArgumentComponent, DatumContainer> getContainers() {
        return containers;
    }

    /**
     * The {@link AnalysisResult analysis} builder
     */
    public static class Builder {
        private Phrase phrase = null;
        private Map<Component.ArgumentComponent, String> strargs = null;

        private Builder() {
        }

        /**
         * Sets the {@link Phrase phrase} for this analysis builder
         *
         * @param phrase the new phrase
         * @return this builder, for chaining
         */
        public Builder phrase(Phrase phrase) {
            this.phrase = phrase;
            return this;
        }

        /**
         * Sets the strargs {@link Map map}
         *
         * @param strargs the new strargs map
         * @return this builder, for chaining
         */
        public Builder strargs(Map<Component.ArgumentComponent, String> strargs) {
            this.strargs = strargs;
            return this;
        }

        /**
         * <pre>
         * Attempts to build a new {@link AnalysisResult analysis}
         * If any {@link DatumContainer containers} cannot be resolved, {@link Optional#empty()} will be returned
         * </pre>
         *
         * @return the analysis
         */
        public Optional<AnalysisResult> build() {
            try {
                Map<Component.ArgumentComponent, DatumContainer> containers = new HashMap<>();
                checkNotNull(this.strargs, "Strargs cannot be null").forEach((k, v) -> containers.put(k, k.doSequence() ? Sequencer.instance().sequence(v) : Literal.EMPTY)); // Populate containers

                return Optional.of(new AnalysisResult(checkNotNull(this.phrase, "Phrase cannot be null"), this.strargs, containers));
            } catch (UnknownSymbolException e) { // Catch unknown symbol from Sequencer, attempt a different phrase
                return Optional.empty();
            }
        }
    }
}
