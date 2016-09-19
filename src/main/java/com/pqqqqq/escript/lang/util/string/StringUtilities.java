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
     * @param delimiterGroups a two-dimensional delimiter string array, where each String[] in the String[][] is prioritized by its ordinal
     * @return the next split sequence, or null if none
     */
    public SplitSequence parseNextSequence(String[]... delimiterGroups) {
        for (String[] splitGroup : delimiterGroups) {
            boolean quotes = false;
            int roundBrackets = 0, squareBrackets = 0, curlyBrackets = 0;
            String builder = "";
            SplitSequence current = null;

            for (int count = 0; count < string.length(); count++) {
                char c = string.charAt(count);

                builder += c;
                if (!quotes && roundBrackets == 0 && squareBrackets == 0 && curlyBrackets == 0) {
                    for (String split : splitGroup) {
                        if (builder.endsWith(split)) {
                            String before = string.substring(0, count - split.length() + 1);
                            String after = string.substring(count + 1);

                            current = new SplitSequence(before, split, after);
                        }
                    }
                }

                if (c == '"') {
                    if (!builder.endsWith("\\") || builder.endsWith("\\\\")) {
                        quotes = !quotes;
                    }
                } else if (!quotes) {
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
        List<String> list = new ArrayList<>();

        boolean quotes = false;
        int roundBrackets = 0, squareBrackets = 0, curlyBrackets = 0;
        String builder = "";

        for (int count = 0; count < string.length(); count++) {
            char c = string.charAt(count);

            if (c == '"') {
                if (!builder.endsWith("\\") || builder.endsWith("\\\\")) {
                    quotes = !quotes;
                }
            } else if (!quotes) {
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

            builder += c;
            if (!quotes && roundBrackets == 0 && squareBrackets == 0 && curlyBrackets == 0) {
                for (String split : delimiter) {
                    if (builder.endsWith(split)) {
                        list.add(builder.substring(0, builder.length() - split.length()));
                        builder = "";
                        break;
                    }
                }
            }
        }

        if (!builder.isEmpty()) {
            list.add(builder);
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
}
