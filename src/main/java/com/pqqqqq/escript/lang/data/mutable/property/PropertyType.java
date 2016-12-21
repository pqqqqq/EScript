package com.pqqqqq.escript.lang.data.mutable.property;

import java.util.Optional;

/**
 * The enumeration of possible property types.
 * Property types specify the name used to retrieve a {@link Property property.}
 */
public enum PropertyType {
    /**
     * The player property
     */
    PLAYER("player", "person", "player's", "person's"),

    /**
     * The block property
     */
    BLOCK("block"),

    /**
     * The entity property
     */
    ENTITY("entity"),

    /**
     * The interaction type property
     */
    INTERACTION("interaction"),

    /**
     * The command property
     */
    COMMAND("command"),

    /**
     * The arguments property
     */
    ARGUMENTS("argument", "arguments");

    private final String[] aliases;

    PropertyType(String... aliases) {
        this.aliases = aliases;
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
     * Attempts to try to get the {@link PropertyType keyword} from one if its aliases
     *
     * @param str the string
     * @return the container
     */
    public static Optional<PropertyType> fromString(String str) {
        for (PropertyType keyword : PropertyType.values()) {
            for (String alias : keyword.getAliases()) {
                if (alias.equalsIgnoreCase(str)) {
                    return Optional.of(keyword);
                }
            }
        }

        return Optional.empty();
    }
}