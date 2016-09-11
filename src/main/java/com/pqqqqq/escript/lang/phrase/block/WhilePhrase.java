package com.pqqqqq.escript.lang.phrase.block;

import com.pqqqqq.escript.lang.data.container.DatumContainer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 *
 * <pre>
 * The while phrase, which runs its block as long as the inner condition remains true
 * Examples:
 *      <code>while 1 is 1:
 *      while $var is 2:</code>
 * </pre>
 */
public class WhilePhrase implements Phrase {
    private static final WhilePhrase INSTANCE = new WhilePhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("while %Condition", true)
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static WhilePhrase instance() {
        return INSTANCE;
    }

    private WhilePhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        DatumContainer condition = ctx.getContainer("Condition");
        int iterations = 0;

        while (condition.resolve(ctx).asBoolean()) {
            ctx.executeBlock();
            iterations++;
        }

        return Result.success(iterations);
    }
}
