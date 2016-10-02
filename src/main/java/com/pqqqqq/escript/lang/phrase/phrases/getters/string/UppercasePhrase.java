package com.pqqqqq.escript.lang.phrase.phrases.getters.string;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The uppercase phrase, which converts all characters in a string to uppercase
 *
 * Some examples:
 *      <code>uppercase value of "Hello"
 *      upper case "HeLLo"</code>
 * </pre>
 */
public class UppercasePhrase implements Phrase {
    private static final UppercasePhrase INSTANCE = new UppercasePhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("uppercase value? of? $String"),
            Syntax.compile("upper case value? of? $String")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static UppercasePhrase instance() {
        return INSTANCE;
    }

    private UppercasePhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(ctx.getLiteral("String").asString().toUpperCase());
    }
}
