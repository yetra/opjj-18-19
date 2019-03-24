package hr.fer.zemris.java.hw03.prob1;

/**
 * This class models a lexer that can tokenize a given input text.
 *
 * @author Bruna Dujmović
 *
 */
public class Lexer {

    /**
     * Character array of the input text.
     */
    private char[] data;

    /**
     * The current token.
     */
    private Token token;

    /**
     * The index of the first untokenized character.
     */
    private int currentIndex;

    /**
     * Constructs a lexer which tokenizes the given text.
     *
     * @param text the text to tokenize
     */
    public Lexer(String text) {
        data = text.toCharArray();
        currentIndex = 0;
        token = nextToken();
    }

    /**
     * Generates and returns the next toxen.
     *
     * @return the next token
     * @throws LexerException if there is an issue with generating the next token
     */
    public Token nextToken() {
        return null;
    }

    /**
     * Returns the last generated token. Calling this method does not start the
     * generation of the next token.
     *
     * @return the last generated token
     */
    public Token getToken() {
        return null;
    }
}
