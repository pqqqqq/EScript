package com.pqqqqq.escript.lang.exception;

import com.pqqqqq.escript.lang.phrase.analysis.Analysis;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-09.
 * Throws an error when a {@link Syntax#matches(Analysis) match} attempt goes South.
 */
public class SyntaxMatchingException extends EScriptException {

    /**
     * Creates an empty syntax matching exception
     */
    public SyntaxMatchingException() {
        super();
    }

    /**
     * Creates a syntax matching exception with the given message
     *
     * @param msg the message
     */
    public SyntaxMatchingException(String msg) {
        super(msg);
    }

    /**
     * Creates a syntax matching exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public SyntaxMatchingException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates a syntax matching exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public SyntaxMatchingException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
