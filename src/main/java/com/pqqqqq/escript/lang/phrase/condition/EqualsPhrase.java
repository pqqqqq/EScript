package com.pqqqqq.escript.lang.phrase.condition;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The equals phrase, which checks if two literals are equal to each other
 *
 * Some examples:
 *      <code>1 is equal to 1
 *      $var equals "hello"</code>
 * </pre>
 */
public class EqualsPhrase implements ConditionalPhrase {
    private static final EqualsPhrase INSTANCE = new EqualsPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 is|does? equal|equals to? $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static EqualsPhrase instance() {
        return INSTANCE;
    }

    private EqualsPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.equals(literal2));
    }
}
