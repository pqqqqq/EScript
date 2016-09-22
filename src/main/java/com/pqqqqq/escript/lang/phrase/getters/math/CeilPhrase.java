package com.pqqqqq.escript.lang.phrase.getters.math;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The ceil phrase, which rounds a number to its ceiling (always up)
 *
 * Some examples:
 *      <code>ceil value of 5
 *      ceil 5
 *      ceiling 5</code>
 * </pre>
 */
public class CeilPhrase implements Phrase {
    private static final CeilPhrase INSTANCE = new CeilPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("ceiling|ceil value? of? $Number")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static CeilPhrase instance() {
        return INSTANCE;
    }

    private CeilPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(Math.ceil(ctx.getLiteral("Number").asNumber()));
    }
}
