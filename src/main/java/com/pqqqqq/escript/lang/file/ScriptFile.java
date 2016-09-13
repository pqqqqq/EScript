package com.pqqqqq.escript.lang.file;

import com.pqqqqq.escript.lang.compile.ScriptCompiler;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-08-31.
 *
 * A file that contains scripts.
 */
public class ScriptFile implements Iterable<RawScript>, Serializable {
    private final File file;
    private final Set<RawScript> scripts;

    /**
     * <p>Creates a new script file instance from the {@link File file's instance}</p>
     *
     * @param file the script file
     * @return the new script file instance
     */
    public static ScriptFile from(File file) {
        return from(checkNotNull(file, "File cannot be null"), FileLexer.from(file).lex().scripts());
    }

    /**
     * <p>Creates a new script file instance from the {@link File file's instance} and its containing {@link RawScript raw scripts}</p>
     * <p>If the set of raw scripts is already lexed, use {@link #from(File)}</p>
     *
     * @param file the script file
     * @param scripts the {@link Set} of scripts
     * @return the new script file instance
     */
    public static ScriptFile from(File file, Set<RawScript> scripts) {
        ScriptFile scriptFile = new ScriptFile(checkNotNull(file, "File cannot be null"), checkNotNull(scripts, "Scripts cannot be null"));
        ScriptCompiler.from(scriptFile).compile(); // Compile here
        return scriptFile;
    }

    private ScriptFile(File file, Set<RawScript> scripts) {
        this.file = file;
        this.scripts = scripts;
    }

    /**
     * Gets the {@link File script file}
     * @return the script file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Gets the {@link Set set} of {@link RawScript raw scripts}
     * @return the raw scripts
     */
    public Set<RawScript> getScripts() {
        return this.scripts;
    }

    @Override
    public Iterator<RawScript> iterator() {
        return scripts.iterator();
    }
}
