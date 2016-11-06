package com.pqqqqq.escript.lang.script;

import com.pqqqqq.escript.lang.data.Datum;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private final Map<String, Object> variables;
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

    private Properties(Event event, Player player, Map<String, Object> variables) {
        this.event = event;
        this.player = player;
        this.variables = variables;
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
     * Gets the variable only if it contains the given class type
     *
     * @param variable the variable name
     * @param type the class type of the variable
     * @param <T> the generic type fo teh variable
     * @return the variable, or {@link Optional#empty()} if no variable with that name and type exists
     */
    public <T> Optional<T> getVariable(String variable, Class<T> type) {
        Object var = this.variables.get(variable);
        if (var == null) {
            return Optional.empty();
        } else if (!type.isInstance(var)) {
            return Optional.empty();
        } else {
            return Optional.of(type.cast(var)); // Cast to it
        }
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
        private Map<String, Object> variables = new HashMap<>();

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
         * Adds a variable K,V value
         *
         * @param key the key
         * @param value the value
         * @return this builder, for chaining
         */
        public Builder variable(String key, Object value) {
            this.variables.put(key, value);
            return this;
        }

        /**
         * Builds the {@link Properties properties} instance
         *
         * @return the new instance
         */
        public Properties build() {
            return new Properties(event, player, variables);
        }
    }
}
