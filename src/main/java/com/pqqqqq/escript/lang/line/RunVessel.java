package com.pqqqqq.escript.lang.line;

import com.pqqqqq.escript.lang.exception.VesselStateException;
import com.pqqqqq.escript.lang.phrase.Result;

/**
 * Created by Kevin on 2016-09-10.
 * <p>
 * <pre>
 * A {@link Runnable runnable} vessel for a {@link Context context}
 * The vessel can only be run once, and will keep the {@link Result} on hand
 * </pre>
 */
public class RunVessel implements Runnable {
    private final Context ctx;
    private Result result = null;

    protected RunVessel(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Gets the represented {@link Context context}
     *
     * @return the context
     */
    public Context getContext() {
        return ctx;
    }

    /**
     * Gets the {@link Result result} of the run
     *
     * @return the result, or null if not run yet
     */
    public Result getResult() {
        return result;
    }

    @Override
    public void run() {
        if (result != null) {
            throw new VesselStateException("This vessel has already departed.");
        }

        result = ctx.getLine().getPhrase().execute(ctx);
    }
}
