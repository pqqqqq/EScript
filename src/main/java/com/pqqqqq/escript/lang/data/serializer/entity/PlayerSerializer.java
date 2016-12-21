package com.pqqqqq.escript.lang.data.serializer.entity;


import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.data.store.map.MapModule;
import com.pqqqqq.escript.lang.exception.SerializationException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Kevin on 2016-09-28.
 * The {@link Player player} {@link Serializer serializer}
 */
public class PlayerSerializer implements Serializer<Player> {
    private static final PlayerSerializer INSTANCE = new PlayerSerializer();
    private static final String[] MAPPER = {"uuid"};

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static PlayerSerializer instance() {
        return INSTANCE;
    }

    private PlayerSerializer() {
    }

    @Override
    public Literal serialize(Player value) {
        // Serialize into array

        LiteralStore store = LiteralStore.empty();
        MapModule mapModule = store.getMapModule();

        mapModule.add("uuid", value.getUniqueId().toString());
        return Literal.fromObject(store);
    }

    @Override
    public Player deserialize(Literal value) {
        LiteralStore store = value.asStore().collapseMap(MAPPER);
        MapModule mapModule = store.getMapModule();

        if (mapModule.size() < 1) { // We'll allow lenience with having more
            throw new SerializationException("Value \"%s\" has incorrect number of arguments for a Player (<1)", value.asString());
        }

        // Even though it's supposed to be a uuid, we'll allow a name as well
        String uuid = mapModule.getLiteral("uuid").asString();

        Optional<Player> optionalPlayer = Sponge.getServer().getPlayer(uuid);
        if (!optionalPlayer.isPresent()) { // Try uuid now
            try {
                optionalPlayer = Sponge.getServer().getPlayer(UUID.fromString(uuid));
            } catch (IllegalArgumentException e) {
            }
        }

        return optionalPlayer.orElse(null);
    }

    @Override
    public Class<Player> getCorrespondingClass() {
        return Player.class;
    }
}
