package com.pqqqqq.escript.lang.util.string;

/**
 * The tracker properties class, which specifies to the tracker what to care about
 */
public class TrackerProperties {
    private static final TrackerProperties DEFAULT = builder().build();

    private final boolean quotesMatter;
    private final boolean bracketsMatter;
    private final boolean last;

    /**
     * Creates a new builder instance
     *
     * @return the new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the default tracker properties values.
     * Quotes and brackets matter, and last is false.
     *
     * @return the default properties value.
     */
    public static TrackerProperties def() {
        return DEFAULT;
    }

    private TrackerProperties(boolean quotesMatter, boolean bracketsMatter, boolean last) {
        this.quotesMatter = quotesMatter;
        this.bracketsMatter = bracketsMatter;
        this.last = last;
    }

    /**
     * Checks if quotes should matter for this tracker
     *
     * @return true if quotes should matter
     */
    public boolean doQuotesMatter() {
        return quotesMatter;
    }

    /**
     * Checks if brackets should matter for this tracker
     *
     * @return true if they should matter
     */
    public boolean doBracketsMatter() {
        return bracketsMatter;
    }

    /**
     * Checks if the tracker should get the last of the given assignment
     *
     * @return true if should be last
     */
    public boolean isLast() {
        return last;
    }

    /**
     * The tracker property builder
     */
    public static class Builder {
        private boolean quotesMatter = true;
        private boolean bracketsMatter = true;
        private boolean last = false;

        /**
         * Sets if quotes should matter to this tracker
         *
         * @param quotesMatter the new quotes matter value
         * @return this builder, for chaining
         */
        public Builder quotes(boolean quotesMatter) {
            this.quotesMatter = quotesMatter;
            return this;
        }

        /**
         * Sets if brackets should matter to this tracker
         *
         * @param bracketsMatter the new brackets matter value
         * @return this builder, for chaining
         */
        public Builder brackets(boolean bracketsMatter) {
            this.bracketsMatter = bracketsMatter;
            return this;
        }

        /**
         * Sets if this tracker should get the last instance of its assignment
         *
         * @param last the new last value
         * @return this builder, for chaining
         */
        public Builder last(boolean last) {
            this.last = last;
            return this;
        }

        /**
         * Builds the new tracker properties instance
         *
         * @return the new instance
         */
        public TrackerProperties build() {
            return new TrackerProperties(quotesMatter, bracketsMatter, last);
        }
    }
}
