package com.pqqqqq.escript.lang.data;

import com.pqqqqq.escript.lang.data.store.LiteralStore;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Kevin on 2016-09-20.
 * <p>
 * An enumeration of keywords that can utilized in ES
 */
public enum Keyword {
    /**
     * The last keyword, which gets the last item in a list
     */
    LAST((list) -> Literal.fromObject(list.getListModule().size()), "last", "tail"),

    /**
     * The first keyword, which gets the first item in a list
     */
    FIRST((list) -> Literal.ONE, "first", "head"),

    /**
     * The next keywords, which gets the index for the index of natural addition progression.
     */
    NEXT(list -> Literal.fromObject(list.getListModule().size() + 1), "next", "add");

    private final Function<LiteralStore, Literal> function;
    private final String[] aliases;
    private final Literal literalValue;

    Keyword(Function<LiteralStore, Literal> function, String... aliases) {
        this.aliases = aliases;
        this.function = function;
        this.literalValue = new Literal(this);
    }

    /**
     * Attempts to apply this keyword to the {@link LiteralStore literal store}
     *
     * @param store the store
     * @return the resultant literal
     */
    public Literal resolve(LiteralStore store) {
        return this.function.apply(store);
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
