package com.pqqqqq.escript.lang.phrase.phrases.trigger;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.script.Script;
import com.pqqqqq.escript.lang.trigger.Trigger;
import com.pqqqqq.escript.lang.trigger.cause.Causes;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The server start trigger phrase, which fires when the server starts.
 * Some ways of doing this:
 *
 *     <code>on server start:
 *     when the server is started:
 *     if the server starts:</code>
 * </pre>
 */
public class ServerStartTrigger implements Phrase {
    private static final ServerStartTrigger INSTANCE = new ServerStartTrigger();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("when|if the? server|game is? started|starts|start|starting:"),
            Syntax.compile("on start|starting of? server|game:")
    };

    /**
     * Gets the main trigger instance
     *
     * @return the instance
     */
    public static ServerStartTrigger instance() {
        return INSTANCE;
    }

    private ServerStartTrigger() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Script.State getRunState() {
        return Script.State.COMPILE_TIME; // Run at compile time
    }

    @Override
    public Result execute(Context ctx) {
        Trigger.builder().script(ctx.getScript().getRawScript()).causes(Causes.SERVER_START).build();
        return Result.success();
    }
}
