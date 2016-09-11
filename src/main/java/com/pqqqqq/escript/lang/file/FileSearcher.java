package com.pqqqqq.escript.lang.file;

import com.pqqqqq.escript.lang.util.ScriptPredicate;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kevin on 2016-08-31.
 *
 * <p>Finds files that are eligible as a script and sends them to the {@link FileLexer}</p>
 */
public class FileSearcher {
    private static final FileSearcher INSTANCE = new FileSearcher();
    private static final File DIR = new File("scripts/");

    /**
     * Gets the main file searcher instance
     * @return the instance
     */
    public static FileSearcher instance() {
        return INSTANCE;
    }

    private FileSearcher() {
    }

    /**
     * <p>Gets the {@link File file} directory for scripts.</p>
     * <p>The script directory is /scripts in the root directory.</p>
     *
     * @return the main directory
     */
    public static File getDir() {
        return DIR;
    }

    /**
     * Searches for {@link ScriptFile script files} in the main scripts directory.
     *
     * @return the {@link Set set} of files
     * @see #getDir()
     */
    public Set<ScriptFile> search() {
        getDir().mkdir(); // Make scripts directory if it doesn't exist
        return search(getDir());
    }

    /**
     * Searches for {@link ScriptFile script files} in the given {@link File root}
     *
     * @param root the root file
     * @return the {@link Set set} of files
     */
    public Set<ScriptFile> search(File root) {
        Set<ScriptFile> files = new HashSet<>();

        for (File file : root.listFiles()) {
              if (file.isDirectory()) {
                  files.addAll(search(file));
              } else if (ScriptPredicate.instance().test(file)) {
                  files.add(ScriptFile.from(file));
              }
        }

        return files;
    }
}
