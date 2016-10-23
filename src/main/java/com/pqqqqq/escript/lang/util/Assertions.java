package com.pqqqqq.escript.lang.util;

import com.pqqqqq.escript.lang.exception.AssertionException;
import com.pqqqqq.escript.lang.exception.EScriptException;

import java.util.function.Supplier;

/**
 * The assertions class. Assertions will perform checks, and throw errors if those checks are unsuccessful.
 */
public class Assertions {

    /**
     * Asserts a given {@link Supplier supplier condition}
     *
     * @param condition the condition
     */
    public static void assertThat(Supplier<Boolean> condition) {
        assertThat(condition, "Assertion failed.");
    }

    /**
     * Asserts a given {@link Supplier supplier condition}
     *
     * @param condition     the condition
     * @param assertMessage the assertion failure message
     * @param args          arguments in the message (String format)
     */
    public static void assertThat(Supplier<Boolean> condition, String assertMessage, Object... args) {
        if (!condition.get()) {
            throw new AssertionException(assertMessage, args);
        }
    }

    /**
     * Asserts a given {@link Supplier supplier condition}, and throws the given {@link EScriptException EScript exception} if the assertion fails.
     *
     * @param condition the condition
     * @param exception the EScript exception
     */
    public static void assertThat(Supplier<Boolean> condition, Supplier<? extends EScriptException> exception) {
        if (!condition.get()) {
            throw exception.get();
        }
    }
}
