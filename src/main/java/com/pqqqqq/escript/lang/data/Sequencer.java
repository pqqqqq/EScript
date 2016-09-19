package com.pqqqqq.escript.lang.data;

import com.pqqqqq.escript.lang.data.container.*;
import com.pqqqqq.escript.lang.exception.UnknownSymbolException;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.AnalysisResult;
import com.pqqqqq.escript.lang.phrase.Phrases;
import com.pqqqqq.escript.lang.phrase.arithmetic.ArithmeticPhrase;
import com.pqqqqq.escript.lang.phrase.condition.ConditionalPhrase;
import com.pqqqqq.escript.lang.util.string.SplitSequence;
import com.pqqqqq.escript.lang.util.string.StringUtilities;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The sequencer handles strargs from, generally from {@link Line#getStrarg(String)}
 * It compiles the argument into a compact {@link DatumContainer}, which can then be resolved at runtime
 * </pre>
 */
public class Sequencer {
    private static final Sequencer INSTANCE = new Sequencer();

    /**
     * Gets the main sequencer instance
     *
     * @return the main instance
     */
    public static Sequencer instance() {
        return INSTANCE;
    }

    private Sequencer() {
    }

    /**
     * Sequences the string argument into a {@link DatumContainer datum container}
     *
     * @param strarg the string argument
     * @return the new data container
     */
    public DatumContainer sequence(String strarg) {
        strarg = checkNotNull(strarg, "String argument cannot be null").trim(); // trim and check null
        if (strarg.isEmpty()) {
            return Literal.EMPTY;
        }

        // Get rid of brackets if they're still there
        if (strarg.startsWith("(") && strarg.endsWith(")")) {
            strarg = strarg.substring(1, strarg.length() - 1);
        }

        // Check if it's a CONDITIONAL phrase
        Optional<AnalysisResult> conditionAnalysis = Phrases.instance().analyze(strarg, (phrase) -> phrase instanceof ConditionalPhrase);
        if (conditionAnalysis.isPresent()) {
            return new PhraseContainer(Literal.fromObject(strarg), conditionAnalysis.get());
        }

        // Check if it's a ARITHMETIC phrase
        Optional<AnalysisResult> arithmeticAnalysis = Phrases.instance().analyze(strarg, (phrase) -> phrase instanceof ArithmeticPhrase);
        if (arithmeticAnalysis.isPresent()) {
            return new PhraseContainer(Literal.fromObject(strarg), arithmeticAnalysis.get());
        }

        // Check if it's an index
        SplitSequence indexSequence = StringUtilities.from(strarg).parseNextSequence(new String[]{"["});
        if (indexSequence != null) {
            return new IndexContainer(sequence(indexSequence.getBeforeSegment()), sequence(StringUtils.removeEnd(indexSequence.getAfterSegment(), "]")));
        }

        // Check if it's a list
        if (strarg.startsWith("{") && strarg.endsWith("}")) {
            String braceTrimmed = strarg.substring(1, strarg.length() - 1).trim();
            if (braceTrimmed.isEmpty()) { // More optimization here
                return Literal.EMPTY_LIST;
            }

            List<DatumContainer> containers = new ArrayList<>();
            StringUtilities.from(braceTrimmed).parseSplit(",").stream().map(this::sequence).forEach(containers::add);
            return new ListContainer(containers);
        }

        // Check if it's a normal phrase
        Optional<AnalysisResult> analysis = Phrases.instance().analyze(strarg, (phrase) -> !(phrase instanceof ConditionalPhrase || phrase instanceof ArithmeticPhrase));
        if (analysis.isPresent()) {
            return new PhraseContainer(Literal.fromObject(strarg), analysis.get());
        }

        // Check plain data
        Optional<Literal> literal = Literal.fromSequence(strarg);
        if (literal.isPresent()) {
            return literal.get();
        }

        // Check if it's a variable
        if (strarg.startsWith("$")) {
            return new VariableContainer(Literal.fromObject(strarg.substring(1)));
        }

        // Otherwise, throw an error
        throw new UnknownSymbolException("Cannot recognize symbol/datum: %s", strarg);
    }
}
