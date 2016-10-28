package com.pqqqqq.escript.lang.phrase.phrases.getters.math.statistics;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.data.store.list.ListModule;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

import java.util.stream.IntStream;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The sample phrase, which samples a vector x number of times.
 * This means a random item of the array is chosen x number of times.
 *
 * Some examples:
 *      <code>sample {1, 2, 3} 5 times
 *      sample 1:100 once</code>
 * </pre>
 */
public class SamplePhrase implements Phrase {
    private static final SamplePhrase INSTANCE = new SamplePhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("sample $Array $Amount? times*")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static SamplePhrase instance() {
        return INSTANCE;
    }

    private SamplePhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        LiteralStore store = ctx.getLiteral("Array").asStore();
        ListModule list = store.getListModule();
        Literal amountLiteral = ctx.getLiteral("Amount", 1);

        int amount;
        if (amountLiteral.isNumber()) {
            amount = amountLiteral.asNumber().intValue();
        } else {
            switch (amountLiteral.asString()) {
                case "once":
                    amount = 1;
                    break;
                case "twice":
                    amount = 2;
                    break;
                case "thrice": // Who the fuck would say thrice?
                    amount = 3;
                    break;
                default:
                    throw new NumberFormatException("Unknown number: " + amountLiteral.asString());
            }
        }

        LiteralStore result = LiteralStore.empty();
        IntStream.rangeClosed(1, amount).forEach((i) -> result.getListModule().add(list.sample()));
        return Result.success(result);
    }
}
