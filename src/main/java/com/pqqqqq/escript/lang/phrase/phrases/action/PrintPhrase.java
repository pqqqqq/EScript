package com.pqqqqq.escript.lang.phrase.phrases.action;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The print phrase, which outputs something to the console.
 * Examples:
 *      <code>
 *          print "Hello!"
 *          console "Hello!"
 *          print to console "Hello!"
 *      </code>
 * </pre>
 */
public class PrintPhrase implements Phrase {
    private static final PrintPhrase INSTANCE = new PrintPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("print|console ${*}Message")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PrintPhrase instance() {
        return INSTANCE;
    }

    private PrintPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        System.out.println(ctx.getLiteral("Message").asString());
        return Result.success();
    }
}
