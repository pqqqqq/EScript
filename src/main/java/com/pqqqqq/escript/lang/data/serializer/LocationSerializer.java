package com.pqqqqq.escript.lang.data.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.exception.SerializationException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Kevin on 2016-09-27.
 * The {@link Location location} {@link World world} serializer
 */
public class LocationSerializer implements Serializer<Location> {
    private static final LocationSerializer INSTANCE = new LocationSerializer();

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
        Object[] array = new Object[4];
        array[0] = value.getExtent().getUniqueId().toString();
        array[1] = value.getX();
        array[2] = value.getY();
        array[3] = value.getZ();

        return Literal.fromObject(array);
    }

    @Override
    public Location<World> deserialize(Literal value) {
        // Locations can be deserialized by lists or strings
        if (value.isString()) {
            return deserialize(Literal.fromObject(value.asString().split(","))); // Strings will just separate them by commas
        }

        List<Literal> list = value.asList();
        if (list.size() < 4) { // We'll allow lenience with having more
            throw new SerializationException("Value \"%s\" has incorrect number of arguments for a Location (<4)", value.asString());
        }

        // Parse position vector
        double x = list.get(1).asNumber();
        double y = list.get(2).asNumber();
        double z = list.get(3).asNumber();
        Vector3d position = new Vector3d(x, y, z);

        // Parse world
        String uuidOrName = list.get(0).asString();
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
    public Class<? extends Location> getCorrespondingClass() {
        return Location.class;
    }
}
