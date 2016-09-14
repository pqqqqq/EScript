package com.pqqqqq.escript.lang.phrase.arithmetic;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The multiply phrase, which multiplies two literals together
 *
 * Some examples:
 *      <code>1 * 1
 *      multiply 2 and 3
 *      2 times 3</code>
 * </pre>
 */
public class MultiplyPhrase implements ArithmeticPhrase {
    private static final MultiplyPhrase INSTANCE = new MultiplyPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 *|times|multiply|multiplied by? $Literal2"),
            Syntax.compile("multiply $Literal1 by|and $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static MultiplyPhrase instance() {
        return INSTANCE;
    }

    private MultiplyPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.mult(literal2));
    }
}
