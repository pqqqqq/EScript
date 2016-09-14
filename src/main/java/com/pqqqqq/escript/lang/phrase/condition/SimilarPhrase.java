package com.pqqqqq.escript.lang.phrase.condition;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The similar phrase, which checks if two literals are similar to each other
 *
 * Some examples:
 *      <code>1 is similar to 1
 *      $var similar "hello"</code>
 * </pre>
 */
public class SimilarPhrase implements ConditionalPhrase {
    private static final SimilarPhrase INSTANCE = new SimilarPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 is? similar to? $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static SimilarPhrase instance() {
        return INSTANCE;
    }

    private SimilarPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.asString().equalsIgnoreCase(literal2.asString()));
    }
}
