package hr.fer.zemris.java.hw03.prob1;

/**
 * An enum of all possible token types.
 *
 * @author Bruna Dujmović
 * 
 */
public enum TokenType {

    /**
     * End of the input.
     */
    EOF,

    /**
     * A string composed solely of letters.
     */
    WORD,

    /**
     * An integer.
     */
    NUMBER,

    /**
     * A single character that is not a whitespace, letter or digit.
     */
    SYMBOL
}
