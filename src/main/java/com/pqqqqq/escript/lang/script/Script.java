package com.pqqqqq.escript.lang.script;

import com.pqqqqq.escript.lang.data.variable.Environment;
import com.pqqqqq.escript.lang.exception.FailedLineException;
import com.pqqqqq.escript.lang.exception.handler.ExceptionHandler;
import com.pqqqqq.escript.lang.exception.state.ESRuntimeException;
import com.pqqqqq.escript.lang.file.RawScript;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.line.RunVessel;
import com.pqqqqq.escript.lang.phrase.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * A script instance, usually formed from a {@link com.pqqqqq.escript.lang.file.RawScript raw script}
 * A script is also a {@link com.pqqqqq.escript.lang.data.variable.Variable variable} {@link Environment environment}
 * </pre>
 */
public class Script extends Environment {
    private final RawScript rawScript;
    private final Properties properties;

    private final List<RunVessel> ranVessels = new ArrayList<>();

    /**
     * Creates a new script instance from the {@link RawScript raw script} it represents
     *
     * @param rawScript the raw script
     * @return the new instance
     */
    public static Script from(RawScript rawScript) {
        return from(rawScript, Properties.empty());
    }

    /**
     * Creates a new script instance from the {@link RawScript raw script} it represents and its {@link Properties properties}
     *
     * @param properties the properties
     * @param rawScript  the raw script
     * @return the new instance
     */
    public static Script from(RawScript rawScript, Properties properties) {
        return new Script(rawScript, properties);
    }

    private Script(RawScript rawScript, Properties properties) {
        this.rawScript = checkNotNull(rawScript, "Raw script cannot be null");
        this.properties = checkNotNull(properties, "Properties cannot be null");
    }

    /**
     * Gets the {@link RawScript raw script}
     *
     * @return the raw script
     */
    public RawScript getRawScript() {
        return rawScript;
    }

    /**
     * Gets this script's {@link Properties properties} instance
     *
     * @return the script's properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Gets a {@link List list} of {@link RunVessel run vessels} that have been executed
     *
     * @return the set of run vessels
     */
    public List<RunVessel> getRanVessels() {
        return ranVessels;
    }

    /**
     * Gets the {@link RunVessel run vessel} at the given tabulation
     *
     * @param tab the tab
     * @return the run vessel, or {@link Optional#empty()}
     */
    public Optional<RunVessel> getVesselAtTab(int tab) {
        return ranVessels.stream().filter(vessel -> vessel.getContext().getLine().getTabulations() == tab).reduce((first, second) -> second); // Get last by reducing
    }

    /**
     * Executes the script's runtime lines
     *
     * @return the {@link Result result}
     */
    public Result execute() {
        for (Line line : getRawScript().getLines()) {
            try {
                if (line.isRunAtRuntime() && line.getTabulations() == 1) { // We want 1 tab, since the block of a script always starts at one
                    RunVessel vessel = line.toContext(Script.this).toVessel();
                    vessel.run();

                    Result result = vessel.getResult();
                    if (result instanceof Result.Failure) {
                        Result.Failure fail = (Result.Failure) result;
                        throw new FailedLineException("Line failed because: \"%s\"", fail.getErrorMessage().orElse("UNKNOWN"));
                    }

                    ranVessels.add(vessel);
                }
            } catch (Throwable e) {
                ExceptionHandler.instance().log(new ESRuntimeException(e, "Error in file: \"%s\" at line %d: ", rawScript.getScriptFile().getName(), line.getLineNumber()));
                ExceptionHandler.instance().flush();
                return Result.failure();
            }
        }

        return Result.success();
    }

    /**
     * An enumeration of possible script run states
     */
    public enum State {
        /**
         * Compilation time, which occurs around when the server is starting
         */
        COMPILE_TIME,

        /**
         * Runtime, which occurs whenever the script is triggered to run
         */
        RUNTIME
    }
}
