package hr.fer.zemris.java.custom.scripting.parser;

/**
 * An exeption to be thrown if there is an issue with parsing a given document.
 *
 * @author Bruna Dujmović
 *
 */
public class SmartScriptParserException extends RuntimeException {

    /**
     * Default constructor for the {@link SmartScriptParserException} class.
     */
    public SmartScriptParserException() {
        super();
    }

    /**
     * Constructor with a message about the exception.
     *
     * @param message a message about the exception
     */
    public SmartScriptParserException(String message) {
        super(message);
    }
}
