package com.pqqqqq.escript.lang.phrase.phrases.getters.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.phrase.phrases.getters.ValuePhrase;
import org.spongepowered.api.entity.living.player.Player;

/**
 * <p>
 * <pre>
 * The player permission phrase, which checks if the player has the given permission
 * Some examples:
 *      <code>player has permission "perm.permission"
 *      "Joe" has permission "perm.permission"</code>
 * </pre>
 */
public class PlayerPermission implements ValuePhrase {
    private static final PlayerPermission INSTANCE = new PlayerPermission();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("the? player|person has? the? perm|permission $Permission"),
            Syntax.compile("$Player has? perm|permission $Permission")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerPermission instance() {
        return INSTANCE;
    }

    private PlayerPermission() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        String permission = ctx.getLiteral("Permission").asString();

        // TODO can we set the permission?
        return Result.success(player.hasPermission(permission));
    }
}
