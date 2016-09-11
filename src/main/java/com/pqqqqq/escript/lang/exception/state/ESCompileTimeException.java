package com.pqqqqq.escript.lang.exception.state;

import com.pqqqqq.escript.lang.exception.EScriptException;

/**
 * Created by Kevin on 2016-08-31.
 *
 * Denotes an exception that occurred during compile time
 */
public class ESCompileTimeException extends EScriptException {

    /**
     * Creates an empty compile time exception
     */
    public ESCompileTimeException() {
        super();
    }

    /**
     * Creates a compile time exception with the given message
     *
     * @param msg the message
     */
    public ESCompileTimeException(String msg) {
        super(msg);
    }

    /**
     * Creates a compile time exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public ESCompileTimeException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates a compile time exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public ESCompileTimeException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
