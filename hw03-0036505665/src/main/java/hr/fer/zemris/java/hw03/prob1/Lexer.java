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
            throw new LexerException("There are no more characters to tokenize.");
        }

        skipBlanks();

        if (currentIndex >= data.length) {
            return token = new Token(TokenType.EOF, null);
        }

        // TODO make a helper method that returns a complete token String, not just the end index?
        int tokenEnd;
        if (Character.isLetter(data[currentIndex])) {
            tokenEnd = getTokenEnd(currentIndex, Character::isLetter);
            String word = new String(data, currentIndex, tokenEnd-currentIndex+1);

            token = new Token(TokenType.WORD, word);

        } else if (Character.isDigit(data[currentIndex])) {
            tokenEnd = getTokenEnd(currentIndex, Character::isDigit);
            String number = new String(data, currentIndex, tokenEnd-currentIndex+1);

            try {
                token = new Token(TokenType.NUMBER, Long.parseLong(number));
            } catch (NumberFormatException ex) {
                throw new LexerException("Can't parse " + number + " to long.");
            }

        } else {
            tokenEnd = currentIndex;
            char symbol = data[currentIndex];

            token = new Token(TokenType.SYMBOL, symbol);
        }

        currentIndex = tokenEnd + 1;
        return token;
    }

    /**
     * Returns the index of the last character that belongs to the current token.
     *
     * @param startIndex the index of
     * @param tester a tester that checks if the currently observed character still belongs to the current token
     * @return the index of the last character that belongs to the current token
     */
    private int getTokenEnd(int startIndex, CharacterTester tester) {
        int endIndex = startIndex;

        while (endIndex < data.length && tester.test(data[endIndex])) {
            endIndex++;
        }

        return endIndex-1;
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

    /**
     * A functional interface that models a tester of character types.
     */
    @FunctionalInterface
    private static interface CharacterTester {

        /**
         * Returns {@code true} if the given character satisfies this tester's condition.
         *
         * @param ch the character to test
         * @return {@code true} if the given character satisfies this tester's condition
         */
        boolean test(char ch);
    }
}
