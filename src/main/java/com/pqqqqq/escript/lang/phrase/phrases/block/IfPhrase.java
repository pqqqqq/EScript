package com.pqqqqq.escript.lang.phrase.phrases.block;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The if phrase, which runs its block if the inner condition is true
 * Examples:
 *      <code>if 1 equals 1:
 *      if "hello" is not equal to "goodbye":</code>
 * </pre>
 */
public class IfPhrase implements Phrase {
    private static final IfPhrase INSTANCE = new IfPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("if $Condition:")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static IfPhrase instance() {
        return INSTANCE;
    }

    private IfPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        boolean condition = ctx.getLiteral("Condition").asBoolean();
        if (condition) {
            ctx.executeBlock();
        }

        return Result.success(condition);
    }
}
