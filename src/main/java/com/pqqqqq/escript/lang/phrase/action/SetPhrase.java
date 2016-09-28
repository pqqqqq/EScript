package com.pqqqqq.escript.lang.phrase.action;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.container.PhraseContainer;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.AnalysisResult;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Phrases;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.getters.sponge.ValuePhrase;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

import java.util.Optional;

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
public class SetPhrase implements Phrase {
    private static final SetPhrase INSTANCE = new SetPhrase();
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
        Optional<AnalysisResult> analysis = Phrases.instance().analyze(name, (phrase) -> phrase instanceof ValuePhrase);

        if (analysis.isPresent()) { // Change the phrase
            Result result = new PhraseContainer(Literal.fromObject(name), analysis.get()).resolveResult(ctx);

            if (result instanceof Result.ValueSuccess) {
                Result.ValueSuccess valueResult = (Result.ValueSuccess) result;

                ValuePhrase phrase = (ValuePhrase) analysis.get().getPhrase();
                Class<?> correspondingClass = phrase.getCorrespondingClass();

                if (correspondingClass == null) {
                    valueResult.set(ctx.getLiteral("Value", (Object) null).getValue().orElse(null));
                } else {
                    valueResult.set(ctx.getSerialized("Value", correspondingClass));
                }
                return Result.success();
            } else {
                return Result.failure("%s is not a bounded value result.", name);
            }
        } else { // Otherwise, just make it a variable
            ctx.getScript().createOrSet(name, ctx.getLiteral("Value", (Object) null));
        }

        return Result.success();
    }
}
