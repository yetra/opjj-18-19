package hr.fer.zemris.java.hw06.shell.utility;

import java.util.Objects;

/**
 * A class which models a token produced by the {@link NameBuilderLexer}.
 *
 * @author Bruna Dujmović
 *
 */
public class NameBuilderLexerToken {

    /**
     * The type of this token.
     */
    private NameBuilderLexerTokenType type;

    /**
     * The value of this token.
     */
    private String value;

    /**
     * Constructs a token based on its type an value.
     *
     * @param type the type of the token
     * @param value the value of the token
     * @throws NullPointerException if the given token is {@code null}
     */
    public NameBuilderLexerToken(NameBuilderLexerTokenType type, String value) {
        Objects.requireNonNull(type);

        this.type = type;
        this.value = value;
    }

    /**
     * Returns the value of this token.
     *
     * @return the value of this token
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the type of this token.
     *
     * @return the type of this token
     */
    public NameBuilderLexerTokenType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameBuilderLexerToken that = (NameBuilderLexerToken) o;
        return type == that.type &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
