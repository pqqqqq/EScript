package com.pqqqqq.escript.lang.phrase.analysis;

import com.pqqqqq.escript.lang.exception.EScriptException;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Phrases;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.util.string.StringUtilities;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by Kevin on 2016-10-01.
 * <p>
 * Runs a full phrase analysis, which attempts to match a {@link Line line} to its {@link Phrase} and {@link Syntax syntax}
 */
public class Analysis {
    private final String line;
    private final List<String> stringComponents;
    private final boolean colon;

    /**
     * Creates a new analysis from the {@link Line line}
     *
     * @param line the line
     * @return the new analysis instance
     */
    public static Analysis from(Line line) {
        return from(line.getLine());
    }

    /**
     * Creates a new analysis from the line
     *
     * @param line the line
     * @return the new analysis instance
     */
    public static Analysis from(String line) {
        String colonOmitted = StringUtils.removeEnd(line, ":"); // We must remove colon from components
        return new Analysis(line, StringUtilities.from(colonOmitted).parseSplit(" "), line.endsWith(":")); // Try to remove colon either way
    }

    private Analysis(String line, List<String> stringComponents, boolean colon) {
        this.line = line;
        this.stringComponents = stringComponents;
        this.colon = colon;
    }

    /**
     * Gets the original line
     *
     * @return the line
     */
    public String getLine() {
        return line;
    }

    /**
     * Gets the {@link StringUtilities#parseSplit(String...) parsed} {@link List string component list}
     *
     * @return the list
     */
    public List<String> getStringComponents() {
        return stringComponents;
    }

    /**
     * Checks if the line ends with a colon
     *
     * @return true if ends with a colon
     */
    public boolean lineHasColon() {
        return colon;
    }

    /**
     * Analyzes the line's contents, and returns an {@link AnalysisResult analysis}
     *
     * @return an {@link Optional optional} phrase
     */
    public Optional<AnalysisResult> analyze() {
        return analyze(null);
    }

    /**
     * <pre>
     * Analyzes the line's contents, and returns an {@link AnalysisResult analysis}
     * Only {@link Phrase phrases} that pass the {@link Predicate predicate filter} will be checked
     * </pre>
     *
     * @param predicate the phrase predicate
     * @return an {@link Optional optional} phrase
     */
    public Optional<AnalysisResult> analyze(Predicate<Phrase> predicate) {
        EScriptException exception = null;

        for (Phrase phrase : Phrases.instance().registry()) { // We don't use stream here because we're going to return the method from within the forEach loop
            if (predicate == null || predicate.test(phrase)) {
                try {
                    Optional<AnalysisResult> analysis = phrase.matches(this);

                    if (analysis.isPresent()) {
                        return analysis;
                    }
                } catch (EScriptException e) {
                    exception = e;
                }
            }
        }

        if (exception != null) {
            throw exception; // Throw the exception here if there is one
        }

        return Optional.empty();
    }
}
