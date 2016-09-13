package com.pqqqqq.escript.lang.exception;

import com.pqqqqq.escript.lang.line.RunVessel;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * Thrown when a {@link RunVessel} is attempted to be run twice
 *
 * @see RunVessel#run()
 */
public class VesselStateException extends EScriptException {

    /**
     * Creates an empty vessel state exception
     */
    public VesselStateException() {
        super();
    }

    /**
     * Creates a vessel state exception with the given message
     *
     * @param msg the message
     */
    public VesselStateException(String msg) {
        super(msg);
    }

    /**
     * Creates a vessel state exception using {@link String#format(String, Object...)}
     *
     * @param message the message
     * @param args    the formatting arguments
     */
    public VesselStateException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Creates a vessel state exception using {@link String#format(String, Object...)}
     *
     * @param cause   the cause
     * @param message the message
     * @param args    the formatting arguments
     */
    public VesselStateException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
