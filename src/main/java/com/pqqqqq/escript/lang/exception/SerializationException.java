package com.pqqqqq.escript.lang.exception;

/**
 * Created by Kevin on 2016-09-09.
 * Thrown an error with a {@link com.pqqqqq.escript.lang.data.serializer.Serializer serializer} occurs
 */
public class SerializationException extends EScriptException {

    /**
     * Creates an empty serialization type exception
     */
    public SerializationException() {
        super();
    }

    /**
     * Creates a serialization exception with the given message
     *
     * @param msg the message
     */
    public SerializationException(String msg) {
        super(msg);
    }

    /**
     * Creates a serialization exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public SerializationException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates a serialization exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public SerializationException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
