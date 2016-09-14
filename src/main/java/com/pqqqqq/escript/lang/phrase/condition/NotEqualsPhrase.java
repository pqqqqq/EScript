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
 * The not equals phrase, which checks if two literals are NOT equal to each other
 *
 * Some examples:
 *      <code>1 is not equal to 1
 *      $var not equals "hello"
 *      $var does not equal "Hello"</code>
 * </pre>
 */
public class NotEqualsPhrase implements Phrase {
    private static final NotEqualsPhrase INSTANCE = new NotEqualsPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 is|does? not equal|equals to? $Literal2"),
            Syntax.compile("$Literal1 doesn't equal|equals to? $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static NotEqualsPhrase instance() {
        return INSTANCE;
    }

    private NotEqualsPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(!literal1.equals(literal2));
    }
}
