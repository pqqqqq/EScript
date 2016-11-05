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
 * The server stop trigger phrase, which fires when the server stops.
 * Some ways of doing this:
 *
 *     <code>on server stop:
 *     when the server is stopped:
 *     if the server stops:</code>
 * </pre>
 */
public class ServerStopTrigger implements Phrase {
    private static final ServerStopTrigger INSTANCE = new ServerStopTrigger();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("when|if the? server|game is? stopped|stops|stop|stopping|exit|extis|exiting:"),
            Syntax.compile("on stop|stopping|exit|exiting of? server|game:")
    };

    /**
     * Gets the main trigger instance
     *
     * @return the instance
     */
    public static ServerStopTrigger instance() {
        return INSTANCE;
    }

    private ServerStopTrigger() {
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
        Trigger.from(ctx.getScript().getRawScript(), Causes.SERVER_START);
        return Result.success();
    }
}
