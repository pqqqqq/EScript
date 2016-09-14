package com.pqqqqq.escript.lang.phrase.condition;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The greater than or equal to phrase, which checks if the left literal is greater than, or equal to the right
 *
 * Some examples:
 *      <code>1 is less than or equal to 2
 *      $var lower than or equals 5</code>
 * </pre>
 */
public class GreaterThanOET implements ConditionalPhrase {
    private static final GreaterThanOET INSTANCE = new GreaterThanOET();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 is? greater|more|higher than|then or is|does? equal|equals to? $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static GreaterThanOET instance() {
        return INSTANCE;
    }

    private GreaterThanOET() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.asNumber() >= literal2.asNumber());
    }
}
