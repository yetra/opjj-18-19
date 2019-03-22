package hr.fer.zemris.java.custom.collections;

/**
 * An exception for an empty stack.
 *
 * @author Bruna Dujmović
 *
 */
public class EmptyStackException extends RuntimeException {

    /**
     * Default constructor for the {@code EmptyStackException} class.
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Constructor with a message about the exception.
     *
     * @param message a message about the exception
     */
    public EmptyStackException(String message) {
        super(message);
    }
}
