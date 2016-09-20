package com.pqqqqq.escript.lang.exception;

import com.pqqqqq.escript.lang.data.Keyword;
import com.pqqqqq.escript.lang.data.Literal;

/**
 * Created by Kevin on 2016-09-11.
 * <pre>
 * Thrown when a {@link Literal} is casted to an invalid type
 * This is only thrown for {@link Keyword keywords} ({@link Literal#asKeyword()})
 * </pre>
 */
public class InvalidTypeException extends EScriptException {

    /**
     * Creates an empty invalid type exception
     */
    public InvalidTypeException() {
        super();
    }

    /**
     * Creates an invalid type exception with the given message
     *
     * @param msg the message
     */
    public InvalidTypeException(String msg) {
        super(msg);
    }

    /**
     * Creates an invalid type exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public InvalidTypeException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates an invalid type exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public InvalidTypeException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
