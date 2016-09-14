package com.pqqqqq.escript.lang.phrase.condition;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The dissimilar phrase, which checks if two literals are NOT similar to each other
 *
 * Some examples:
 *      <code>1 is not similar to 1
 *      $var dissimilar "hello"</code>
 * </pre>
 */
public class DissimilarPhrase implements ConditionalPhrase {
    private static final DissimilarPhrase INSTANCE = new DissimilarPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 is? not similar to? $Literal2"),
            Syntax.compile("$Literal1 is? dissimilar to? $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static DissimilarPhrase instance() {
        return INSTANCE;
    }

    private DissimilarPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(!literal1.asString().equalsIgnoreCase(literal2.asString()));
    }
}
