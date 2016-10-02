package com.pqqqqq.escript.lang.phrase.phrases.arithmetic;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The power phrase, which takes the power of two literals
 *
 * Some examples:
 *      <code>1 / 1
 *      divide 2 by 3
 *      2 divided by 3</code>
 * </pre>
 */
public class PowerPhrase implements ArithmeticPhrase {
    private static final PowerPhrase INSTANCE = new PowerPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 **|power the? $Literal2"),
            Syntax.compile("raise $Literal1 to the? $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PowerPhrase instance() {
        return INSTANCE;
    }

    private PowerPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.pow(literal2));
    }
}
