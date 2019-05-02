package hr.fer.zemris.java.hw06.shell.utility;

/**
 * An interface for generating parts of a file name for the massrename shell command.
 *
 * @author Bruna Dujmović
 *
 */
public interface NameBuilder {

    /**
     * Generates the parts of a file name and writes them to the given {@link StringBuilder}.
     *
     * @param result the result whose file name should be generated
     * @param sb the {@link StringBuilder} object for storing file name parts
     */
    void execute(FilterResult result, StringBuilder sb);
}
