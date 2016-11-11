package com.pqqqqq.escript.lang.phrase.phrases.action.sponge;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import org.spongepowered.api.event.Cancellable;

/**
 * <p>
 * <pre>
 * The cancel phrase, which cancels the event
 * Examples:
 *      <code>
 *          cancel
 *          cancel event false
 *          cancel the event
 *      </code>
 * </pre>
 */
public class CancelPhrase implements Phrase {
    private static final CancelPhrase INSTANCE = new CancelPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("cancel the? event? $Cancel?")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static CancelPhrase instance() {
        return INSTANCE;
    }

    private CancelPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        boolean cancel = ctx.getLiteral("Cancel", true).asBoolean();
        ((Cancellable) ctx.getScript().getProperties().getEvent()).setCancelled(cancel);
        return Result.success();
    }
}
