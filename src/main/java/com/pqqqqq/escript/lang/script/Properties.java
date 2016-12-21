package com.pqqqqq.escript.lang.script;

import com.pqqqqq.escript.lang.data.Datum;
import com.pqqqqq.escript.lang.data.mutable.property.PropertyEnvironment;
import com.pqqqqq.escript.lang.data.mutable.property.PropertyType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * A list of {@link Script script} properties for generic scripts
 * </pre>
 */
public class Properties extends PropertyEnvironment {
    private static final Properties EMPTY = builder().build();
    private final Event event; // Due to most triggers using events, this won't be optional (but can be null)
    private final Player player; // Due to most triggers using player, this won't be optional (but can be null)

    private final Map<String, Datum> variableBus = new HashMap<>();

    /**
     * Creates a builder instance
     *
     * @return the new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the empty properties instance.
     * Properties are immutable, and cannot be changed through this way, use {@link #builder()}
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
     * Gets the variable bus, which will be deployed when the script is created at runtime.
     *
     * @return the variable bus
     */
    public Map<String, Datum> getVariableBus() {
        return variableBus;
    }

    /**
     * The properties builder
     */
    public static class Builder {
        private Event event = null;
        private Player player = null;
        private Map<PropertyType, Object> properties = new HashMap<>();

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
         * Also sets the "Player" property
         *
         * @param player the player
         * @return this builder, for chaining
         */
        public Builder player(Player player) {
            this.player = player;
            if (player != null) {
                property(PropertyType.PLAYER, player);
            }

            return this;
        }

        /**
         * Adds a property K,V value
         *
         * @param key the key
         * @param value the value
         * @return this builder, for chaining
         */
        public Builder property(String key, Object value) {
            this.properties.put(PropertyType.fromString(key).orElse(null), value);
            return this;
        }

        /**
         * Adds a property K,V value
         *
         * @param type the type
         * @param value the value
         * @return this builder, for chaining
         */
        public Builder property(PropertyType type, Object value) {
            if (type != null) {
                this.properties.put(type, value);
            }

            return this;
        }

        /**
         * Builds the {@link Properties properties} instance
         *
         * @return the new instance
         */
        public Properties build() {
            Properties newProperties = new Properties(event, player);
            newProperties.createAll(properties);

            return newProperties;
        }
    }
}
