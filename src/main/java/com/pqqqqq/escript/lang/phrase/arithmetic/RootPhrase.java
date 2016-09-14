package com.pqqqqq.escript.lang.phrase.arithmetic;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The root phrase, which takes the root of two literals
 *
 * Some examples:
 *      <code>4 // 2
 *      root 2 of 4</code>
 * </pre>
 */
public class RootPhrase implements Phrase {
    private static final RootPhrase INSTANCE = new RootPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 //|root $Literal2"),
            Syntax.compile("root $Literal2 of $Literal1")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static RootPhrase instance() {
        return INSTANCE;
    }

    private RootPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.root(literal2));
    }
}
