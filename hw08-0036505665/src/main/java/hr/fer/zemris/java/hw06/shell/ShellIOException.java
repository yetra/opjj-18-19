package hr.fer.zemris.java.hw06.shell;

/**
 * A subclass of {@link RuntimeException} that is to be thrown if reading or writing
 * from an {@link Environment} object fails.
 *
 * @author Bruna Dujmović
 *
 */
public class ShellIOException extends RuntimeException {

    /**
     * Default constructor for the {@link ShellIOException} class.
     */
    public ShellIOException() {
        super();
    }

    /**
     * Constructor with a message about the exception.
     *
     * @param message a message about the exception
     */
    public ShellIOException(String message) {
        super(message);
    }
}
