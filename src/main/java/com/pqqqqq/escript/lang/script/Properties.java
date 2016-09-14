package com.pqqqqq.escript.lang.script;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * A list of {@link Script script} properties for generic scripts
 * </pre>
 */
public class Properties {
    private static final Properties EMPTY = builder().build();
    private final Event event; // Due to most triggers using events, this won't be optional (but can be null)
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

    private Properties(Event event, Player player) {
        this.event = event;
        this.player = player;
    }

    /**
     * Gets the associated {@link Event event}
     *
     * @return the event
     */
    public Event getEvent() {
        return event;
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
        private Event event = null;
        private Player player = null;

        private Builder() {
        }

        /**
         * Sets the {@link Event event} of the script's properties
         *
         * @param event the new event
         * @return this builder, for chaining
         */
        public Builder event(Event event) {
            this.event = event;
            return this;
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
            return new Properties(event, player);
        }
    }
}
