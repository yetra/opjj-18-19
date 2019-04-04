package hr.fer.zemris.java.hw03.prob1;

/**
 * An enum of all possible lexer states.
 *
 * @author Bruna Dujmović
 *
 */
public enum  LexerState {
    /**
     * This state tokenizes everything as a single {@link TokenType#WORD}. No escaping
     * are possible. The '#' symbol denotes the start and end of this state.
     */
    BASIC,

    /**
     * This state tokenizes the given input into {@link TokenType#WORD},
     * {@link TokenType#NUMBER}, {@link TokenType#SYMBOL} tokens. It is possible to
     * escape a backslash or a digit.
     */
    EXTENDED
}
 