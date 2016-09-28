package com.pqqqqq.escript.lang.data.serializer;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.registry.Registry;

import java.util.Optional;

/**
 * Created by Kevin on 2016-09-27.
 * A {@link Registry registry} of {@link Serializer serializers}
 */
public class Serializers extends Registry<Serializer> {
    // REGISTRY \\

    public static final LocationSerializer LOCATION = LocationSerializer.instance();

    // END REGISTRY \\

    private static final Serializers INSTANCE = new Serializers();

    /**
     * Gets the main serializers instance
     *
     * @return the main instance
     */
    public static Serializers instance() {
        return INSTANCE;
    }

    private Serializers() {
        super(Serializer.class);
    }

    /**
     * Attempts to {@link Serializer#serialize(Object) serialize} the object using the serializer registry
     *
     * @param object the object to serialize
     * @return the serialized {@link Literal literal}, or {@link Optional#empty()}
     */
    @SuppressWarnings("unchecked")
    public Optional<Literal> serialize(Object object) {
        return registry().stream().filter((serializer) -> serializer.isApplicable(object)).map((serializer) -> serializer.serialize(object)).findFirst();
    }

    /**
     * Attempts to gets the corresponding {@link Serializer serializer} for the given class
     *
     * @param clazz the class
     * @return the serializer, or {@link Optional#empty()}
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<Serializer<T>> getSerializer(Class<? extends T> clazz) {
        return registry().stream().filter((serializer) -> serializer.getCorrespondingClass().isAssignableFrom(clazz)).map(a -> (Serializer<T>) a).findFirst();
    }
}
