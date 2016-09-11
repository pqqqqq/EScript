package com.pqqqqq.escript.lang.data;

import com.pqqqqq.escript.lang.data.container.ConditionContainer;
import com.pqqqqq.escript.lang.data.container.DatumContainer;
import com.pqqqqq.escript.lang.data.container.PhraseContainer;
import com.pqqqqq.escript.lang.data.container.VariableContainer;
import com.pqqqqq.escript.lang.data.container.expression.ArithmeticContainer;
import com.pqqqqq.escript.lang.data.container.expression.ConditionalExpressionContainer;
import com.pqqqqq.escript.lang.exception.UnknownSymbolException;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.AnalysisResult;
import com.pqqqqq.escript.lang.phrase.Phrases;
import com.pqqqqq.escript.lang.util.string.SplitSequence;
import com.pqqqqq.escript.lang.util.string.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-09-02.
 *
 * <pre>
 * The sequencer handles strargs from, generally from {@link Line#getStrarg(String)}
 * It compiles the argument into a compact {@link DatumContainer}, which can then be resolved at runtime
 * </pre>
 */
public class Sequencer {
    private static final String[][] LITERAL_DELIMITER_GROUPS = {{" + ", " - "}, {"*", "/", "%"}, {"^", "`"}}; // The +/- group is first since we want these split first, not last
    private static final String[][] CONDITION_DELIMITER_GROUPS = {{"is not", "less than or equal to", "greater than or equal to", "dissimilar"}, {"is", "less than", "greater than", "similar"}}; // Different groups because of the crossing of delimiters
    private static final Sequencer INSTANCE = new Sequencer();
    private final Condition conditionInstance = new Condition();

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

        // Check if it's a condition
        Optional<DatumContainer> conditionLiteral = conditionInstance.parse(strarg);
        if (conditionLiteral.isPresent()) {
            return conditionLiteral.get();
        }

        SplitSequence triple = StringUtils.from(strarg).parseNextSequence(LITERAL_DELIMITER_GROUPS); // Split into ordered triple segments
        if (triple == null || triple.getDelimiter() == null) { // Check if there's no split string
            // TODO logic needed!!
            // TODO Is it okay to have plain data and variables before phrases?

            // Check plain data
            Optional<Literal> literal = Literal.fromSequence(strarg);
            if (literal.isPresent()) {
                return literal.get();
            }

            // Check if it's a variable
            if (strarg.startsWith("$")) {
                return new VariableContainer(Literal.fromObject(strarg.substring(1)));
            }

            // Check if it's a phrase
            Optional<AnalysisResult> analysis = Phrases.instance().analyze(strarg);
            if (analysis.isPresent()) {
                return new PhraseContainer(Literal.fromObject(strarg), analysis.get());
            }

            // Otherwise, throw an error
            throw new UnknownSymbolException("Cannot recognize symbol/datum: %s", strarg);
        }

        return new ArithmeticContainer(sequence(triple.getBeforeSegment()), sequence(triple.getAfterSegment()), triple.getDelimiter(), strarg);
    }

    class Condition {
        private Condition() {
        }

        Optional<DatumContainer> parse(String strarg) {
            String[] splitOr = StringUtils.from(strarg).parseSplit(" or "); // 'Or' takes precedence over 'and'
            List<List<ConditionalExpressionContainer>> mainExpressionList = new ArrayList<>();

            for (String orCondition : splitOr) {
                String[] splitAnd = StringUtils.from(orCondition).parseSplit(" and ");
                List<ConditionalExpressionContainer> andExpressionList = new ArrayList<>();

                for (String condition : splitAnd) {
                    SplitSequence triple = StringUtils.from(condition).parseNextSequence(CONDITION_DELIMITER_GROUPS);

                    if (triple == null || triple.getDelimiter() == null) {
                        return Optional.empty(); // Need exactly a left side and a right side
                    }

                    // Get literals for these values
                    DatumContainer leftSideLiteral = Sequencer.this.sequence(triple.getBeforeSegment());
                    DatumContainer rightSideLiteral = Sequencer.this.sequence(triple.getAfterSegment());
                    String comparator = triple.getDelimiter();

                    ConditionalExpressionContainer container = new ConditionalExpressionContainer(leftSideLiteral, rightSideLiteral, comparator);
                    if (splitAnd.length == 1 && splitOr.length == 1) { // If this is the only thing, return this alone
                        return Optional.of(container);
                    }

                    andExpressionList.add(container);
                }

                mainExpressionList.add(andExpressionList);
            }

            return Optional.of(new ConditionContainer(mainExpressionList));
        }
    }
}
