package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

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
     * @throws NullPointerException if the given text is {@code null}
     */
    public Lexer(String text) {
        Objects.requireNonNull(text);

        data = text.toCharArray();
        currentIndex = 0;
    }

    /**
     * Generates and returns the next toxen.
     *
     * @return the next token
     * @throws LexerException if there are no more characters to tokenize
     */
    public Token nextToken() {
        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException();
        }

        skipBlanks();

        if (currentIndex >= data.length) {
            token = new Token(TokenType.EOF, null);
        }

        return token;
    }

    /**
     * A helper method which skips all the blanks in the {@code data} character array.
     */
    private void skipBlanks() {
        while (currentIndex < data.length &&
                Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }
    }

    /**
     * Returns the last generated token. Calling this method does not start the
     * generation of the next token.
     *
     * @return the last generated token
     */
    public Token getToken() {
        return token;
    }
}
