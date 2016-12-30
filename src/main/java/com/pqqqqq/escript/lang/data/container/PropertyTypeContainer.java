package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.property.PropertyType;
import com.pqqqqq.escript.lang.line.Context;

/**
 * A container to get the value of a {@link PropertyType property type} at runtime
 */
public class PropertyTypeContainer implements DatumContainer {
    private final PropertyType type;

    /**
     * Creates a new property type container from the property type
     *
     * @param type the type
     */
    public PropertyTypeContainer(PropertyType type) {
        this.type = type;
    }

    /**
     * Gets the property type
     *
     * @return the type
     */
    public PropertyType getType() {
        return type;
    }

    @Override
    public Literal resolve(Context ctx) {
        // We have to check if we're at runtime or compile time.
        // Compile time would mean the script is null, and we'll return an empty literal
        if (ctx.getScript() == null) {
            return Literal.EMPTY;
        } else {
            return ctx.getScript().getProperties().getValue(type).map(property -> property.resolve(ctx)).orElse(Literal.EMPTY);
        }
    }
}
