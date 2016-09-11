package com.pqqqqq.escript.lang.util;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Predicate;

/**
 * Created by Kevin on 2016-08-31.
 *
 * The {@link Predicate} and {@link FileFilter} for a script {@link File}
 */
public class ScriptPredicate implements Predicate<File>, FileFilter {
    private static final ScriptPredicate INSTANCE = new ScriptPredicate();

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static ScriptPredicate instance() {
        return INSTANCE;
    }

    private ScriptPredicate() {
    }

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(".es") || file.getName().endsWith(".esc");
    }

    @Override
    public boolean test(File file) {
        return accept(file);
    }
}
