package com.pqqqqq.escript.lang.phrase.phrases.getters.string;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The lowercase phrase, which converts all characters in a string to lowercase
 *
 * Some examples:
 *      <code>lowercase value of "Hello"
 *      lower case "HeLLo"</code>
 * </pre>
 */
public class LowercasePhrase implements Phrase {
    private static final LowercasePhrase INSTANCE = new LowercasePhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("lowercase value? of? $String"),
            Syntax.compile("lower case value? of? $String")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static LowercasePhrase instance() {
        return INSTANCE;
    }

    private LowercasePhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(ctx.getLiteral("String").asString().toLowerCase());
    }
}
