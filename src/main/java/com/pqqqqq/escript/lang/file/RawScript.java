package com.pqqqqq.escript.lang.file;

import com.pqqqqq.escript.lang.exception.EScriptException;
import com.pqqqqq.escript.lang.exception.handler.ExceptionHandler;
import com.pqqqqq.escript.lang.exception.state.ESCompileTimeException;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.script.Properties;
import com.pqqqqq.escript.lang.script.Script;
import com.pqqqqq.escript.lang.trigger.Trigger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-08-31.
 *
 * A raw script, characterised only by strings of lines, and a containing script file.
 */
public class RawScript {
    private final File scriptFile;
    private final List<Line> lines;

    private Optional<Trigger> trigger; // TODO final?

    /**
     * <p>Creates a new raw script instance from its {@link File script file}, and its contents.</p>
     *
     * @param scriptFile the script file
     * @param buildableLines the file's contents (in build form)
     * @return the new raw file instance
     */
    public static RawScript from(File scriptFile, List<Line.Builder> buildableLines) {
        return new RawScript(checkNotNull(scriptFile, "File cannot be null"), checkNotNull(buildableLines, "Lines cannot be null"));
    }

    /**
     * Creates a new {@link Builder raw script builder} instance
     *
     * @return the new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    private RawScript(File scriptFile, List<Line.Builder> buildableLines) {
        this.scriptFile = scriptFile;

        // Populate lines with buildable lines
        List<Line> lines = new ArrayList<>(); // New lines
        buildableLines.stream().map(buildableLine -> buildableLine.script(this).build()).forEach(lines::add);
        this.lines = lines;

        compile(); // Compile
    }

    /**
     * Gets the raw script's {@link File script file}
     * @return the script file
     */
    public File getScriptFile() {
        return this.scriptFile;
    }

    /**
     * Gets the {@link List list} of {@link Line lines} this raw script contains
     *
     * @return the lines list
     */
    public List<Line> getLines() {
        return lines;
    }

    /**
     * Gets this script's {@link Trigger trigger}
     *
     * @return the trigger
     */
    public Optional<Trigger> getTrigger() {
        return trigger;
    }

    public void setTrigger(Optional<Trigger> trigger) { // No javadocs on purpose, this shouldn't be used
        this.trigger = trigger;
    }

    /**
     * Converts this raw script to a runtime {@link Script script}
     *
     * @return the new script
     */
    public Script toScript() {
        return Script.from(this);
    }

    /**
     * Converts this raw script to a runtime {@link Script script} with the givenh {@link Properties properties}
     *
     * @param properties the properties
     * @return the new script
     */
    public Script toScript(Properties properties) {
        return Script.from(this, properties);
    }

    protected void compile() { // TODO Return type??
        getLines().forEach(line -> {
            if (line.isRunAtCompileTime()) {
                line.toContext(null).toVessel().run();
            }
        });
    }

    /**
     * The {@link RawScript raw script} builder
     */
    public static class Builder {
        private File scriptFile = null;
        private List<Line.Builder> buildableLines = new ArrayList<>();

        private Builder() {
        }

        /**
         * Sets the {@link File script file}
         *
         * @param scriptFile the script file
         * @return this builder, for chaining
         */
        public Builder scriptFile(File scriptFile) {
            this.scriptFile = scriptFile;
            return this;
        }

        /**
         * Adds the given line to the builder
         *
         * @param line the line
         * @param number the line number
         * @return this builder, for chaining
         */
        public Builder line(String line, int number) {
            return line(Line.builder().line(line).number(number));
        }

        /**
         * Adds the given {@link Line.Builder buildable line} to the builder
         *
         * @param line the line builder
         * @return this builder, for chaining
         */
        public Builder line(Line.Builder line) {
            this.buildableLines.add(line);
            return this;
        }

        /**
         * Builds the raw script
         *
         * @return the new raw script instance
         */
        public RawScript build() {
            return from(scriptFile, buildableLines);
        }
    }
}
