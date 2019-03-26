package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * A class which models a token produced by a {@code SmartScriptLexer} object.
 *
 * @author Bruna Dujmović
 *
 */
public class SmartScriptToken {

    /**
     * The type of this token.
     */
    private SmartScriptTokenType type;

    /**
     * The value of this token.
     */
    private Object value;

    /**
     * Constructs a token based on its type an value.
     *
     * @param type the type of the token
     * @param value the value of the token
     * @throws NullPointerException if the given token is {@code null}
     */
    public SmartScriptToken(SmartScriptTokenType type, Object value) {
        Objects.requireNonNull(type);

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
    public SmartScriptTokenType getType() {
        return type;
    }
}
