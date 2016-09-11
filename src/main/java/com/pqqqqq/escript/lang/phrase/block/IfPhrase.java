package com.pqqqqq.escript.lang.phrase.block;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 *
 * <pre>
 * The if phrase, which runs its block if the inner condition is true
 * Examples:
 *      <code>if 1 is 1:
 *      if "hello" is not "goodbye":</code>
 * </pre>
 */
public class IfPhrase implements Phrase {
    private static final IfPhrase INSTANCE = new IfPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("if $Condition", true)
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
