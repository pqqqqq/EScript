package com.pqqqqq.escript.lang.data;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Kevin on 2016-09-20.
 * <p>
 * An enumeration of keywords that can utilized in ES
 */
public enum Keyword {
    /**
     * <pre>
     * The last keyword, takes one container argument in {@link #resolve(Literal...)}, and inserts on indices
     * For example, in {@link Literal#fromIndex(Literal)}, the last keyword will automatically get the last item
     * </pre>
     */
    LAST((arguments) -> Literal.fromObject(arguments[0].size()), "last", "tail"),

    /**
     * <pre>
     * The first keyword, takes one container argument in {@link #resolve(Literal...)}, and inserts on indices
     * For example, in {@link Literal#fromIndex(Literal)}, the first keyword will automatically get the first item
     * </pre>
     */
    FIRST((arguments) -> Literal.ONE, "first", "head");

    private final Function<Literal[], Literal> function;
    private final String[] aliases;
    private final Literal literalValue;

    Keyword(Function<Literal[], Literal> function, String... aliases) {
        this.aliases = aliases;
        this.function = function;
        this.literalValue = new Literal(this);
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
    public Literal resolve(Literal... arguments) {
        return this.function.apply(arguments);
    }

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
