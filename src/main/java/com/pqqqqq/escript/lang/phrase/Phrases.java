package com.pqqqqq.escript.lang.phrase;

import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.action.MessagePhrase;
import com.pqqqqq.escript.lang.phrase.action.PrintPhrase;
import com.pqqqqq.escript.lang.phrase.block.ElsePhrase;
import com.pqqqqq.escript.lang.phrase.block.IfPhrase;
import com.pqqqqq.escript.lang.phrase.getters.PlayerHealth;
import com.pqqqqq.escript.lang.phrase.getters.PlayerName;
import com.pqqqqq.escript.lang.phrase.trigger.MineTrigger;
import com.pqqqqq.escript.lang.registry.Registry;

import java.util.Optional;

/**
 * Created by Kevin on 2016-08-31.
 *
 * A registry of phrases
 */
public class Phrases extends Registry<Phrase> {
    // REGISTRY \\

    // TRIGGERS
    public static final Phrase MINE_TRIGGER = MineTrigger.instance();

    // ACTIONS
    public static final Phrase PRINT = PrintPhrase.instance();
    public static final Phrase MESSAGE = MessagePhrase.instance();

    // GETTERS
    public static final Phrase PLAYER_HEALTH = PlayerHealth.instance();
    public static final Phrase PLAYER_NAME = PlayerName.instance();

    // BLOCKS
    public static final Phrase IF = IfPhrase.instance();
    public static final Phrase ELSE = ElsePhrase.instance();

    // END REGISTRY \\

    private static final Phrases INSTANCE = new Phrases();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static Phrases instance() {
        return INSTANCE;
    }

    private Phrases() {
        super(Phrase.class);
    }

    /**
     * Analyzes the {@link Line line}, and returns an {@link AnalysisResult analysis}
     *
     * @param line the line
     * @return an {@link Optional optional} phrase
     */
    public Optional<AnalysisResult> analyze(Line line) {
        return analyze(line.getLine());
    }

    /**
     * Analyzes the line's contents, and returns an {@link AnalysisResult analysis}
     *
     * @param line the line
     * @return an {@link Optional optional} phrase
     */
    public Optional<AnalysisResult> analyze(String line) {
        for (Phrase phrase : registry()) {
            Optional<AnalysisResult> analysis = phrase.matches(line);
            if (analysis.isPresent()) {
                return analysis;
            }
        }

        return Optional.empty();
    }
}
