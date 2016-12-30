package com.pqqqqq.escript.lang.util.string;

import com.pqqqqq.escript.lang.exception.FormatException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The string transformer in essence is an extension of the {@link StringUtils} class from the Apache commons library.
 * The class is more diverse, and doesn't require extra expenditure.
 *
 * <p>String libraries contain a <strong>mutable</strong> string instance that is changed with every function it undergoes.
 * The string may retrieved at any time based on its transformations.
 */
public class StringTransformer {
    private static final TrackerProperties COMMENT_TRACKER = TrackerProperties.builder().brackets(false).build();

    private String string;

    /**
     * Creates a new string library instance
     *
     * @param string the represented string
     * @return the new instance
     */
    public static StringTransformer from(String string) {
        return new StringTransformer(string);
    }

    private StringTransformer(String string) {
        this.string = string;
    }

    /**
     * Gets the mutable string in its current state
     *
     * @return the string
     */
    public String getCurrentResult() {
        return string;
    }

    /**
     * Removes the given string if it is at the start of the focal string.
     *
     * @param remove the leading string to remove
     * @return true if successful
     */
    public boolean removeStart(String remove) {
        boolean successful = string.startsWith(remove);
        this.string = StringUtils.removeStart(string, remove); // Set transformed string

        return successful;
    }

    /**
     * Removes the given string if it is at the end of the focal string.
     *
     * @param remove the trailing string to remove
     * @return true if successful
     */
    public boolean removeEnd(String remove) {
        boolean successful = string.endsWith(remove);
        this.string = StringUtils.removeEnd(string, remove); // Set transformed string

        return successful;
    }

    /**
     * Removes given strings if they are
     * (1) at the start of the focal string (first argument), and
     * (2) at the end of the focal string (second argument).
     *
     * @param start the leading string to remove
     * @param end   the trailing string to remove
     * @return true if successful
     */
    public boolean remove(String start, String end) {
        boolean successful = string.startsWith(start) && string.endsWith(end);
        this.string = StringUtils.removeEnd(StringUtils.removeStart(string, start), end); // Set transformed string

        return successful;
    }

    /**
     * Removes ALL prefixes specified from the given string. This is ensured to remove any and all prefixes
     * not dependent on the order of them.
     *
     * <p>Once every prefix fails to be removed, the process is terminated
     *
     * @param prefixes the prefixes
     * @return a set of successful prefix removals
     */
    public Set<String> removeStart(String[] prefixes) {
        return removePayload(new HashSet<>(Arrays.asList(prefixes)), true);
    }

    /**
     * Removes ALL suffixes specified from the given string. This is ensured to remove any and all suffixes
     * not dependent on the order of them.
     * <p>
     * <p>Once every suffix fails to be removed, the process is terminated
     *
     * @param suffixes the suffixes
     * @return a set of successful suffix removals
     */
    public Set<String> removeEnd(String[] suffixes) {
        return removePayload(new HashSet<>(Arrays.asList(suffixes)), false);
    }

    private Set<String> removePayload(Set<String> matchers, boolean prefix) {
        Set<String> successes = new HashSet<>(); // Will contain successes
        Set<String> failures = new HashSet<>(); // Will contain failures

        Function<String, Boolean> removeFunction = prefix ? this::removeStart : this::removeEnd; // Reference trim start or end based on boolean

        // Iterator is the most useful method here, because we can remove easily during iteration
        Iterator<String> iterator = matchers.iterator();

        while (iterator.hasNext()) {
            String matcher = iterator.next();
            if (removeFunction.apply(matcher)) {
                iterator.remove();
                successes.add(matcher);
            } else {
                failures.add(matcher);
            }
        }

        if (!successes.isEmpty()) { // We'll only use recursion if there was a success, otherwise we're not longer remove prefixes/suffixes
            successes.addAll(removePayload(failures, prefix));
        }

        return successes;
    }

    /**
     * Removes <strong>only</strong> the first instance of a pattern, and returns the group
     * specified.
     *
     * @param pattern the pattern
     * @param group   the main group
     * @return the group's value, null if the pattern doesn't match, or empty if the group
     * specified doesn't exist
     */
    public String remove(Pattern pattern, String group) {
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            try {
                String ret = matcher.group(group);
                this.string = matcher.replaceFirst(""); // Set transformed string

                return ret;
            } catch (IllegalArgumentException e) {
                return "";
            }
        }

        return null;
    }

    /**
     * Gets a result that represents the block comment status and the current string
     *
     * @param blockComment whether there is an active block comment
     * @return true if successful
     */
    public boolean removeComments(boolean blockComment) {
        if (blockComment) { // We're already in a block comment
            SplitSequence split = StringUtilities.from(string).parseNextSequence(COMMENT_TRACKER, new String[]{"*/"}); // Parse split with end of comment
            if (split != null) {
                StringTransformer newTransformer = from(split.getAfterSegment());
                boolean ret = newTransformer.removeComments(false); // Get the other side of the block

                this.string = newTransformer.getCurrentResult(); // Set this string
                return ret; // Return
            } else { // Null = no ending block on this line
                this.string = ""; // Set string blank
                return true; // Return that the block is still going, and there's no line
            }
        } else {
            SplitSequence split = StringUtilities.from(string).parseNextSequence(COMMENT_TRACKER, new String[]{"/*", "//"}); // Parse split with start of block comment or inline comment
            if (split == null) { // If neither are there, return the entire line, and no current block
                return false; // Return that there is no block, and the entire line is okay (string doesn't need to be changed)
            }

            switch (split.getDelimiter()) {
                case "/*": // Block comment is first
                    StringTransformer newTransformer = from(split.getAfterSegment());
                    boolean removeSuccess = newTransformer.removeComments(true); // Remove comments coming after this new block line

                    // Return the current state of the block comment from the other result, as well as any line (if any is there) after the block comment is closed on this line
                    this.string = split.getBeforeSegment() + newTransformer.getCurrentResult();
                    return removeSuccess;
                case "//": // Inline comment is first
                    this.string = split.getBeforeSegment();
                    return false; // Return anything before the in-line comment, and no current block comment
                default:
                    throw new FormatException("Unknown delimiter: %s", split.getDelimiter()); // How would this even be possible?
            }
        }
    }
}
