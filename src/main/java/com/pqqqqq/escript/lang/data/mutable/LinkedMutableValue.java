package com.pqqqqq.escript.lang.data.mutable;

import com.pqqqqq.escript.lang.data.serializer.Serializer;
import org.spongepowered.api.data.value.mutable.CompositeValueStore;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Kevin on 2016-10-08.
 * <pre>
 * A linked {@link MutableValue mutable value} who's value is retrieved by a {@link Supplier supplier}
 * and whose value is set by a {@link Function function}. No actual value is stored in this class.
 *
 * <i>Linked</i> mutable values are used to manipulate values which are existent elsewhere in the environment.
 * For example, manipulating a player's health.
 *
 * Serializers are also optional.
 * </pre>
 */
public final class LinkedMutableValue<T> implements MutableValue<T> {
    private final Supplier<? extends T> getSupplier;
    private final Function<? super T, Boolean> setFunction;
    private final Serializer<T> serializer; // This isn't optional anymore

    /**
     * <pre>
     * Creates a new linked mutable value from the {@link Supplier supplier}, {@link Consumer set consumer}, and {@link Serializer serializer}.
     * Since the consumer has no return type to indicate success, the success will always be TRUE.
     * </pre>
     *
     * @param getSupplier the get supplier
     * @param setConsumer the set consumer
     * @param serializer  the serializer
     * @param <T>         the value's generic type
     * @return the new instance
     */
    public static <T> LinkedMutableValue<T> fromConsumer(Supplier<? extends T> getSupplier, Consumer<? super T> setConsumer, Serializer<T> serializer) {
        return new LinkedMutableValue<>(getSupplier, (value) -> {
            setConsumer.accept(value);
            return true;
        }, serializer);
    }

    /**
     * Creates a new linked mutable value from the {@link Supplier supplier}, {@link Function set function}, and {@link Serializer serializer}
     *
     * @param getSupplier the get supplier
     * @param setFunction the set function
     * @param serializer  the serializer
     * @param <T>         the value's generic type
     * @return the new instance
     */
    public static <T> LinkedMutableValue<T> fromFunction(Supplier<? extends T> getSupplier, Function<? super T, Boolean> setFunction, Serializer<T> serializer) {
        return new LinkedMutableValue<>(getSupplier, setFunction, serializer);
    }

    /**
     * Creates a new linked value from the {@link Value sponge value}, {@link CompositeValueStore sponge value store}, and {@link Serializer serializer}
     *
     * @param value      the value
     * @param valueStore the value store
     * @param serializer the serializer
     * @param <T>        the value's generic type
     * @return the new instance
     */
    public static <T> LinkedMutableValue<T> fromStore(Value<T> value, CompositeValueStore<?, ?> valueStore, Serializer<T> serializer) {
        return new LinkedMutableValue<>(value::get, (set) -> {
            value.set(set);
            return valueStore.offer(value).isSuccessful();
        }, serializer);
    }

    private LinkedMutableValue(Supplier<? extends T> getSupplier, Function<? super T, Boolean> setFunction, Serializer<T> serializer) {
        this.getSupplier = getSupplier;
        this.setFunction = setFunction;
        this.serializer = serializer;
    }

    @Override
    public T getValue() {
        return getSupplier.get();
    }

    @Override
    public boolean setValue(T value) {
        return setFunction.apply(value);
    }

    @Override
    public Serializer<T> getSerializer() {
        return serializer;
    }

    @Override
    public LinkedMutableValue<T> copy() {
        return fromFunction(getSupplier, setFunction, serializer);
    }
}
