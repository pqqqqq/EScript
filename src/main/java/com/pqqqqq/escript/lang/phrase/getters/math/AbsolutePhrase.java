package com.pqqqqq.escript.lang.phrase.getters.math;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The absolute phrase, which gets the absolute value of a number
 *
 * Some examples:
 *      <code>absolute value of 5
 *      absolute 5
 *      abs 5</code>
 * </pre>
 */
public class AbsolutePhrase implements Phrase {
    private static final AbsolutePhrase INSTANCE = new AbsolutePhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("absolute|abs value? of? $Number")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static AbsolutePhrase instance() {
        return INSTANCE;
    }

    private AbsolutePhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(Math.abs(ctx.getLiteral("Number").asNumber()));
    }
}
