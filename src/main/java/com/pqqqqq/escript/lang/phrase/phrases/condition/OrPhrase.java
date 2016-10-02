package com.pqqqqq.escript.lang.phrase.phrases.condition;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The or phrase, which checks if one of the two literals is true
 *
 * Some examples:
 *      <code>1 is not similar to 1 or false</code>
 * </pre>
 */
public class OrPhrase implements ConditionalPhrase {
    private static final OrPhrase INSTANCE = new OrPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 or $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static OrPhrase instance() {
        return INSTANCE;
    }

    private OrPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.asBoolean() || literal2.asBoolean());
    }
}
