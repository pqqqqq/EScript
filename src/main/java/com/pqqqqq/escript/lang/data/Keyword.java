package com.pqqqqq.escript.lang.data;

import java.util.List;
import java.util.Optional;

/**
 * Created by Kevin on 2016-09-20.
 * <p>
 * An enumeration of keywords that can utilized in ES
 */
public enum Keyword {
    /**
     * <pre>
     * The last keyword, takes one container argument in {@link #applyTo(Literal...)}, and inserts on indices
     * For example, in {@link Literal#fromIndex(Literal)}, the last keyword will automatically get the last item
     * </pre>
     */
    LAST("last", "tail") {
        @Override
        public Literal applyTo(Literal... arguments) {
            Literal container = arguments[0];
            if (container.isList()) {
                List<Literal> list = container.asList();
                return list.isEmpty() ? Literal.EMPTY : list.get(list.size() - 1);
            } else {
                String string = container.asString();
                return Literal.fromObject(string.isEmpty() ? Literal.EMPTY_STRING : string.charAt(string.length() - 1));
            }
        }
    },

    /**
     * <pre>
     * The first keyword, takes one container argument in {@link #applyTo(Literal...)}, and inserts on indices
     * For example, in {@link Literal#fromIndex(Literal)}, the first keyword will automatically get the first item
     * </pre>
     */
    FIRST("first", "head") {
        @Override
        public Literal applyTo(Literal... arguments) {
            Literal container = arguments[0];
            if (container.isList()) {
                List<Literal> list = container.asList();
                return list.isEmpty() ? Literal.EMPTY : list.get(0);
            } else {
                String string = container.asString();
                return Literal.fromObject(string.isEmpty() ? Literal.EMPTY_STRING : string.charAt(0));
            }
        }
    };

    private final Literal literalValue;
    private final String[] aliases;

    Keyword(String... aliases) {
        this.literalValue = new Literal(this);
        this.aliases = aliases;
    }

    /**
     * <pre>
     * Attempts to apply this keyword to the list of {@link Literal arguments}
     * Different keywords may take a different number of arguments
     * </pre>
     *
     * @param arguments the arguments
     * @return the resultant literal
     */
    public abstract Literal applyTo(Literal... arguments);

    /**
     * Gets the {@link Literal literal value} for this keyword
     *
     * @return the literal
     */
    public Literal getLiteral() {
        return literalValue;
    }

    /**
     * Gets a string of matching aliases for this keyword
     *
     * @return an array of aliases
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * <pre>
     * Attempts to retrieve the keyword associated with the strarg.
     * The strarg must be listed in the {@link Keyword alias array} of a keyword, and is case insensitive.
     * </pre>
     *
     * @param strarg the strarg
     * @return the keyword, or {@link Optional#empty()} if none match
     */
    public static Optional<Keyword> fromString(String strarg) {
        for (Keyword keyword : values()) {
            for (String alias : keyword.getAliases()) {
                if (alias.equalsIgnoreCase(strarg)) {
                    return Optional.of(keyword);
                }
            }
        }

        return Optional.empty();
    }
}
