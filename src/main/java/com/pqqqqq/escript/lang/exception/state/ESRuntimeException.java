package com.pqqqqq.escript.lang.exception.state;

import com.pqqqqq.escript.lang.exception.EScriptException;

/**
 * Created by Kevin on 2016-08-31.
 *
 * Denotes an exception that occurred during run time
 */
public class ESRuntimeException extends EScriptException {

    /**
     * Creates an empty runtime exception
     */
    public ESRuntimeException() {
        super();
    }

    /**
     * Creates a runtime exception with the given message
     *
     * @param msg the message
     */
    public ESRuntimeException(String msg) {
        super(msg);
    }

    /**
     * Creates a runtime exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public ESRuntimeException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates a runtime exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public ESRuntimeException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
