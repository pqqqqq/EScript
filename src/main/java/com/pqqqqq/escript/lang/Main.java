package com.pqqqqq.escript.lang;

import com.pqqqqq.escript.lang.exception.handler.ExceptionHandler;
import com.pqqqqq.escript.lang.file.FileSearcher;
import com.pqqqqq.escript.lang.file.RawScript;
import com.pqqqqq.escript.lang.file.ScriptFile;
import com.pqqqqq.escript.lang.trigger.cause.Causes;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * The main language file
 */
public class Main {
    private static final Main INSTANCE = new Main();

    private Set<ScriptFile> scriptFiles = new HashSet<>();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static Main instance() {
        return INSTANCE;
    }

    private Main() {
    }

    /**
     * Initializes the script environment
     */
    public void init() {
        Causes.instance().reload(); // Reload causes
        ExceptionHandler.instance().attach(); // Exception handler init
        scriptFiles = FileSearcher.instance().search(); // Populate scripts
    }

    public static void main(String[] args) {
        /*Pattern test = Pattern.compile("(when|if)(\\s+?)(?<MineType>\\S+?)(\\s+?)(is(\\s*?))?mine(d)?:");
        Pattern test2 = Pattern.compile("on(\\s+?)(mine|mining)(\\s+?)(of(\\s*?))?(?<MineType>\\S+?):");

        Matcher matcher = test2.matcher("on mining stone:");
        matcher.find();
        System.out.println(matcher.group("MineType"));*/

        //System.out.println(Phrases.instance().registry().toString());

        //long millis = System.currentTimeMillis();
        //INSTANCE.init();
        //System.out.println("Millis: " + (System.currentTimeMillis() - millis));


        long time = System.currentTimeMillis();
        INSTANCE.init();
        System.out.println("Time: " + (System.currentTimeMillis() - time));

        for (ScriptFile scriptFile : INSTANCE.scriptFiles) {
            for (RawScript script : scriptFile) {
                script.toScript().execute();
            }
        }
    }

    /**
     * Gets a {@link Set set} of {@link ScriptFile script files} that are loaded
     *
     * @return the script files
     */
    public Set<ScriptFile> getScriptFiles() {
        return scriptFiles;
    }
}
