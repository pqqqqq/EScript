package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.variable.Variable;
import com.pqqqqq.escript.lang.line.Context;

import java.util.Optional;

/**
 * Created by Kevin on 2016-09-02.
 *
 * <pre>
 * A {@link DatumContainer} that attempts to resolve a {@link Variable}
 * </pre>
 */
public class VariableContainer implements DatumContainer {
    private final DatumContainer variableName;

    /**
     * Creates a new variable container with the given {@link DatumContainer datum container} as its name
     *
     * @param variableName the name (as a datum container)
     */
    public VariableContainer(DatumContainer variableName) {
        this.variableName = variableName;
    }

    @Override
    public Literal resolve(Context ctx) {
        String name = variableName.resolve(ctx).asString();
        Optional<Variable> variable = ctx.getScript().getVariable(name);

        if (variable.isPresent()) {
            return variable.get().getValue().resolve(ctx); // Resolve value if necessary
        } else {
            return Literal.EMPTY;
        }
    }
}
