package com.pqqqqq.escript.lang.exception;

/**
 * Created by Kevin on 2016-09-09.
 * Thrown when a Sponge registry type is unable to be found
 */
public class UnknownRegistryTypeException extends EScriptException {

    /**
     * Creates an empty unknown registry type exception
     */
    public UnknownRegistryTypeException() {
        super();
    }

    /**
     * Creates an unknown registry type exception with the given message
     *
     * @param msg the message
     */
    public UnknownRegistryTypeException(String msg) {
        super(msg);
    }

    /**
     * Creates an unknown registry type exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public UnknownRegistryTypeException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates an unknown registry type exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public UnknownRegistryTypeException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
