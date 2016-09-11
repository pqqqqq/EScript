package com.pqqqqq.escript.lang.exception;

import com.pqqqqq.escript.lang.data.Literal;

/**
 * Created by Kevin on 2016-09-11.
 * Thrown when a {@link Literal} attempts to format data invalidly
 */
public class FormatException extends EScriptException {

    /**
     * Creates an empty format exception
     */
    public FormatException() {
        super();
    }

    /**
     * Creates a format exception with the given message
     *
     * @param msg the message
     */
    public FormatException(String msg) {
        super(msg);
    }

    /**
     * Creates a format exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public FormatException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates a format exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public FormatException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
