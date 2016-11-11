package com.pqqqqq.escript.lang.util.string;

import org.apache.commons.lang3.StringUtils;

/**
 * The string library in essence is an extension of the {@link StringUtils} class from the Apache commons library.
 * The class is more diverse, and doesn't require extra expenditure.
 */
public class StringLibrary {
    private static final TrackerProperties COMMENT_TRACKER = TrackerProperties.builder().brackets(false).build();
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
     * Gets a result that represents the block comment status and the current string
     *
     * @param blockComment whether there is an active block comment
     * @return the result pair of boolean and string (boolean representing state of block comments)
     */
    public Result removeComments(boolean blockComment) {
        if (blockComment) { // We're already in a block comment
            SplitSequence split = StringUtilities.from(string).parseNextSequence(COMMENT_TRACKER, new String[]{"*/"}); // Parse split with end of comment
            if (split != null) {
                return from(split.getAfterSegment()).removeComments(false); // Get the other side of the block
            } else { // Null = no ending block on this line
                return new Result("", true); // Return that the block is still going, and there's no line
            }
        } else {
            SplitSequence split = StringUtilities.from(string).parseNextSequence(COMMENT_TRACKER, new String[]{"/*", "//"}); // Parse split with start of block comment or inline comment
            if (split == null) { // If neither are there, return the entire line, and no current block
                return new Result(string, false); // Return that there is no block, and the entire line is okay
            }

            switch (split.getDelimiter()) {
                case "/*": // Block comment is first
                    Result otherResult = from(split.getAfterSegment()).removeComments(true); // Remove comments coming after this new block line

                    // Return the current state of the block comment from the other result, as well as any line (if any is there) after the block comment is closed on this line
                    return new Result(split.getBeforeSegment() + otherResult.getResult(), otherResult.isSuccessful());
                case "//": // Inline comment is first
                    return new Result(split.getBeforeSegment(), false); // Return anything before the in-line comment, and no current block comment
                default:
                    return null; // How would this even be possible? Just error I guess
            }
        }
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
