package com.pqqqqq.escript.lang.phrase.condition;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The greater than phrase, which checks if the left literal is greater than the right
 *
 * Some examples:
 *      <code>1 is greater than 2
 *      $var more than 5</code>
 * </pre>
 */
public class GreaterThan implements ConditionalPhrase {
    private static final GreaterThan INSTANCE = new GreaterThan();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 is? greater|more|higher than|then $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static GreaterThan instance() {
        return INSTANCE;
    }

    private GreaterThan() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.asNumber() > literal2.asNumber());
    }
}
