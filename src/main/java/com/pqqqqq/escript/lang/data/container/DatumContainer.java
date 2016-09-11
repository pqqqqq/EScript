package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;

/**
 * Created by Kevin on 2016-09-02.
 *
 * A type that can be resolved into a {@link Literal} at runtime
 */
public interface DatumContainer {

    Literal resolve(Context ctx);
}
