package com.pqqqqq.escript.lang.line;

import com.pqqqqq.escript.lang.data.Sequencer;
import com.pqqqqq.escript.lang.data.container.DatumContainer;
import com.pqqqqq.escript.lang.exception.UnknownPhraseException;
import com.pqqqqq.escript.lang.file.FileLexer;
import com.pqqqqq.escript.lang.file.RawScript;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.analysis.Analysis;
import com.pqqqqq.escript.lang.phrase.analysis.AnalysisResult;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Component;
import com.pqqqqq.escript.lang.script.Script;
import com.pqqqqq.escript.lang.util.string.StringUtilities;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * A script line, characterised by its raw script, line contents, its line number, and its {@link Phrase}
 */
public class Line {
    private final RawScript rawScript;
    private final String line;
    private final int lineNumber;
    private final int tabulations;

    private final Set<Line> lineBlock;

    private final Phrase phrase;
    private final Map<Component.ArgumentComponent, String> strargs;
    private final Map<Component.ArgumentComponent, DatumContainer> containers;

    /**
     * Creates a new {@link Builder builder} instance
     *
     * @return the builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    private Line(RawScript rawScript, String line, int lineNumber, int tabulations, AnalysisResult analysis, Set<Line> lineBlock) {
        this(rawScript, line, lineNumber, tabulations, analysis.getPhrase(), analysis.getStrargs(), analysis.getContainers(), lineBlock);
    }

    private Line(RawScript rawScript, String line, int lineNumber, int tabulations, Phrase phrase, Map<Component.ArgumentComponent, String> strargs, Map<Component.ArgumentComponent, DatumContainer> containers, Set<Line> lineBlock) {
        this.rawScript = rawScript;
        this.line = StringUtilities.from(line).trim(); // Trim line
        this.lineNumber = lineNumber;
        this.tabulations = tabulations;
        this.phrase = phrase;
        this.strargs = strargs;
        this.containers = containers;
        this.lineBlock = lineBlock;
    }

    /**
     * Gets the {@link RawScript raw script} for this line
     *
     * @return the script
     */
    public RawScript getRawScript() {
        return rawScript;
    }

    /**
     * Gets the contents of the line
     *
     * @return the line's contents
     */
    public String getLine() {
        return line;
    }

    /**
     * Gets the line number
     *
     * @return the line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets the number of tabulations for this line
     *
     * @return the number of tabs
     */
    public int getTabulations() {
        return tabulations;
    }

    /**
     * <pre>
     * Gets the block of lines that extend from this line.
     * This will be empty unless this line is a root for the next tabulation.
     * </pre>
     *
     * @return the line block {@link Set set}
     */
    public Set<Line> getLineBlock() {
        return lineBlock;
    }

    /**
     * Gets the {@link Phrase}
     *
     * @return the phrase
     */
    public Phrase getPhrase() {
        return phrase;
    }

    /**
     * Checks if this line should be run at {@link Script.State#COMPILE_TIME compile time}
     *
     * @return true if run at compile time
     */
    public boolean isRunAtCompileTime() {
        return getPhrase().getRunState() == Script.State.COMPILE_TIME;
    }

    /**
     * Checks if this line should be run at {@link Script.State#RUNTIME runtime}
     *
     * @return true if run at runtime
     */
    public boolean isRunAtRuntime() {
        return getPhrase().getRunState() == Script.State.RUNTIME;
    }

    /**
     * Gets the string argument (before {@link Sequencer#sequence(String) sequenced}) of the given group
     *
     * @param group the group name
     * @return the strarg
     */
    public Optional<String> getStrarg(String group) {
        for (Map.Entry<Component.ArgumentComponent, String> entry : this.strargs.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(group)) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    /**
     * Gets the {@link DatumContainer} (after {@link Sequencer#sequence(String) sequenced}) of the given group
     *
     * @param group the group name
     * @return the datum container
     */
    public Optional<DatumContainer> getContainer(String group) {
        for (Map.Entry<Component.ArgumentComponent, DatumContainer> entry : this.containers.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(group)) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    protected Map<Component.ArgumentComponent, DatumContainer> getContainers() { // Context needs this
        return containers;
    }

    /**
     * Converts this line into a {@link Context context} with the given {@link Script script instance}
     *
     * @param script the script
     * @return the new context
     */
    public Context toContext(Script script) {
        return new Context(script, this);
    }

    /**
     * The {@link Line line} builder class
     */
    public static class Builder {
        private RawScript rawScript;
        private String line = null;
        private Integer lineNumber = null;
        private Integer tabulations = null;

        private Optional<AnalysisResult> analysisResult = Optional.empty();

        private Set<Line.Builder> lineBlock = new HashSet<>();

        private Builder() {
        }

        /**
         * Sets the {@link RawScript raw script}
         *
         * @param rawScript the raw script
         * @return this builder, for chaining
         */
        public Builder script(RawScript rawScript) {
            this.rawScript = rawScript;
            return this;
        }

        /**
         * Sets the line's contents
         *
         * @param line the line
         * @return this builder, for chaining
         */
        public Builder line(String line) {
            this.line = line;
            return this;
        }

        /**
         * Sets the line's line number
         *
         * @param lineNumber the line number
         * @return this builder, for chaining
         */
        public Builder number(int lineNumber) {
            this.lineNumber = lineNumber;
            return this;
        }

        /**
         * Sets the line's tab number
         *
         * @param tabulations the tab number
         * @return this builder, for chaining
         */
        public Builder tabs(int tabulations) {
            this.tabulations = tabulations;
            return this;
        }

        /**
         * <pre>
         * Gets the number of tabulations for this builder
         * This getter is required for {@link FileLexer#scripts()}, which is why no other fields of this builder have getters
         * </pre>
         *
         * @return the tab count
         */
        public Integer getTabulations() {
            return tabulations;
        }

        /**
         * Sets the line's {@link AnalysisResult analysis result}
         *
         * @param analysisResult the analysis result
         * @return this builder, for chaining
         */
        public Builder analysis(AnalysisResult analysisResult) {
            this.analysisResult = Optional.of(analysisResult);
            return this;
        }

        /**
         * Adds a line builder to this line's block
         *
         * @param line the line builder to add
         * @return this builder, for chaining
         */
        public Builder addLineBlock(Line.Builder line) {
            this.lineBlock.add(line);
            return this;
        }

        /**
         * Builds the new {@link Line} instance
         *
         * @return the new line instance
         */
        public Line build() {
            AnalysisResult analysisResult = this.analysisResult.orElseGet(() -> Analysis.from(line).analyze().orElseThrow(() -> new UnknownPhraseException("Unknown phrase for line: \"%s\"", line)));

            // Convert each line builder into a line
            Set<Line> lineBlock = new HashSet<>();
            this.lineBlock.stream().map(buildableLine -> buildableLine.script(this.rawScript).build()).forEach(lineBlock::add);

            return new Line(checkNotNull(this.rawScript, "You must specify a script."), checkNotNull(this.line, "You must specify a line string."), checkNotNull(this.lineNumber, "You must specify a line number."),
                    checkNotNull(this.tabulations, "You must specify a tabulation number."), analysisResult, lineBlock);
        }
    }
}
