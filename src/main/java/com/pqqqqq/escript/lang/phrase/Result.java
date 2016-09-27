package com.pqqqqq.escript.lang.phrase;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.util.CurrentValue;
import org.spongepowered.api.data.value.mutable.CompositeValueStore;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <pre>
 * A result of a {@link Phrase#execute(Context) execution}
 * Results themselves are interfaces, and are implemented by {@link Success} and {@link Failure}
 * </pre>
 */
public interface Result {

    /**
     * Creates a {@link Success success} result with no return value
     *
     * @return the new success instance
     */
    static Success<?> success() {
        return Success.EMPTY;
    }

    /**
     * Creates a new {@link Success success} with the given return value
     *
     * @param value the return value
     * @param <T>   the return value type
     * @return the new success instance
     */
    static <T> Success<T> success(T value) {
        return new Success<>(value);
    }

    /**
     * Creates a new {@link ValueSuccess value success} with the given return value
     *
     * @param value the return value
     * @param <T>   the return value type
     * @return the new success instance
     */
    static <T> ValueSuccess<T> valueSuccess(CurrentValue<T> value) {
        return new ValueSuccess<>(value);
    }

    /**
     * Creates a new {@link ValueSuccess value success} with the {@link Supplier supplier}, and its corresponding {@link Function set function}
     *
     * @param value       the value
     * @param setFunction the set function
     * @param <T>         the return value type
     * @return the new success instance
     */
    static <T> ValueSuccess<T> valueSuccess(Supplier<T> value, Function<T, Boolean> setFunction) {
        return new ValueSuccess<>(new CurrentValue<T>() {

            @Override
            public Optional<T> get() {
                return Optional.ofNullable(value.get());
            }

            @Override
            public boolean set(T value) {
                return setFunction.apply(value);
            }
        });
    }

    /**
     * Creates a new {@link ValueSuccess value success} with the {@link Value sponge value} and corresponding {@link CompositeValueStore value store}
     *
     * @param value      the value
     * @param valueStore the store
     * @param <T>        the generic type
     * @return the new success instance
     */
    static <T> ValueSuccess<T> valueSuccess(Value<T> value, CompositeValueStore<?, ?> valueStore) {
        return valueSuccess(value::get, (set) -> {
            value.set(set);
            return valueStore.offer(value).isSuccessful();
        });
    }

    /**
     * Creates a {@link Failure failure} result with no error message
     *
     * @return the new failure instance
     */
    static Failure failure() {
        return new Failure();
    }

    /**
     * Creates a new {@link Failure failure} result with the given error message
     *
     * @param errorMessage the error message
     * @param args arguments for {@link String#format(String, Object...)}
     * @return the new failure instance
     */
    static Failure failure(String errorMessage, Object... args) {
        return new Failure(String.format(errorMessage, args));
    }

    /**
     * Gets whether the {@link Phrase#execute(Context) execution} was successful
     *
     * @return true if successful
     */
    default boolean wasSuccessful() {
        return this instanceof Success;
    }

    /**
     * <pre>
     * A successful {@link Result result}
     * Successes have {@link Optional optional} {@link #getValue() return values}
     * </pre>
     *
     * @param <T> the return value's type
     */
    class Success<T> implements Result {
        private static final Success<?> EMPTY = new Success<>();
        private final Optional<T> value;

        private Success() {
            this(null);
        }

        protected Success(T value) {
            this.value = Optional.ofNullable(value);
        }

        /**
         * Gets the return value of this success
         *
         * @return the {@link Optional optional} return value
         */
        public Optional<T> getValue() {
            return value;
        }

        /**
         * <pre>
         * Gets the return value as a {@link Literal literal}
         * If there is no value, {@link Literal#EMPTY} is returned
         * </pre>
         *
         * @return the literal return value
         */
        public Literal getLiteralValue() {
            if (getValue().isPresent()) {
                return Literal.fromObject(getValue().get());
            } else {
                return Literal.EMPTY;
            }
        }
    }

    /**
     * <pre>
     * A {@link Success successful} {@link Result result} whose value has resides in a {@link CurrentValue current value} container.
     * Value successes include a {@link #set(Object)} method
     * </pre>
     *
     * @param <T> the type bounded by the current value
     */
    final class ValueSuccess<T> extends Success<CurrentValue<? super T>> {

        protected ValueSuccess(CurrentValue<? super T> value) {
            super(value);
        }

        /**
         * Sets the value, based on the {@link CurrentValue current value}
         *
         * @param value the new value
         * @return true, if the set was successful
         * @see CurrentValue#set(Object)
         */
        public boolean set(T value) {
            return getValue().get().set(value);
        }
    }

    /**
     * <pre>
     * An unsuccessful {@link Result result}
     * Failures contain {@link Optional optional} {@link #getErrorMessage() error messages}
     * </pre>
     */
    final class Failure implements Result {
        private final Optional<String> errorMessage;

        private Failure() {
            this(null);
        }

        private Failure(String errorMessage) {
            this.errorMessage = Optional.ofNullable(errorMessage);
        }

        /**
         * Gets the error message of this failure
         *
         * @return the {@link Optional optional} error message
         */
        public Optional<String> getErrorMessage() {
            return errorMessage;
        }
    }
}
