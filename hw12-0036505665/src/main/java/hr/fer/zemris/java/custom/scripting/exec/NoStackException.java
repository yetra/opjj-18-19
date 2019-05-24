package hr.fer.zemris.java.custom.scripting.exec;

/**
 * An exception to be thrown if required stack does not exist in {@link ObjectMultistack}.
 *
 * @author Bruna Dujmović
 *
 */
public class NoStackException extends RuntimeException {

    /**
     * Default constructor for {@link NoStackException}.
     */
    public NoStackException() {
        super();
    }

    /**
     * Constructor with a message about the exception.
     *
     * @param message a message about the exception
     */
    public NoStackException(String message) {
        super(message);
    }
}
