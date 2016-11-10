package com.pqqqqq.escript.lang.phrase.phrases.arithmetic;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The divide phrase, which divides two literals together
 *
 * Some examples:
 *      <code>1 / 1
 *      divide 2 by 3
 *      2 divided by 3</code>
 * </pre>
 */
public class DividePhrase implements ArithmeticPhrase {
    private static final DividePhrase INSTANCE = new DividePhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 /|divide|divided by? $Literal2"),
            Syntax.compile("divide $Literal1 by|and $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static DividePhrase instance() {
        return INSTANCE;
    }

    private DividePhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.div(literal2));
    }
}
