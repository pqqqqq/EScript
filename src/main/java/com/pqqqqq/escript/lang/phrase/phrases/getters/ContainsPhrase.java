package com.pqqqqq.escript.lang.phrase.phrases.getters;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

import java.util.List;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The contains phrase, which checks if one item is contained in another
 *
 * Some examples:
 *      <code>"h" is in "hello"
 *      "hello" is in {1, "123", "hello"}
 *      {1, 2, 3} contains 2</code>
 * </pre>
 */
public class ContainsPhrase implements Phrase {
    private static final ContainsPhrase INSTANCE = new ContainsPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Check is in $Container"),
            Syntax.compile("$Container contains|contain|has $Check")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static ContainsPhrase instance() {
        return INSTANCE;
    }

    private ContainsPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal container = ctx.getLiteral("Container");
        Literal check = ctx.getLiteral("Check");

        if (container.isList()) { // List, we check entries
            List<Literal> list = container.asList();
            return Result.success(list.contains(check));
        } else { // Otherwise, take it as a string
            String stringContainer = container.asString();
            return Result.success(stringContainer.contains(check.asString()));
        }
    }
}
