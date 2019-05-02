package hr.fer.zemris.java.hw06.shell.utility;

/**
 * An enumeration of all possible token types for {@link NameBuilderLexer}.
 *
 * @author Bruna Dujmović
 *
 */
public enum  NameBuilderLexerTokenType {

    /**
     * The string token type (anything that is not a substitution).
     */
    STRING,

    /**
     * The substitution token type (anything that is between '${' and '}').
     */
    SUBSTITUTION
}
