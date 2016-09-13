package com.pqqqqq.escript.lang.exception;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * An exception from within EScript's internals
 */
public class EScriptException extends RuntimeException {

    /**
     * Creates an empty EScript exception
     */
    public EScriptException() {
        super();
    }

    /**
     * Creates an EScript exception with the given message
     *
     * @param msg the message
     */
    public EScriptException(String msg) {
        super(msg);
    }

    /**
     * Creates an EScript exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public EScriptException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * Creates an EScript exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public EScriptException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }
}
