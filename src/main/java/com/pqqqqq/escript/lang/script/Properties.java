package com.pqqqqq.escript.lang.script;

import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 *
 * <pre>
 * A list of {@link Script script} properties for generic scripts
 * </pre>
 */
public class Properties {
    private static final Properties EMPTY = builder().build();
    private final Player player; // Due to most triggers using player, this won't be optional (but can be null)

    /**
     * Creates a builder instance
     *
     * @return the new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * <pre>
     * Returns the empty properties instance.
     * Properties are immutable, and cannot be changed through this way, use {@link #builder()}
     * </pre>
     *
     * @return the empty properties instance
     */
    public static Properties empty() {
        return EMPTY;
    }

    private Properties(Player player) {
        this.player = player;
    }

    /**
     * Gets the associated {@link Player player}
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The properties builder
     */
    public static class Builder {
        private Player player = null;

        private Builder() {
        }

        /**
         * Sets the {@link Player player} of the script properties
         *
         * @param player the player
         * @return this builder, for chaining
         */
        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        /**
         * Builds the {@link Properties properties} instance
         *
         * @return the new instance
         */
        public Properties build() {
            return new Properties(player);
        }
    }
}
