package com.pqqqqq.escript.lang.phrase.phrases.getters.string;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The substring phrase, which creates a substring of the string
 *
 * Some examples:
 *      <code>substring of "Hello" from 2
 *      substring "Hello" from 1 to 4
 *      substring "HEllo" to 3</code>
 * </pre>
 */
public class SubstringPhrase implements Phrase {
    private static final SubstringPhrase INSTANCE = new SubstringPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("substring of? $String from? $Start* to? $End*")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static SubstringPhrase instance() {
        return INSTANCE;
    }

    private SubstringPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal stringLiteral = ctx.getLiteral("String");
        String string = stringLiteral.asString();

        int start = ctx.getLiteral("Start", 1).asNumber().intValue() - 1; // Base 1
        int end = ctx.getLiteral("End", string.length()).asNumber().intValue(); // Inclusive end

        return Result.success(string.substring(start, end));
    }
}
