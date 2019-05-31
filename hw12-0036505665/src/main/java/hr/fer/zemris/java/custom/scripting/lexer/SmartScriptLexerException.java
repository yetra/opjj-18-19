package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An exeption to be thrown if there is an issue with retrieving the next token.
 *
 * @author Bruna Dujmović
 *
 */
public class SmartScriptLexerException extends RuntimeException {

    /**
     * Default constructor for the {@code LexerException} class.
     */
    public SmartScriptLexerException() {
        super();
    }

    /**
     * Constructor with a message about the exception.
     *
     * @param message a message about the exception
     */
    public SmartScriptLexerException(String message) {
        super(message);
    }
}
