package com.pqqqqq.escript.lang.phrase.action.sponge;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The broadcast phrase, which sends a message to the server
 * Examples:
 *      <code>
 *          broadcast "Hello!"
 *          broadcast to server "Hello!"
 *      </code>
 * </pre>
 */
public class BroadcastPhrase implements Phrase {
    private static final BroadcastPhrase INSTANCE = new BroadcastPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("send? broadcast to? server* $Message")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static BroadcastPhrase instance() {
        return INSTANCE;
    }

    private BroadcastPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        String message = ctx.getLiteral("Message").asString();
        Sponge.getServer().getBroadcastChannel().send(Text.of(message));
        return Result.success();
    }
}
