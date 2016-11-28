package com.pqqqqq.escript.lang.data.serializer;

import com.pqqqqq.escript.lang.data.Literal;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;

import java.util.Optional;

/**
 * Created by Kevin on 2016-09-28.
 * <pre>
 * The {@link CatalogType catalog type} {@link Serializer serializer}.
 * The catalog serializer is a functional interface that requires the catalog class to be specified.
 * </pre>
 */
public interface CatalogSerializer<T extends CatalogType> extends Serializer<T> {

    @Override
    default Literal serialize(T value) {
        return Literal.fromObject(value.getId()); // Simple, get the id as a literal
    }

    @Override
    default T deserialize(Literal value) {
        String string = value.asString().trim();
        Optional<T> returnValue = Sponge.getRegistry().getType(getCorrespondingClass(), string);

        if (returnValue.isPresent()) {
            return returnValue.get();
        } else {
            if (!string.startsWith("minecraft:")) { // If no minecraft:, add it
                return Sponge.getRegistry().getType(getCorrespondingClass(), "minecraft:" + string).orElse(null);
            } else { // Opposite, if there is a minecraft:, remove it
                return Sponge.getRegistry().getType(getCorrespondingClass(), string.substring(10)).orElse(null);
            }
        }
    }
}
