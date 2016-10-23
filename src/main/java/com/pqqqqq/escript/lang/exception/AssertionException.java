package com.pqqqqq.escript.lang.exception;

/**
 * Created by Kevin on 2016-09-09.
 * Thrown when an assertion fails
 */
public class AssertionException extends EScriptException {

    /**
     * Creates an empty assertion exception
     */
    public AssertionException() {
        super();
    }

    /**
     * Creates an assertion exception with the given message
     *
     * @param msg the message
     */
    public AssertionException(String msg) {
        super(msg);
    }

    /**
     * Creates an assertion exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public AssertionException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates an assertion exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public AssertionException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
