package com.pqqqqq.escript.lang.phrase;

import com.pqqqqq.escript.lang.phrase.syntax.Component;

import java.util.Map;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * An analysis result, containing a phrase and the pattern used to match it.
 */
public class AnalysisResult {
    private final Phrase phrase;
    private final Map<Component.ArgumentComponent, String> strargs;

    /**
     * Creates a new analysis result
     *
     * @param phrase  the {@link Phrase phrase}
     * @param strargs the strargs {@link Map map}
     */
    public AnalysisResult(Phrase phrase, Map<Component.ArgumentComponent, String> strargs) {
        this.phrase = phrase;
        this.strargs = strargs;
    }

    /**
     * Gets the {@link Phrase phrase}
     *
     * @return the phrase
     */
    public Phrase getPhrase() {
        return phrase;
    }

    /**
     * Gets the strargs {@link Map map}
     *
     * @return the map
     */
    public Map<Component.ArgumentComponent, String> getStrargs() {
        return strargs;
    }
}
