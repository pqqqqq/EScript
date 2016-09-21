package com.pqqqqq.escript.lang.phrase.action.sponge.player;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The message phrase, which messages the given player
 * Examples:
 *      <code>
 *          message "Hello!"
 *          send message "Hello!"
 *          message player "Hello!" to "Fred"
 *      </code>
 * </pre>
 */
public class PlayerMessage implements Phrase {
    private static final PlayerMessage INSTANCE = new PlayerMessage();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("send? message|msg $Message to? $Player*")

            /*Pattern.compile("^(send(\\s+?))?(message|msg)(\\s+?)(?<Message>\\S+?)$", Pattern.CASE_INSENSITIVE),
            Pattern.compile("^(send(\\s+?))?(message|msg)(\\s+?)(?<Message>\\S+?)(\\s+?)" +
                    StringUtils.from("to").getOutsideQuotePattern() + "(\\s+?)(?<Player>.+?)$", Pattern.CASE_INSENSITIVE)*/
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static PlayerMessage instance() {
        return INSTANCE;
    }

    private PlayerMessage() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Player player = ctx.getPlayer("Player");
        String message = ctx.getLiteral("Message").asString();

        player.sendMessage(Text.of(message));
        return Result.success();
    }
}
