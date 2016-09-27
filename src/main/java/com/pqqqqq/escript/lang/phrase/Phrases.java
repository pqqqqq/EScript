package com.pqqqqq.escript.lang.phrase;

import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.phrase.action.PrintPhrase;
import com.pqqqqq.escript.lang.phrase.action.SetPhrase;
import com.pqqqqq.escript.lang.phrase.action.sponge.BroadcastPhrase;
import com.pqqqqq.escript.lang.phrase.action.sponge.player.PlayerCloseInventory;
import com.pqqqqq.escript.lang.phrase.action.sponge.player.PlayerKick;
import com.pqqqqq.escript.lang.phrase.action.sponge.player.PlayerMessage;
import com.pqqqqq.escript.lang.phrase.arithmetic.*;
import com.pqqqqq.escript.lang.phrase.block.*;
import com.pqqqqq.escript.lang.phrase.condition.*;
import com.pqqqqq.escript.lang.phrase.getters.ContainsPhrase;
import com.pqqqqq.escript.lang.phrase.getters.math.AbsolutePhrase;
import com.pqqqqq.escript.lang.phrase.getters.math.CeilPhrase;
import com.pqqqqq.escript.lang.phrase.getters.math.FloorPhrase;
import com.pqqqqq.escript.lang.phrase.getters.math.RoundPhrase;
import com.pqqqqq.escript.lang.phrase.getters.sponge.MOTDPhrase;
import com.pqqqqq.escript.lang.phrase.getters.sponge.player.*;
import com.pqqqqq.escript.lang.phrase.getters.string.LowercasePhrase;
import com.pqqqqq.escript.lang.phrase.getters.string.SubstringPhrase;
import com.pqqqqq.escript.lang.phrase.getters.string.UppercasePhrase;
import com.pqqqqq.escript.lang.phrase.trigger.MineTrigger;
import com.pqqqqq.escript.lang.phrase.trigger.PlaceTrigger;
import com.pqqqqq.escript.lang.phrase.trigger.ServerStartTrigger;
import com.pqqqqq.escript.lang.phrase.trigger.ServerStopTrigger;
import com.pqqqqq.escript.lang.registry.SortedRegistry;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * A {@link SortedRegistry sorted registry} of {@link Phrase phrases}
 */
public class Phrases extends SortedRegistry<Phrase> {
    // REGISTRY \\

    // TRIGGERS
    public static final Phrase MINE_TRIGGER = MineTrigger.instance();
    public static final Phrase PLACE_TRIGGER = PlaceTrigger.instance();
    public static final Phrase SERVER_START = ServerStartTrigger.instance();
    public static final Phrase SERVER_STOP = ServerStopTrigger.instance();

    // ----------------------------------------------------------------------- \\

    // ACTIONS
    public static final Phrase PRINT = PrintPhrase.instance();
    public static final Phrase SET = SetPhrase.instance();

    // Sponge
    public static final Phrase BROADCAST = BroadcastPhrase.instance();

    // Player
    public static final Phrase PLAYER_CLOSE_INVENTORY = PlayerCloseInventory.instance();
    public static final Phrase PLAYER_KICK = PlayerKick.instance();
    public static final Phrase PLAYER_MESSAGE = PlayerMessage.instance();

    // ----------------------------------------------------------------------- \\

    // GETTERS
    public static final Phrase CONTAINS = ContainsPhrase.instance();

    // Math
    public static final Phrase ABSOLUTE = AbsolutePhrase.instance();
    public static final Phrase CEIL = CeilPhrase.instance();
    public static final Phrase FLOOR = FloorPhrase.instance();
    public static final Phrase ROUND = RoundPhrase.instance();

    // String
    public static final Phrase LOWERCASE = LowercasePhrase.instance();
    public static final Phrase UPPERCASE = UppercasePhrase.instance();
    public static final Phrase SUBSTRING = SubstringPhrase.instance();

    // Sponge
    public static final Phrase MOTD = MOTDPhrase.instance();

    // Player
    public static final Phrase PLAYER_HEALTH = PlayerHealth.instance();
    public static final Phrase PLAYER_MAX_HEALTH = PlayerMaxHealth.instance();
    public static final Phrase PLAYER_NAME = PlayerName.instance();
    public static final Phrase PLAYER_EXHAUSTION = PlayerExhaustion.instance();
    public static final Phrase PLAYER_FOOD = PlayerFood.instance();
    public static final Phrase PLAYER_GAMEMODE = PlayerGameMode.instance();
    public static final Phrase PLAYER_SATURATION = PlayerSaturation.instance();

    // ----------------------------------------------------------------------- \\

    // BLOCKS
    public static final Phrase IF = IfPhrase.instance();
    public static final Phrase ELSE = ElsePhrase.instance();
    public static final Phrase WHILE = WhilePhrase.instance();
    public static final Phrase COUNT = CountPhrase.instance();
    public static final Phrase FOR_EACH = ForEachPhrase.instance();

    // ----------------------------------------------------------------------- \\

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

    // ----------------------------------------------------------------------- \\

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
     * <pre>
     * Analyzes the {@link Line line}, and returns an {@link AnalysisResult analysis}
     * Only {@link Phrase phrases} that pass the {@link Predicate predicate filter} will be checked
     * </pre>
     *
     * @param line the line
     * @param predicate the phrase predicate
     * @return an {@link Optional optional} phrase
     */
    public Optional<AnalysisResult> analyze(Line line, Predicate<Phrase> predicate) {
        return analyze(line.getLine(), predicate);
    }

    /**
     * Analyzes the line's contents, and returns an {@link AnalysisResult analysis}
     *
     * @param line the line
     * @return an {@link Optional optional} phrase
     */
    public Optional<AnalysisResult> analyze(String line) {
        return analyze(line, (phrase) -> true);
    }

    /**
     * <pre>
     * Analyzes the line's contents, and returns an {@link AnalysisResult analysis}
     * Only {@link Phrase phrases} that pass the {@link Predicate predicate filter} will be checked
     * </pre>
     *
     * @param line      the line
     * @param predicate the phrase predicate
     * @return an {@link Optional optional} phrase
     */
    public Optional<AnalysisResult> analyze(String line, Predicate<Phrase> predicate) {
        for (Phrase phrase : registry()) {
            if (predicate.test(phrase)) {
                Optional<AnalysisResult> analysis = phrase.matches(line);

                if (analysis.isPresent()) {
                    return analysis;
                }
            }
        }

        return Optional.empty();
    }
}
