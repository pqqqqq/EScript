package com.pqqqqq.escript.lang.compile;

import com.pqqqqq.escript.lang.exception.FormatException;
import com.pqqqqq.escript.lang.file.ScriptFile;

import java.io.*;

/**
 * Created by Kevin on 2016-09-12.
 *
 * <pre>
 * The main compiler class, which compiles a script into a .esc file, or loads a compiled class
 * </pre>
 */
public class ScriptCompiler {
    private final ScriptFile scriptFile;

    /**
     * Creates a new compiler for the {@link ScriptFile script file}
     *
     * @param scriptFile the script file
     * @return the new compiler instance
     */
    public static ScriptCompiler from(ScriptFile scriptFile) {
        return new ScriptCompiler(scriptFile);
    }

    /**
     * Reads a compiled {@link ScriptFile script file} from the given {@link File file}
     *
     * @param file the file
     * @return the script file
     */
    public static ScriptFile readCompiled(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file); // Create FIS
            ObjectInputStream in = new ObjectInputStream(fileInputStream); // Create OIS

            Object obj = in.readObject();
            if (!(obj instanceof ScriptFile)) {
                throw new FormatException("File \"%s\" was improperly formatted during compilation.", file.getAbsoluteFile().getPath());
            }

            in.close();
            return (ScriptFile) obj;
        } catch (IOException | ClassNotFoundException e) {
            throw new FormatException(e, "File \"%s\" was improperly formatted during compilation.", file.getAbsoluteFile().getPath());
        }
    }

    private ScriptCompiler(ScriptFile scriptFile) {
        this.scriptFile = scriptFile;
    }

    /**
     * Gets the {@link ScriptFile script file}
     *
     * @return the script file
     */
    public ScriptFile getScriptFile() {
        return scriptFile;
    }

    public File compile() {
        File sourceFile = scriptFile.getFile();
        int index = sourceFile.getName().lastIndexOf(".");
        File file = new File(sourceFile.getParentFile(), sourceFile.getName().substring(0, index) + ".esc"); // New file

        try {
            file.createNewFile(); // Create it
            FileOutputStream fileOutputStream = new FileOutputStream(file); // Create FOS
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream); // Create OOS

            out.writeObject(getScriptFile()); // Write the file

            out.flush(); // Flush
            out.close(); // Close
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
