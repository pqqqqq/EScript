package com.pqqqqq.escript.lang.phrase.arithmetic;

import com.pqqqqq.escript.lang.phrase.Phrase;

/**
 * Created by Kevin on 2016-09-14.
 * <p>
 * <pre>
 * An arithmetic phrase interface, useful for tagging and to define a ubiquitous priority (of -2) for every arithmetic operation
 * </pre>
 */
public interface ArithmeticPhrase extends Phrase {

    @Override
    default int getPriority() {
        return -2;
    }
}
