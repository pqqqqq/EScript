package com.pqqqqq.escript.lang.data.serializer.block;

import com.flowpowered.math.vector.Vector3d;
import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.data.store.map.MapModule;
import com.pqqqqq.escript.lang.exception.SerializationException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Kevin on 2016-09-27.
 * The {@link Location location} {@link World world} serializer
 */
public class LocationSerializer implements Serializer<Location> {
    private static final LocationSerializer INSTANCE = new LocationSerializer();
    private static final String[] MAPPER = {"world", "x", "y", "z"};

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static LocationSerializer instance() {
        return INSTANCE;
    }

    private LocationSerializer() {
    }

    @Override
    public Literal serialize(Location value) {
        LiteralStore store = LiteralStore.empty();
        MapModule mapModule = store.getMapModule();

        mapModule.add("world", Literal.fromObject(value.getExtent().getUniqueId().toString()));
        mapModule.add("x", Literal.fromObject(value.getX()));
        mapModule.add("y", Literal.fromObject(value.getY()));
        mapModule.add("z", Literal.fromObject(value.getZ()));
        return Literal.fromObject(store);
    }

    @Override
    public Location<World> deserialize(Literal value) {
        LiteralStore store = value.asStore().collapseMap(MAPPER);
        MapModule mapModule = store.getMapModule();

        if (mapModule.size() < 4) { // We'll allow lenience with having more
            throw new SerializationException("Value \"%s\" has incorrect number of arguments for a Location (<4)", value.asString());
        }

        // Parse position vector
        double x = mapModule.getLiteral("x").asNumber();
        double y = mapModule.getLiteral("y").asNumber();
        double z = mapModule.getLiteral("z").asNumber();
        Vector3d position = new Vector3d(x, y, z);

        // Parse world
        String uuidOrName = mapModule.getLiteral("world").asString();
        Optional<World> worldOptional;

        try {
            worldOptional = Sponge.getServer().getWorld(UUID.fromString(uuidOrName));
        } catch (IllegalArgumentException e) {
            worldOptional = Sponge.getServer().getWorld(uuidOrName);
        }

        World world = worldOptional.orElseThrow(() -> new SerializationException("Could not resolve world: \"%s\"", uuidOrName));
        return new Location<>(world, position);
    }

    @Override
    public Class<Location> getCorrespondingClass() {
        return Location.class;
    }
}
