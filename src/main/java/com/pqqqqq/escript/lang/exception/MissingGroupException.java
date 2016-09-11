package com.pqqqqq.escript.lang.exception;

import com.pqqqqq.escript.lang.line.Context;

/**
 * Created by Kevin on 2016-09-02.
 *
 * Thrown when a {@link Context context} cannot find a {@link com.pqqqqq.escript.lang.data.Literal literal} for the given group
 * @see Context#getLiteral(String)
 */
public class MissingGroupException extends EScriptException {

    /**
     * Creates an empty missing group exception
     */
    public MissingGroupException() {
        super();
    }

    /**
     * Creates a missing group exception with the given message
     *
     * @param msg the message
     */
    public MissingGroupException(String msg) {
        super(msg);
    }

    /**
     * Creates a missing group exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public MissingGroupException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates a missing group exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public MissingGroupException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
