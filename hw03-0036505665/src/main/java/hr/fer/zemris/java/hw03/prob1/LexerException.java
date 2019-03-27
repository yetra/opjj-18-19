package hr.fer.zemris.java.hw03.prob1;

/**
 * An exeption to be thrown if there is an issue with token retrieval.
 *
 * @author Bruna Dujmović
 *
 */
public class LexerException extends RuntimeException {

    /**
     * Default constructor for the {@code LexerException} class.
     */
    public LexerException() {
        super();
    }

    /**
     * Constructor with a message about the exception.
     *
     * @param message a message about the exception
     */
    public LexerException(String message) {
        super(message);
    }
}
