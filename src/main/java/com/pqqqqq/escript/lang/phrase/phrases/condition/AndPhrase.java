package com.pqqqqq.escript.lang.phrase.phrases.condition;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The and phrase, which checks if both of the two literals is true
 *
 * Some examples:
 *      <code>1 is not similar to 1 and 1 equals 1</code>
 * </pre>
 */
public class AndPhrase implements ConditionalPhrase {
    private static final AndPhrase INSTANCE = new AndPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 and $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static AndPhrase instance() {
        return INSTANCE;
    }

    private AndPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.asBoolean() && literal2.asBoolean());
    }
}
