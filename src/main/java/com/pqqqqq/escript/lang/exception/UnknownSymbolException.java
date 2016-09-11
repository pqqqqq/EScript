package com.pqqqqq.escript.lang.exception;

import com.pqqqqq.escript.lang.data.Sequencer;

/**
 * Created by Kevin on 2016-09-09.
 * Thrown when the {@link Sequencer sequencer} fails to recognize the given symbol
 * @see Sequencer#sequence(String)
 */
public class UnknownSymbolException extends EScriptException {

    /**
     * Creates an empty unknown symbol exception
     */
    public UnknownSymbolException() {
        super();
    }

    /**
     * Creates an unknown symbol exception with the given message
     *
     * @param msg the message
     */
    public UnknownSymbolException(String msg) {
        super(msg);
    }

    /**
     * Creates an unknown symbol exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public UnknownSymbolException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates an unknown symbol exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public UnknownSymbolException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
