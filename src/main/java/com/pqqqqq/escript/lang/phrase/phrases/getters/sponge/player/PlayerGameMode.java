package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.data.mutable.LinkedMutableValue;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The player game mode phrase, which gets the player's game mode
 * Some examples:
 *      <code>player's gamemode
 *      player gamemode
 *      gamemode of player
 *      player's gamemode
 *      gamemode of "Joe"</code>
 * </pre>
 */
public class PlayerGameMode implements ValuePhrase {
    private static final PlayerGameMode INSTANCE = new PlayerGameMode();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Player gamemode"), // TODO allow "game mode"?
            Syntax.compile("the? gamemode of $Player")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerGameMode instance() {
        return INSTANCE;
    }

    private PlayerGameMode() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        return Result.valueSuccess(LinkedMutableValue.fromStore(player.gameMode(), player, Serializers.GAME_MODE));
    }
}
