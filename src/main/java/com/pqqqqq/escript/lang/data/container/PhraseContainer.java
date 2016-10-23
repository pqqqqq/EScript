package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.exception.FailedLineException;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.line.RunVessel;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.AnalysisResult;

/**
 * Created by Kevin on 2015-06-17.
 * Represents a {@link DatumContainer} which resolves a phrase's {@link Literal} value at runtime
 */
public class PhraseContainer implements DatumContainer.Value {
    private final Literal phrase;
    private final AnalysisResult analysis;

    /**
     * Creates a new {@link PhraseContainer} instance with the given phrase, and its {@link AnalysisResult analysis result}
     *
     * @param phrase   the phrase
     * @param analysis the analysis result
     */
    public PhraseContainer(Literal phrase, AnalysisResult analysis) {
        this.phrase = phrase;
        this.analysis = analysis;
    }

    /**
     * Gets the {@link Literal literal} phrase string associated with this {@link PhraseContainer phrase container}
     *
     * @return the phrase
     */
    public Literal getPhrase() {
        return phrase;
    }

    /**
     * Gets the {@link AnalysisResult analysis result}
     *
     * @return the analysis result
     */
    public AnalysisResult getAnalysis() {
        return analysis;
    }

    @Override
    public Literal resolve(Context ctx) {
        Result result = resolveResult(ctx);
        if (result instanceof Result.Success) {
            return ((Result.Success) result).getLiteralValue();
        } else {
            Result.Failure fail = (Result.Failure) result;
            throw new FailedLineException("Line failed because: \"%s\"", fail.getErrorMessage().orElse("UNKNOWN"));
        }
    }

    @Override
    public MutableValue<?> resolveVariable(Context ctx) {
        Result result = resolveResult(ctx);
        if (result instanceof Result.ValueSuccess) {
            Result.ValueSuccess<?> valueResult = (Result.ValueSuccess<?>) result;
            return valueResult.getValue().orElse(null);
        }

        return null;
    }

    /**
     * Resolves the phrase container to its base {@link Result result}
     *
     * @param ctx the {@link Context context}
     * @return the result
     */
    public Result resolveResult(Context ctx) {
        RunVessel vessel = Line.builder().script(ctx.getLine().getRawScript()).line(ctx.getLine().getLine()).number(ctx.getLine().getLineNumber()).tabs(ctx.getLine().getTabulations()).analysis(getAnalysis()).build().toContext(ctx.getScript()).toVessel();
        vessel.run();

        return vessel.getResult();
    }
}
