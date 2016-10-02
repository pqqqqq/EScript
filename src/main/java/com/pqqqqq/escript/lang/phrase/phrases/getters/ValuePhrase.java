package com.pqqqqq.escript.lang.phrase.phrases.getters;

import com.pqqqqq.escript.lang.phrase.Phrase;

/**
 * Created by Kevin on 2016-09-24.
 * A tagging interface for phrases that have bounded values
 */
public interface ValuePhrase extends Phrase {

    /**
     * Gets the class corresponding to this value
     *
     * @return the class
     */
    default Class<?> getCorrespondingClass() {
        return null;
    }
}
