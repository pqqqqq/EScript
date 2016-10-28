package com.pqqqqq.escript.lang.phrase.phrases.getters.math.statistics;

import com.pqqqqq.escript.lang.data.store.list.ListModule;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * Conducts a random trial, which checks if the given event occurs based on its probability.
 * Note that the number is a <strong>probability</strong> and not a percentage.
 *
 * Some examples:
 *      <code>test trial 0.5
 *      trial 0.8</code>
 * </pre>
 */
public class TrialPhrase implements Phrase {
    private static final TrialPhrase INSTANCE = new TrialPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("conduct|test? trial $Probability")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static TrialPhrase instance() {
        return INSTANCE;
    }

    private TrialPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        double probability = ctx.getLiteral("Probability").asNumber();
        double RNG = ListModule.RANDOM.nextDouble();

        return Result.success(probability >= RNG); // Checks if probability is higher than randomly generated number
    }
}
