package com.pqqqqq.escript.lang.phrase.phrases.getters.math;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The floor phrase, which rounds a number to its floor (always down)
 *
 * Some examples:
 *      <code>floor value of 5
 *      floor 5
 *      floor 5</code>
 * </pre>
 */
public class FloorPhrase implements Phrase {
    private static final FloorPhrase INSTANCE = new FloorPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("floor value? of? $Number")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static FloorPhrase instance() {
        return INSTANCE;
    }

    private FloorPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(Math.floor(ctx.getLiteral("Number").asNumber()));
    }
}
