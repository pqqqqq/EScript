package com.pqqqqq.escript.lang.exception;

import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.Phrases;

/**
 * Created by Kevin on 2016-08-31.
 *
 * Thrown when a {@link Line line} is unable to find its corresponding {@link com.pqqqqq.escript.lang.phrase.Phrase phrase}
 * @see Phrases#analyze(String)
 */
public class UnknownPhraseException extends EScriptException {

    /**
     * Creates an empty unknown phrase exception
     */
    public UnknownPhraseException() {
        super();
    }

    /**
     * Creates an unknown phrase exception with the given message
     *
     * @param msg the message
     */
    public UnknownPhraseException(String msg) {
        super(msg);
    }

    /**
     * Creates an unknown phrase exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public UnknownPhraseException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates an unknown phrase exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public UnknownPhraseException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
