package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.mutable.variable.Variable;
import com.pqqqqq.escript.lang.exception.InvalidTypeException;
import com.pqqqqq.escript.lang.line.Context;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * A type that can be resolved into a {@link Literal} at runtime
 */
public interface DatumContainer {

    /**
     * Attempts to resolve the container into a {@link Literal value}
     *
     * @param ctx the {@link Context context}
     * @return the literal value
     */
    Literal resolve(Context ctx);

    /**
     * A datum container whose {@link Variable variable} can also be resolved
     */
    interface Value extends DatumContainer {

        /**
         * Attempts to resolve the container into a {@link MutableValue mutable value}
         *
         * @param ctx the {@link Context context}
         * @return the variable
         * @throws InvalidTypeException if the relevant container is not a value
         */
        MutableValue<?> resolveVariable(Context ctx);
    }
}
