package com.pqqqqq.escript.lang.phrase.getters.math;

import com.flowpowered.math.GenericMath;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The ceil phrase, which rounds a number normally (0.5 and above rounds up, otherwise round down)
 *
 * Some examples:
 *      <code>round value of 5
 *      round 5</code>
 * </pre>
 */
public class RoundPhrase implements Phrase {
    private static final RoundPhrase INSTANCE = new RoundPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("round value? of? $Number")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static RoundPhrase instance() {
        return INSTANCE;
    }

    private RoundPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(GenericMath.round(ctx.getLiteral("Number").asNumber(), 0));
    }
}
