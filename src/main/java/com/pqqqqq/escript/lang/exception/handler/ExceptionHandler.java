package com.pqqqqq.escript.lang.exception.handler;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin on 2015-06-02.
 * The error handler class that writes to a {@link PrintStream} about {@link Throwable} exceptions
 */
public class ExceptionHandler {
    private static final ExceptionHandler INSTANCE = new ExceptionHandler();
    private static final File ERROR_FILE = new File("scripts/scripts.log");
    private PrintStream writer;

    private ExceptionHandler() {
    }

    /**
     * Gets the {@link ExceptionHandler} instance
     *
     * @return the instance
     */
    public static ExceptionHandler instance() {
        return INSTANCE;
    }

    /**
     * Gets the error {@link File} that the {@link PrintStream} is writing to
     *
     * @return the error file
     */
    public static File getErrorFile() {
        return ERROR_FILE;
    }

    /**
     * Attaches this {@link ExceptionHandler}'s {@link PrintStream} to the error file, denoted by {@link #getErrorFile()}
     */
    public void attach() {
        try {
            ERROR_FILE.createNewFile(); // Create if not existent
            writer = new PrintStream(ERROR_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs a given message with the {@link #timestamp()} included
     *
     * @param message the message to log
     * @param args the arguments for string formatting
     */
    public void log(Object message, Object... args) {
        writer.println(timestamp() + ": " + String.format(message.toString(), args));
    }

    /**
     * Logs a given message with the {@link #timestamp()} included, and flushes the stream right away
     *
     * @param message the message to log
     * @param args the arguments for string formatting
     * @see #log(Object, Object...)
     */
    public void logFlush(Object message, Object... args) {
        log(message, args);
        flush();
    }

    /**
     * Logs the {@link Throwable} into the {@link PrintStream}
     *
     * @param e the error
     */
    public void log(Throwable e) {
        writer.print(timestamp() + ": ");
        e.printStackTrace(writer);
    }

    /**
     * Logs the {@link Throwable} into the {@link PrintStream} and flushes the stream right away
     *
     * @param e the error
     * @see #log(Throwable)
     */
    public void logFlush(Throwable e) {
        log(e);
        flush();
    }

    /**
     * Gets the timestamp, formatted by {@link SimpleDateFormat#getDateTimeInstance()}
     *
     * @return the timestamp
     */
    public String timestamp() {
        return "[" + SimpleDateFormat.getDateTimeInstance().format(new Date()) + "]";
    }

    /**
     * Flushes the {@link PrintStream}
     */
    public void flush() {
        writer.flush();
    }

    /**
     * Closes the {@link PrintStream}
     */
    public void close() {
        writer.close();
    }
}
