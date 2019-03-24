package hr.fer.zemris.java.hw03.prob1;

/**
 * A class which models a token produced by a {@code Lexer} object.
 *
 * @author Bruna Dujmović
 *
 */
public class Token {

    /**
     * The type of this token.
     */
    private TokenType type;

    /**
     * The value of this token.
     */
    private Object value;

    /**
     * Constructs a token based on its type an value.
     *
     * @param type the type of the token
     * @param value the value of the token
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns the value of this token.
     *
     * @return the value of this token
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the type of this token.
     *
     * @return the type of this token
     */
    public TokenType getType() {
        return type;
    }
}
