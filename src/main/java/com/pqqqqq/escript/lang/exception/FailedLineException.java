package com.pqqqqq.escript.lang.exception;

import com.pqqqqq.escript.lang.line.Context;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * Thrown when a line fails to {@link Context#execute() execute}
 *
 * @see com.pqqqqq.escript.lang.phrase.Result.Failure
 */
public class FailedLineException extends EScriptException {

    /**
     * Creates an empty failed line exception
     */
    public FailedLineException() {
        super();
    }

    /**
     * Creates a failed line exception with the given message
     *
     * @param msg the message
     */
    public FailedLineException(String msg) {
        super(msg);
    }

    /**
     * Creates a failed line exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public FailedLineException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates a failed line exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public FailedLineException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
