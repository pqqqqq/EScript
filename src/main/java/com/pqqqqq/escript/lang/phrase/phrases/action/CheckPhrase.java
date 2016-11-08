package com.pqqqqq.escript.lang.phrase.phrases.action;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The check phrase, which checks a condition, which if not met, returns a break.
 * Examples:
 *      <code>check if player has permission "perm.permission"
 *      check that the health of "Joe" is less than 5
 *      </code>
 * </pre>
 */
public class CheckPhrase implements Phrase {
    private static final CheckPhrase INSTANCE = new CheckPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("check if|that? $Condition")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static CheckPhrase instance() {
        return INSTANCE;
    }

    private CheckPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return ctx.getLiteral("Condition").asBoolean() ? Result.success() : Result.breakResult();
    }
}
