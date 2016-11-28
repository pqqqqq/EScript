package com.pqqqqq.escript.lang.data;

import com.pqqqqq.escript.lang.data.container.DatumContainer;

import java.util.Optional;

/**
 * The enumeration of possible keywords
 */
public enum Keyword {
    /**
     * The player keyword
     */
    PLAYER(ctx -> Literal.fromObject(ctx.getScript().getProperties().getPlayer().getUniqueId()),
            "player", "player's", "person", "person's"), // TODO plain object reference?

    /**
     * The block keyword
     */
    BLOCK(ctx -> Literal.EMPTY, "block"), // TODO Should this be empty?

    /**
     * The entity keyword
     */
    ENTITY(ctx -> Literal.EMPTY, "entity"); // TODO Should this be empty?

    private final DatumContainer resolver;
    private final String[] aliases;

    Keyword(DatumContainer resolver, String... aliases) {
        this.resolver = resolver;
        this.aliases = aliases;
    }

    /**
     * Gets the {@link DatumContainer container} resolver
     *
     * @return the resolver
     */
    public DatumContainer getResolver() {
        return resolver;
    }

    /**
     * Gets the possible aliases for the keyword
     *
     * @return the aliases
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Attempts to try to get the {@link Keyword keyword} from one if its aliases
     *
     * @param str the string
     * @return the container
     */
    public static Optional<Keyword> fromString(String str) {
        for (Keyword keyword : Keyword.values()) {
            for (String alias : keyword.getAliases()) {
                if (alias.equalsIgnoreCase(str)) {
                    return Optional.of(keyword);
                }
            }
        }

        return Optional.empty();
    }
}