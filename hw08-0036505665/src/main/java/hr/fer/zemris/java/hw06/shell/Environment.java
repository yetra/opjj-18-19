package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * This interface is an abstraction which will be passed to each defined
 * {@link ShellCommand}. Commands can communicate with the user (read the user input
 * and write a response) only through this interface.
 *
 * @author Bruna Dujmović
 *
 */
public interface Environment {

    /**
     * Reads and returns the last line that the user wrote.
     *
     * @return the last line that the user wrote
     * @throws ShellIOException if reading the user input fails
     */
    String readLine() throws ShellIOException;

    /**
     * Writes a given text.
     *
     * @param text the text to write
     * @throws ShellIOException if writing fails
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes a given line of text.
     *
     * @param text the line of text to write
     * @throws ShellIOException if writing fails
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Returns an unmodifiable map of supported shell commands.
     *
     * @return an unmodifiable map of supported shell commands
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Returns the symbol which denotes the start of each new line in a multi-line
     * command.
     *
     * @return the current multi-line symbol
     */
    Character getMultilineSymbol();

    /**
     * Changes the current multi-lines symbol to the given character.
     *
     * @param symbol the character that is to be the new multi-line symbol
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Returns the symbol which denotes the start of each shell prompt.
     *
     * @return the symbol which denotes the start of each shell prompt
     */
    Character getPromptSymbol();

    /**
     * Changes the current prompt symbol to the given character.
     *
     * @param symbol the character that is to be the new prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * Returns the symbol that must be at the end of each line (except the last) of a
     * multi-line command to inform the shell that more lines are expected.
     *
     * @return the current more lines symbol
     */
    Character getMorelinesSymbol();

    /**
     * Changes the current more lines symbol to the given character.
     *
     * @param symbol the character that is to be the new more lines symbol
     */
    void setMorelinesSymbol(Character symbol);

    /**
     * Returns the normalized absolute path to the current directory of the running
     * java process.
     *
     * @return the normalized absolute path to the current directory of the running
     *         java process
     */
    Path getCurrentDirectory();

    /**
     * Sets the current directory to the directory specified by a given path.
     *
     * @param path the path to the new current directory
     * @throws IllegalArgumentException if the path doesn't point to an existing
     *         directory
     */
    void setCurrentDirectory(Path path);

    /**
     * Returns the shell commands' shared data specified by a given key.
     *
     * @param key the key of the shared command data to return
     * @return the shell commands' shared data specified by the given key
     */
    Object getSharedData(String key);

    /**
     * Adds the given key-value pair to the shell commands' shared data.
     *
     * @param key the key of the data to add
     * @param value the value of the data to add
     * @throws NullPointerException if the given key is {@code null}
     */
    void setSharedData(String key, Object value);
}
