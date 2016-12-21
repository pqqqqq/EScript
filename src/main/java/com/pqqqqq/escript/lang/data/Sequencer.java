package com.pqqqqq.escript.lang.data;

import com.pqqqqq.escript.lang.data.container.*;
import com.pqqqqq.escript.lang.data.mutable.property.PropertyType;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.exception.UnknownSymbolException;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.analysis.Analysis;
import com.pqqqqq.escript.lang.phrase.analysis.AnalysisResult;
import com.pqqqqq.escript.lang.util.string.SplitSequence;
import com.pqqqqq.escript.lang.util.string.StringUtilities;
import com.pqqqqq.escript.lang.util.string.TrackerProperties;
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

        // Create an analysis instance here, and if necessary analyses can be run with different predicates
        Analysis analysis = Analysis.from(strarg);

        // Check if it's a normal phrase
        Optional<AnalysisResult> normalAnalysis = analysis.analyze(/*phrase -> !(phrase instanceof ConditionalPhrase || phrase instanceof ArithmeticPhrase)*/);
        if (normalAnalysis.isPresent()) {
            return new PhraseContainer(Literal.fromObject(strarg), normalAnalysis.get());
        }

        // Check if it's an index
        SplitSequence indexSequence = StringUtilities.from(strarg).parseNextSequence(TrackerProperties.builder().last(true).build(), new String[]{"[", "$"});
        if (indexSequence != null) {
            switch (indexSequence.getDelimiter()) {
                case "[": // Sequenced index
                    return new IndexContainer(sequence(indexSequence.getBeforeSegment()), sequence(StringUtils.removeEnd(indexSequence.getAfterSegment(), "]"))); // Remove ending bracket
                case "$": // Non-sequenced index
                    if (!indexSequence.getBeforeSegment().trim().isEmpty()) { // An empty beginning means the $ is leading, meaning it's a variable
                        return new IndexContainer(sequence(indexSequence.getBeforeSegment()), Literal.fromObject(indexSequence.getAfterSegment().trim()));
                    }
                    break;
                default:
                    break; // How would this even be possible?
            }
        }

        // Check if it's a list
        if (strarg.startsWith("{") && strarg.endsWith("}")) {
            String braceTrimmed = strarg.substring(1, strarg.length() - 1).trim();
            if (braceTrimmed.isEmpty()) { // Optimization here
                return LiteralStore.emptyLiteral().get();
            }

            List<DatumContainer> containers = new ArrayList<>();
            StringUtilities.from(braceTrimmed).parseSplit(",").stream().map(this::sequence).forEach(containers::add);
            return new StoreContainer(containers);
        }

        // Check if it's a range vector
        List<String> rangeSplit = StringUtilities.from(strarg).parseSplit(":");
        if (rangeSplit.size() == 2) {
            return new RangeContainer(sequence(rangeSplit.get(0)), sequence(rangeSplit.get(1)));
        }

        // Check if it's a literal map entry
        List<String> entrySplit = StringUtilities.from(strarg).parseSplit("=");
        if (entrySplit.size() == 2) {
            return new EntryReplicateContainer(entrySplit.get(0).trim(), sequence(entrySplit.get(1)));
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

        // Check for property
        Optional<PropertyType> propertyType = PropertyType.fromString(strarg);
        if (propertyType.isPresent()) {
            // We have to check if we're at runtime or compile time.
            // Compile time would mean the script is null, and we'll return an empty literal
            return (ctx) -> {
                if (ctx.getScript() == null) {
                    return Literal.EMPTY;
                } else {
                    return ctx.getScript().getProperties().getValue(propertyType.get()).map(property -> property.resolve(ctx)).orElse(Literal.EMPTY); // Lambda expression here, or class?
                }
            };
        }

        // Otherwise, throw an error
        throw new UnknownSymbolException("Cannot recognize symbol/datum: %s", strarg);
    }
}
