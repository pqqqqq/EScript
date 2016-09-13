package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;

import java.io.Serializable;

/**
 * Created by Kevin on 2016-09-02.
 *
 * <pre>
 * A type that can be resolved into a {@link Literal} at runtime
 * Containers are also data {@link Serializable serializable}
 * </pre>
 */
public interface DatumContainer extends Serializable {

    Literal resolve(Context ctx);
}
