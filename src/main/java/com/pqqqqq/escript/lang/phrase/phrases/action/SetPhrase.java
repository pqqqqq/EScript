package com.pqqqqq.escript.lang.phrase.phrases.action;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.container.DatumContainer;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The variable phrase, which will create, or modify a variable/value
 * Examples:
 *      <code>set var to "hello"
 *      set var
 *      set player's health to 10</code>
 * </pre>
 */
public class SetPhrase implements Phrase {
    private static final SetPhrase INSTANCE = new SetPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("set|change|modify #Name to $Value"),
            Syntax.compile("create #Name with? value* $Value*")

            /*Pattern.compile("^(send(\\s+?))?(message|msg)(\\s+?)(?<Message>\\S+?)$", Pattern.CASE_INSENSITIVE),
            Pattern.compile("^(send(\\s+?))?(message|msg)(\\s+?)(?<Message>\\S+?)(\\s+?)" +
                    StringUtils.from("to").getOutsideQuotePattern() + "(\\s+?)(?<Player>.+?)$", Pattern.CASE_INSENSITIVE)*/
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static SetPhrase instance() {
        return INSTANCE;
    }

    private SetPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result execute(Context ctx) {
        String name = ctx.getStrarg("Name");
        Literal literalValue = ctx.getLiteral("Value", (Object) null);
        DatumContainer container = ctx.getContainer("Name");

        if (container instanceof DatumContainer.Value) {
            DatumContainer.Value valueContainer = (DatumContainer.Value) container;
            MutableValue mutableValue = valueContainer.resolveVariable(ctx);
            Serializer<?> serializer = mutableValue.getSerializer();

            Object value = (serializer == null ? literalValue.getValue().orElse(null) : (literalValue.isEmpty() ? null : serializer.deserialize(literalValue)));
            mutableValue.setValue(value); // This may error
            return Result.success();
        }

        return Result.failure("\"%s\" (Container: %s) is not a mutable value.", name, container.getClass().getSimpleName());
    }
}
