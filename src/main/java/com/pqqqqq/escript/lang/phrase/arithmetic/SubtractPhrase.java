package com.pqqqqq.escript.lang.phrase.arithmetic;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The subtract phrase, which subtracts two literals from each other
 *
 * Some examples:
 *      <code>1 - 1
 *      subtract 2 from 3
 *      take difference of 2 and 1
 *      2 minus 3</code>
 * </pre>
 */
public class SubtractPhrase implements ArithmeticPhrase {
    private static final SubtractPhrase INSTANCE = new SubtractPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 -|minus $Literal2"),
            Syntax.compile("subtract $Literal1 from $Literal2"),
            Syntax.compile("take? difference|diff of? $Literal1 and|from $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static SubtractPhrase instance() {
        return INSTANCE;
    }

    private SubtractPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.sub(literal2));
    }
}
