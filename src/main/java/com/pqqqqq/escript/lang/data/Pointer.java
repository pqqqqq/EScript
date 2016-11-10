package com.pqqqqq.escript.lang.data;

import com.pqqqqq.escript.lang.data.store.LiteralStore;

import java.util.Optional;
import java.util.function.Function;

/**
 * An enumeration of pointers that can utilized in ES
 */
public enum Pointer {
    /**
     * The last pointer, which gets the last item in a list
     */
    LAST((list) -> Literal.fromObject(list.getListModule().size()), "last", "tail"),

    /**
     * The first pointer, which gets the first item in a list
     */
    FIRST((list) -> Literal.ONE, "first", "head"),

    /**
     * The next pointer, which gets the index for the index of natural addition progression.
     */
    NEXT(list -> Literal.fromObject(list.getListModule().size() + 1), "next", "add");

    private final Function<LiteralStore, Literal> function;
    private final String[] aliases;
    private final Literal literalValue;

    Pointer(Function<LiteralStore, Literal> function, String... aliases) {
        this.aliases = aliases;
        this.function = function;
        this.literalValue = new Literal(this);
    }

    /**
     * Attempts to apply this pointer to the {@link LiteralStore literal store}
     *
     * @param store the store
     * @return the resultant literal
     */
    public Literal resolve(LiteralStore store) {
        return this.function.apply(store);
    }

    /**
     * Gets the {@link Literal literal value} for this pointer
     *
     * @return the literal
     */
    public Literal getLiteral() {
        return literalValue;
    }

    /**
     * Gets a string of matching aliases for this pointer
     *
     * @return an array of aliases
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * <pre>
     * Attempts to retrieve the pointer associated with the strarg.
     * The strarg must be listed in the {@link Pointer alias array} of a pointer, and is case insensitive.
     * </pre>
     *
     * @param strarg the strarg
     * @return the pointer, or {@link Optional#empty()} if none match
     */
    public static Optional<Pointer> fromString(String strarg) {
        for (Pointer pointer : values()) {
            for (String alias : pointer.getAliases()) {
                if (alias.equalsIgnoreCase(strarg)) {
                    return Optional.of(pointer);
                }
            }
        }

        return Optional.empty();
    }
}
