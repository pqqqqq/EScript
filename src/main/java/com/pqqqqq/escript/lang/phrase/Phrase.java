package com.pqqqqq.escript.lang.phrase;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.syntax.Component;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import com.pqqqqq.escript.lang.registry.RegistryEntry;
import com.pqqqqq.escript.lang.script.Script;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * A phrase denotes the action that will be performed by a given {@link Line line}
 * A phrase instance can be stored, and is considered to be a compiled line.
 * </pre>
 */
public interface Phrase extends RegistryEntry, Comparable<Phrase> {

    /**
     * Executes the phrase under the {@link Context context}
     *
     * @param ctx the context
     * @return the {@link Result result}
     */
    Result execute(Context ctx);

    /**
     * Gets an array of {@link Syntax syntaxes} used to match a {@link Line}
     *
     * @return an array of syntaxes
     */
    Syntax[] getSyntaxes();

    /**
     * <pre>
     * Gets the {@link Script.State state} in which this phrase should run
     * By default, the script runs at {@link Script.State#RUNTIME runtime}
     * </pre>
     *
     * @return the state
     */
    default Script.State getRunState() {
        return Script.State.RUNTIME;
    }

    /**
     * <pre>
     * Gets the priority number for this phrase.
     * If the priority for one phrase is higher than another, the phrase with a higher priority will attempt to be {@link #matches(Line) matched} first.
     * By default, a phrase's priority is 0.
     * </pre>
     *
     * @return the priority
     */
    default int getPriority() {
        return 0;
    }

    /**
     * <pre>
     * Check if the given {@link Line line} matches any of the given {@link #getSyntaxes() syntaxes}
     * If the line does match a pattern, an {@link AnalysisResult analysis} is returned, otherwise {@link Optional#empty()} is returned
     * </pre>
     *
     * @param line the line
     * @return the matched pattern if the line is this type of phrase
     */
    default Optional<AnalysisResult> matches(Line line) {
        return matches(line.getLine());
    }

    /**
     * <pre>
     * Check if the given line's contents matches any of the given {@link #getSyntaxes() syntaxes}
     * If the line does match a pattern, an {@link AnalysisResult analysis} is returned, otherwise {@link Optional#empty()} is returned
     * </pre>
     *
     * @param line the line
     * @return the matched pattern if the line is this type of phrase
     */
    default Optional<AnalysisResult> matches(String line) {
        for (Syntax syntax : getSyntaxes()) {
            Optional<Map<Component.ArgumentComponent, String>> match = syntax.matches(line);

            if (match.isPresent()) {
                return Optional.of(new AnalysisResult(this, match.get()));
            }
        }

        return Optional.empty();
    }

    @Override
    default int compareTo(Phrase other) { // Override for comparable
        if (getPriority() > other.getPriority()) { // Higher priority = closer to first in list
            return -1;
        }

        if (getPriority() < other.getPriority()) { // Lower priority = closer to last in list
            return 1;
        }

        return 0; // Same means keep natural order
    }
}
