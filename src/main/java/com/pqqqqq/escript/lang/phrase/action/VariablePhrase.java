package com.pqqqqq.escript.lang.phrase.action;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The variable phrase, which will create, or modify a variable
 * Examples:
 *      <code>set var to "hello"
 *      set var</code>
 * </pre>
 */
public class VariablePhrase implements Phrase {
    private static final VariablePhrase INSTANCE = new VariablePhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("set|change|modify ^Name to $Value"),
            Syntax.compile("create ^Name with? value* $Value*")

            /*Pattern.compile("^(send(\\s+?))?(message|msg)(\\s+?)(?<Message>\\S+?)$", Pattern.CASE_INSENSITIVE),
            Pattern.compile("^(send(\\s+?))?(message|msg)(\\s+?)(?<Message>\\S+?)(\\s+?)" +
                    StringUtils.from("to").getOutsideQuotePattern() + "(\\s+?)(?<Player>.+?)$", Pattern.CASE_INSENSITIVE)*/
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static VariablePhrase instance() {
        return INSTANCE;
    }

    private VariablePhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        String name = ctx.getStrarg("Name");
        Literal value = ctx.getLiteral("Value", (Object) null);

        ctx.getScript().createOrSet(name, value);
        return Result.success();
    }
}
