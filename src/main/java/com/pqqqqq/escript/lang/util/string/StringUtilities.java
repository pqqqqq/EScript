package com.pqqqqq.escript.lang.util.string;

import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * String utilities
 */
public class StringUtilities {
    private final String string;

    /**
     * Creates a new string utilities instance
     *
     * @param string the represented string
     * @return the new instance
     */
    public static StringUtilities from(String string) {
        return new StringUtilities(string);
    }

    private StringUtilities(String string) {
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
     * <p>Calculates the number of leading tabulations in the represented strings.</p>
     * <p>For example, the string: "        hello" has two leading tabulations.</p>
     * <p>A tabulation is either a tabulation character (\t) or four spaces.</p>
     *
     * @return the number of leading tabs
     */
    public int getLeadingTabulations() {
        int tabs = 0, buff = 0;

        for (char c : getString().toCharArray()) {
            if (c == '\t') { // If it's a tab, remove any buffer and add a tab
                tabs++;
                buff = 0;
            } else if (c == ' ') { // If it's a space, add to buff unless buff is 4
                if (++buff == 4) {
                    buff = 0;
                    tabs++;
                }
            } else {
                return tabs; // Otherwise, it's the end of the road
            }
        }

        return -1; // If it reaches here, the string is solely whitespace, and is negligible
    }

    /**
     * Gets a double from the string, or null if none
     *
     * @return the double, or null
     */
    public Double asDouble() {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    /**
     * Formats the colour into legacy minecraft format
     */
    public String formatColour() {
        if (getString() == null) {
            return null;
        }

        return TextSerializers.formattingCode('&').replaceCodes(getString(), TextSerializers.LEGACY_FORMATTING_CODE);

        //return str.replaceAll("&([0-9a-fA-FkKlLmMnNoOrR])", "§$1");
    }

    /**
     * Parses the next {@link SplitSequence} in a string by a prioritized split group
     *
     * @param properties the {@link TrackerProperties tracker properties}
     * @param delimiterGroups a two-dimensional delimiter string array, where each String[] in the String[][] is prioritized by its ordinal
     * @return the next split sequence, or null if none
     */
    public SplitSequence parseNextSequence(TrackerProperties properties, String[]... delimiterGroups) {
        for (String[] splitGroup : delimiterGroups) {
            Tracker tracker = new Tracker(properties); // Create new tracker
            SplitSequence current = null;

            for (int count = 0; count < string.length(); count++) {
                char c = string.charAt(count);

                tracker.builder += c; // Manual
                if (tracker.canMatch()) {
                    for (String split : splitGroup) {
                        if (tracker.doesMatch(split)) {
                            String before = string.substring(0, count - split.length() + 1);
                            String after = string.substring(count + 1);

                            current = new SplitSequence(before, split, after);

                            if (!properties.isLast()) { // If it's not the last one, we can return now
                                return current;
                            }
                        }
                    }
                }

                tracker.track(c, false);
            }

            if (current != null) {
                return current;
            }
        }

        return null;
    }

    /**
     * Parses a split of the specified string at the given delimiters, excluding quotes, round and square brackets
     *
     * @param delimiter the delimiter strings
     * @return the split string {@link List list}
     */
    public List<String> parseSplit(String... delimiter) {
        return parseSplit(TrackerProperties.def(), delimiter);
    }

    /**
     * Parses a split of the specified string at the given delimiters based on the {@link TrackerProperties tracker properties} for delimitation.
     *
     * @param properties the tracker properties
     * @param delimiter  the delimiter strings
     * @return the split string {@link List list}
     */
    public List<String> parseSplit(TrackerProperties properties, String... delimiter) {
        List<String> list = new ArrayList<>();
        Tracker tracker = new Tracker(); // New tracker

        for (int count = 0; count < string.length(); count++) {
            char c = string.charAt(count);
            tracker.track(c, true);

            if (tracker.canMatch()) {
                for (String split : delimiter) {
                    if (tracker.doesMatch(split)) {
                        list.add(tracker.builder.substring(0, tracker.builder.length() - split.length()));
                        tracker.builder = "";
                        break;
                    }
                }
            }
        }

        if (!tracker.builder.isEmpty()) {
            list.add(tracker.builder);
        }
        return list;
    }

    /**
     * Trims a string with {@link String#trim()} and by removing any double whitespace
     *
     * @return the trimmed string
     */
    public String trim() {
        String trimmed = string.trim(); // First trim using String#trim()
        boolean quotes = false;
        String builder = "";

        for (char c : trimmed.toCharArray()) {
            if (c == '"') {
                if (!builder.endsWith("\\") || builder.endsWith("\\\\")) {
                    quotes = !quotes;
                }
            }

            if (quotes || !Character.isWhitespace(c) || !builder.isEmpty() && !Character.isWhitespace(builder.charAt(builder.length() - 1))) { // trim() should get rid of leading whitespace, but just in case
                builder += c;
            }
        }

        return builder;
    }

    private class Tracker { // Private class
        private final TrackerProperties properties;

        private String builder = "";
        private boolean quotes = false;
        private int roundBrackets = 0, squareBrackets = 0, curlyBrackets = 0;

        private Tracker() {
            this(TrackerProperties.def());
        }

        private Tracker(TrackerProperties properties) { // Can't be created by anything else
            this.properties = properties;
        }

        private boolean canMatch() { // Other getters aren't necessary
            return (!quotes || !properties.doQuotesMatter()) && (!properties.doBracketsMatter() || roundBrackets == 0 && squareBrackets == 0 && curlyBrackets == 0);
        }

        private boolean doesMatch(String str) {
            return builder.endsWith(str);
        }

        private void track(char c, boolean addToString) { // This class tracks all that is necessary for the next character
            if (c == '"' && properties.doQuotesMatter()) {
                if (!builder.endsWith("\\") || builder.endsWith("\\\\")) {
                    quotes = !quotes;
                }
            } else if (!quotes && properties.doBracketsMatter()) {
                if (c == '(') {
                    roundBrackets++;
                } else if (c == ')') {
                    roundBrackets--;
                } else if (c == '[') {
                    squareBrackets++;
                } else if (c == ']') {
                    squareBrackets--;
                } else if (c == '{') {
                    curlyBrackets++;
                } else if (c == '}') {
                    curlyBrackets--;
                }
            }

            if (addToString) {
                builder += c;
            }
        }
    }

}
