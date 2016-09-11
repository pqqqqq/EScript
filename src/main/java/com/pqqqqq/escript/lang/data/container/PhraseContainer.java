package com.pqqqqq.escript.lang.data.container;


import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.exception.FailedLineException;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.line.RunVessel;
import com.pqqqqq.escript.lang.phrase.AnalysisResult;
import com.pqqqqq.escript.lang.phrase.Result;

/**
 * Created by Kevin on 2015-06-17.
 * Represents a {@link DatumContainer} which resolves a phrase's {@link Literal} value at runtime
 */
public class PhraseContainer implements DatumContainer {
    private final Literal phrase;
    private final AnalysisResult analysis;

    /**
     * Creates a new {@link PhraseContainer} instance with the given phrase, and its {@link AnalysisResult analysis result}
     *
     * @param phrase the phrase
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
        RunVessel vessel = Line.builder().script(ctx.getLine().getRawScript()).line(ctx.getLine().getLine()).number(ctx.getLine().getLineNumber()).tabs(ctx.getLine().getTabulations()).analysis(getAnalysis()).build().toContext(ctx.getScript()).toVessel();
        vessel.run();

        Result result = vessel.getResult();
        if (result instanceof Result.Success) {
            return ((Result.Success) result).getLiteralValue();
        } else {
            Result.Failure fail = (Result.Failure) result;
            throw new FailedLineException("Line failed because: \"%s\"", fail.getErrorMessage().orElse("UNKNOWN"));
        }
    }
}
