package com.pqqqqq.escript.lang.util.string;

import org.apache.commons.lang3.StringUtils;

/**
 * The string library in essence is an extension of the {@link StringUtils} class from the Apache commons library.
 * The class is more diverse, and doesn't require extra expenditure.
 */
public class StringLibrary {
    private final String string;

    /**
     * Creates a new string library instance
     *
     * @param string the represented string
     * @return the new instance
     */
    public static StringLibrary from(String string) {
        return new StringLibrary(string);
    }

    private StringLibrary(String string) {
        this.string = string;
    }

    /**
     * Gets the represented string
     *
     * @return the string
     */
    public String getString() {
        return string;
    }

    /**
     * Removes the given string if it is at the start of the focal string.
     *
     * @param remove the leading string to remove
     * @return the {@link Result result}
     */
    public Result removeStart(String remove) {
        boolean successful = string.startsWith(remove);
        return new Result(StringUtils.removeStart(string, remove), successful);
    }

    /**
     * Removes the given string if it is at the end of the focal string.
     *
     * @param remove the trailing string to remove
     * @return the {@link Result result}
     */
    public Result removeEnd(String remove) {
        boolean successful = string.endsWith(remove);
        return new Result(StringUtils.removeEnd(string, remove), successful);
    }

    /**
     * Removes given strings if they are
     * (1) at the start of the focal string (first argument), and
     * (2) at the end of the focal string (second argument).
     *
     * @param start the leading string to remove
     * @param end   the trailing string to remove
     * @return the {@link Result result}, which is successful only if both were removed
     */
    public Result remove(String start, String end) {
        boolean successful = string.startsWith(start) && string.endsWith(end);
        return new Result(StringUtils.removeEnd(StringUtils.removeStart(string, start), end), successful);
    }

    /**
     * A result of the string library.
     * Results have the resultant String, and a boolean for success.
     */
    public static class Result {
        private final String result;
        private final boolean successful;

        private Result(String result, boolean successful) {
            this.result = result;
            this.successful = successful;
        }

        /**
         * Gets the resultant string
         *
         * @return the string
         */
        public String getResult() {
            return result;
        }

        /**
         * Gets if whatever action that was performed was successful.
         *
         * @return true if successful
         */
        public boolean isSuccessful() {
            return successful;
        }
    }
}
