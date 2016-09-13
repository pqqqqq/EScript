package com.pqqqqq.escript.lang.exception;

/**
 * Created by Kevin on 2016-09-09.
 * Thrown when a block if or else if doesn't exist yet
 */
public class MissingIfChainException extends EScriptException {

    /**
     * Creates an empty missing if chain exception
     */
    public MissingIfChainException() {
        super();
    }

    /**
     * Creates an missing if chain exception with the given message
     *
     * @param msg the message
     */
    public MissingIfChainException(String msg) {
        super(msg);
    }

    /**
     * Creates an missing if chain exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public MissingIfChainException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates an missing if chain exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public MissingIfChainException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
