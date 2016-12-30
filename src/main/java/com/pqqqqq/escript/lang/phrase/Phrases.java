package com.pqqqqq.escript.lang.phrase;

import com.pqqqqq.escript.lang.phrase.phrases.action.CheckPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.action.PrintPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.action.SetPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.action.sponge.BroadcastPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.action.sponge.CancelPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.action.sponge.player.PlayerCloseInventory;
import com.pqqqqq.escript.lang.phrase.phrases.action.sponge.player.PlayerKick;
import com.pqqqqq.escript.lang.phrase.phrases.action.sponge.player.PlayerMessage;
import com.pqqqqq.escript.lang.phrase.phrases.arithmetic.*;
import com.pqqqqq.escript.lang.phrase.phrases.block.*;
import com.pqqqqq.escript.lang.phrase.phrases.condition.*;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ContainsPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.math.AbsolutePhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.math.CeilPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.math.FloorPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.math.RoundPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.math.statistics.SamplePhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.math.statistics.TrialPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.MOTDPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.block.BlockType;
import com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player.*;
import com.pqqqqq.escript.lang.phrase.phrases.getters.string.LowercasePhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.string.SubstringPhrase;
import com.pqqqqq.escript.lang.phrase.phrases.getters.string.UppercasePhrase;
import com.pqqqqq.escript.lang.phrase.phrases.trigger.*;
import com.pqqqqq.escript.lang.phrase.phrases.trigger.command.CommandTrigger;
import com.pqqqqq.escript.lang.registry.SortedRegistry;

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
    public static final Phrase COMMAND_TRIGGER = CommandTrigger.instance();
    public static final Phrase INTERACT_BLOCK_TRIGGER = InteractBlockTrigger.instance();
    public static final Phrase INTERACT_ENTITY_TRIGGER = InteractEntityTrigger.instance();

    public static final Phrase SERVER_START = ServerStartTrigger.instance();
    public static final Phrase SERVER_STOP = ServerStopTrigger.instance();

    // ----------------------------------------------------------------------- \\

    // ACTIONS
    public static final Phrase PRINT = PrintPhrase.instance();
    public static final Phrase SET = SetPhrase.instance();
    public static final Phrase CHECK = CheckPhrase.instance();

    // Sponge
    public static final Phrase BROADCAST = BroadcastPhrase.instance();
    public static final Phrase CANCEL = CancelPhrase.instance();

    // Player
    public static final Phrase PLAYER_CLOSE_INVENTORY = PlayerCloseInventory.instance();
    public static final Phrase PLAYER_KICK = PlayerKick.instance();
    public static final Phrase PLAYER_MESSAGE = PlayerMessage.instance();

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

    // ----------------------------------------------------------------------- \\

    // GETTERS

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
    public static final Phrase PLAYER_LOCATION = PlayerLocation.instance();
    public static final Phrase PLAYER_PERMISSION = PlayerPermission.instance();

    public static final Phrase PLAYER_ITEM_HAND = PlayerItemHand.instance();
    public static final Phrase PLAYER_HELMET = PlayerHelmet.instance();
    public static final Phrase PLAYER_CHEST_PLATE = PlayerChestplate.instance();
    public static final Phrase PLAYER_LEGGINGS = PlayerLeggings.instance();
    public static final Phrase PLAYER_BOOTS = PlayerBoots.instance();

    // Block
    public static final Phrase BLOCK_TYPE = BlockType.instance();

    public static final Phrase CONTAINS = ContainsPhrase.instance();

    // Math
    public static final Phrase ABSOLUTE = AbsolutePhrase.instance();
    public static final Phrase CEIL = CeilPhrase.instance();
    public static final Phrase FLOOR = FloorPhrase.instance();
    public static final Phrase ROUND = RoundPhrase.instance();

    // Statistics
    public static final Phrase SAMPLE = SamplePhrase.instance();
    public static final Phrase TRIAL = TrialPhrase.instance();

    // String
    public static final Phrase LOWERCASE = LowercasePhrase.instance();
    public static final Phrase UPPERCASE = UppercasePhrase.instance();
    public static final Phrase SUBSTRING = SubstringPhrase.instance();

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
}
