package com.pqqqqq.escript.lang.phrase.condition;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The less than phrase, which checks if the left literal is less than the right
 *
 * Some examples:
 *      <code>1 is less than 2
 *      $var lower than 5</code>
 * </pre>
 */
public class LessThan implements Phrase {
    private static final LessThan INSTANCE = new LessThan();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 is? less|lesser|lower than|then $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static LessThan instance() {
        return INSTANCE;
    }

    private LessThan() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.asNumber() < literal2.asNumber());
    }
}
