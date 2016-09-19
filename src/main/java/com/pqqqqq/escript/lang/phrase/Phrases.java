package com.pqqqqq.escript.lang.phrase;

import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.action.MessagePhrase;
import com.pqqqqq.escript.lang.phrase.action.PrintPhrase;
import com.pqqqqq.escript.lang.phrase.action.VariablePhrase;
import com.pqqqqq.escript.lang.phrase.arithmetic.*;
import com.pqqqqq.escript.lang.phrase.block.*;
import com.pqqqqq.escript.lang.phrase.condition.*;
import com.pqqqqq.escript.lang.phrase.getters.ContainsPhrase;
import com.pqqqqq.escript.lang.phrase.getters.PlayerHealth;
import com.pqqqqq.escript.lang.phrase.getters.PlayerName;
import com.pqqqqq.escript.lang.phrase.trigger.MineTrigger;
import com.pqqqqq.escript.lang.phrase.trigger.PlaceTrigger;
import com.pqqqqq.escript.lang.phrase.trigger.ServerStartTrigger;
import com.pqqqqq.escript.lang.phrase.trigger.ServerStopTrigger;
import com.pqqqqq.escript.lang.registry.SortedRegistry;

import java.util.Optional;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * A registry of phrases
 */
public class Phrases extends SortedRegistry<Phrase> {
    // REGISTRY \\

    // TRIGGERS
    public static final Phrase MINE_TRIGGER = MineTrigger.instance();
    public static final Phrase PLACE_TRIGGER = PlaceTrigger.instance();
    public static final Phrase SERVER_START = ServerStartTrigger.instance();
    public static final Phrase SERVER_STOP = ServerStopTrigger.instance();

    // ACTIONS
    public static final Phrase PRINT = PrintPhrase.instance();
    public static final Phrase MESSAGE = MessagePhrase.instance();
    public static final Phrase VARIABLE = VariablePhrase.instance();

    // GETTERS
    public static final Phrase PLAYER_HEALTH = PlayerHealth.instance();
    public static final Phrase PLAYER_NAME = PlayerName.instance();

    public static final Phrase CONTAINS = ContainsPhrase.instance();

    // BLOCKS
    public static final Phrase IF = IfPhrase.instance();
    public static final Phrase ELSE = ElsePhrase.instance();
    public static final Phrase WHILE = WhilePhrase.instance();
    public static final Phrase COUNT = CountPhrase.instance();
    public static final Phrase FOR_EACH = ForEachPhrase.instance();

    // CONDITIONS
    public static final Phrase OR = OrPhrase.instance();
    public static final Phrase AND = AddPhrase.instance();
    public static final Phrase DISSIMILAR = DissimilarPhrase.instance();
    public static final Phrase SIMILAR = SimilarPhrase.instance();
    public static final Phrase EQUALS = EqualsPhrase.instance();
    public static final Phrase NOT_EQUALS = NotEqualsPhrase.instance();
    public static final Phrase GREATER_THAN = GreaterThan.instance();
    public static final Phrase LESS_THAN = LessThan.instance();
    public static final Phrase GREATER_THAN_OET = GreaterThanOET.instance();
    public static final Phrase LESS_THAN_OET = LessThanOET.instance();

    // ARITHMETIC
    public static final Phrase POWER = PowerPhrase.instance();
    public static final Phrase ROOT = RootPhrase.instance();
    public static final Phrase MULTIPLY = MultiplyPhrase.instance();
    public static final Phrase DIVIDE = DividePhrase.instance();
    public static final Phrase ADD = AddPhrase.instance();
    public static final Phrase SUBTRACT = SubtractPhrase.instance();
    public static final Phrase MODULUS = ModulusPhrase.instance();

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
