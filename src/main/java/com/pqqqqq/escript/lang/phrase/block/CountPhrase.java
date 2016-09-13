package com.pqqqqq.escript.lang.phrase.block;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.container.DatumContainer;
import com.pqqqqq.escript.lang.data.variable.Variable;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 *
 * <pre>
 * The count phrase, which counts up to a number
 * This is basically a for loop
 *
 * Examples:
 *      <code>count number from 1 to 6:
 *      count num to 6 by 2:</code>
 * </pre>
 */
public class CountPhrase implements Phrase {
    private static final CountPhrase INSTANCE = new CountPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("count ^Name from $Start to $End by? $Increment*", true),
            Syntax.compile("count ^Name to $End by? $Increment*", true)

    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static CountPhrase instance() {
        return INSTANCE;
    }

    private CountPhrase() {
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

        double start = ctx.getLiteral("Start", 1).asNumber();
        double end = ctx.getLiteral("End").asNumber();
        double increment = ctx.getLiteral("Increment", 1).asNumber();

        for (double trace = start; trace <= end; trace += increment) {
            variable.setValue(Literal.fromObject(trace));
            ctx.executeBlock();

            iterations++;
        }

        return Result.success(iterations);
    }
}
