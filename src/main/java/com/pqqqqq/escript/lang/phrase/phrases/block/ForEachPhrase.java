package com.pqqqqq.escript.lang.phrase.phrases.block;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.variable.Variable;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The for each phrase, which iterates through a list
 *
 * Examples:
 *      <code>for each entry in $var:
 *      for each entry in {"hello", "goodbye"}</code>
 * </pre>
 */
public class ForEachPhrase implements Phrase {
    private static final ForEachPhrase INSTANCE = new ForEachPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("for each? $^Name in $Array:")

    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static ForEachPhrase instance() {
        return INSTANCE;
    }

    private ForEachPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        int iterations = 0;

        String name = ctx.getStrarg("Name");
        Variable variable = ctx.getScript().createOrGet(name);

        LiteralStore store = ctx.getLiteral("Array").asStore();
        for (Literal literal : store.getListModule().toImmutable()) {
            variable.setValue(literal);
            ctx.executeBlock();

            iterations++;
        }

        return Result.success(iterations);
    }
}
