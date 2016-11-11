package com.pqqqqq.escript.lang.file;

import com.pqqqqq.escript.lang.exception.handler.ExceptionHandler;
import com.pqqqqq.escript.lang.exception.state.ESCompileTimeException;
import com.pqqqqq.escript.lang.line.Line;
import com.pqqqqq.escript.lang.util.string.StringLibrary;
import com.pqqqqq.escript.lang.util.string.StringUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <p>A class dedicated to file lexing.</p>
 * <p>Returns formatted contents of files.</p>
 */
public class FileLexer {
    private final File file;

    private List<String> lines = null;

    /**
     * Creates a new file lexer instance that represents the {@link File file}
     *
     * @param file the file
     * @return the new file lexer instance
     */
    public static FileLexer from(File file) {
        return new FileLexer(checkNotNull(file, "File cannot be null"));
    }

    private FileLexer(File file) {
        this.file = file;
    }

    /**
     * Gets the {@link File file} this file lexer represents
     *
     * @return the file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Lexes the represented file, and loads its contents into memory
     *
     * @return this instance, for chaining
     */
    public FileLexer lex() {
        checkState(getFile().exists(), "The file must exist.");
        this.lines = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(getFile());
            BufferedReader br = new BufferedReader(fileReader); // Create buffered instance

            String line; // Line variable, will change through while loop
            while ((line = br.readLine()) != null) {
                this.lines.add(line); // Add the line
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Returns the raw {@link List list} of lines for this file
     *
     * @return the list of lines
     */
    public List<String> raw() {
        return checkNotNull(this.lines, "Lines haven't been lexed yet.");
    }

    /**
     * Returns a separated {@link Set} of {@link RawScript raw scripts} for this file
     *
     * @return the set of raw scripts
     */
    public Set<RawScript> scripts() {
        Set<RawScript> scripts = new HashSet<>();
        RawScript.Builder scriptBuilder = null;

        int lineNumber = 0; // Have to keep track of line numbers
        boolean blockComment = false; // Have to keep track of block comment state

        Deque<Line.Builder> blockLineQueue = new ArrayDeque<>(); // Used for adding block lines
        Line.Builder lastLine = null; // Recording last line is important to start branching lines

        try {
            for (String lineContents : checkNotNull(lines, "Lines haven't been lexed yet.")) {
                // Remove any comments
                StringLibrary.Result commentResult = StringLibrary.from(lineContents).removeComments(blockComment);
                lineContents = commentResult.getResult(); // Set the line's contents to whatever after comments
                blockComment = commentResult.isSuccessful(); // Set new block comment state

                String trimmed = lineContents.trim();
                lineNumber++; // Incr line number

                int tabs = StringUtilities.from(lineContents).getLeadingTabulations(); // lineContents, not trimmed! trimmed has all leading whitespace removed.
                if (tabs == -1) { // If it's whitespace, just go to the next
                    continue;
                }

                Line.Builder line = Line.builder().line(trimmed).number(lineNumber).tabs(tabs);
                if (tabs == 0) { // If the line is a root line
                    lastLine = null; // Reset last line

                    if (scriptBuilder == null) { // Check if this is the first script
                        scriptBuilder = RawScript.builder().scriptFile(getFile()).line(line);
                    } else { // Otherwise, end the previous one
                        scripts.add(scriptBuilder.build()); // Build the previous one
                        scriptBuilder = RawScript.builder().scriptFile(getFile()).line(line);
                    }
                } else if (scriptBuilder != null) { // If the line isn't a root line, and a script is being built
                    // Check if the last line has a lower tab number than this one
                    if (lastLine != null && lastLine.getTabulations() < tabs) {
                        blockLineQueue.offer(lastLine); // Add to queue
                        lastLine.addLineBlock(line); // Add line block
                    } else {
                        Line.Builder blockLineCheck;
                        while ((blockLineCheck = blockLineQueue.peekLast()) != null) { // Condition is fine, since we'll be breaking
                            if (blockLineCheck.getTabulations() < tabs) { // Same check as above, but with queue
                                blockLineCheck.addLineBlock(line);
                                break;
                            } else {
                                blockLineQueue.pollLast(); // Otherwise, remove that
                            }
                        }
                    }

                    scriptBuilder.line(line);
                }

                lastLine = line; // Set last line to this line
            }

            if (scriptBuilder != null) {
                scripts.add(scriptBuilder.build()); // Build the last script, if necessary
            }
        } catch (Throwable e) {
            ExceptionHandler.instance().log(new ESCompileTimeException(e, "Error in file: \"%s\" at line %d: ", file.getName(), lineNumber));
            ExceptionHandler.instance().flush();
        }

        return scripts;
    }
}
