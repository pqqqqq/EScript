package com.pqqqqq.escript.lang.file;

import com.pqqqqq.escript.lang.compile.ScriptCompiler;
import com.pqqqqq.escript.lang.util.ScriptPredicate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        // TODO Configurable compile
        Set<ScriptFile> files = new HashSet<>();
        List<String> compiled = new ArrayList<>();

        for (File file : root.listFiles()) {
              if (file.isDirectory()) {
                  files.addAll(search(file));
              } else if (file.getName().endsWith(".esc")) { // Compiled
                  files.add(ScriptCompiler.readCompiled(file));
                  compiled.add(file.getName().substring(0, file.getName().length() - 1));
              }
        }

        for (File file : root.listFiles()) {
            if (file.getName().endsWith(".es") && !compiled.contains(file.getName())) { // Uncompiled
                files.add(ScriptFile.from(file));
            }
        }

        return files;
    }
}
