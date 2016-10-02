package com.pqqqqq.escript.lang.data.serializer;

import com.pqqqqq.escript.lang.data.Literal;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;

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
        return Sponge.getRegistry().getType(getCorrespondingClass(), value.asString()).orElse(null); // Simple, attempt to get from registry
    }
}
