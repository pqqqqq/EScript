package com.pqqqqq.escript.lang.phrase.arithmetic;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The add phrase, which adds two literals together
 *
 * Some examples:
 *      <code>1 + 1
 *      add 2 to 3
 *      2 plus 3
 *      add "hello" to {}</code>
 * </pre>
 */
public class AddPhrase implements Phrase {
    private static final AddPhrase INSTANCE = new AddPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 +|plus|add|added to? $Literal2"),
            Syntax.compile("add $Literal1 to|and $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static AddPhrase instance() {
        return INSTANCE;
    }

    private AddPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.add(literal2));
    }
}
