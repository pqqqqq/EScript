package com.pqqqqq.escript.lang.util;

/**
 * Created by Kevin on 2016-10-01.
 * The timer class, which times how long a given {@link Runnable runnable} takes to run
 */
public class Timer {
    private final Runnable runnable;

    /**
     * Creates a new timer from the given {@link Runnable runnable}
     *
     * @param runnable the runnable
     * @return the new timer
     */
    public static Timer from(Runnable runnable) {
        return new Timer(runnable);
    }

    private Timer(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Runs the given runnable, timing the process
     *
     * @return the time taken
     */
    public long time() {
        long currentTime = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - currentTime;
    }
}
