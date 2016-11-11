package com.pqqqqq.escript.lang.data.serializer.primitive;

import com.pqqqqq.escript.lang.data.Datum;
import com.pqqqqq.escript.lang.data.Literal;

/**
 * The datum {@link PrimitiveSerializer primitive serializer}.
 */
public class DatumSerializer implements PrimitiveSerializer<Datum> {
    private static final DatumSerializer INSTANCE = new DatumSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static DatumSerializer instance() {
        return INSTANCE;
    }

    private DatumSerializer() {
    }

    @Override
    public Datum deserialize(Literal value) {
        return value;
    }

    @Override
    public Literal serialize(Datum value) {
        throw new AbstractMethodError("Datums can only be serialized in a given context.");
    }

    @Override
    public Class<Datum> getCorrespondingClass() {
        return Datum.class;
    }
}
