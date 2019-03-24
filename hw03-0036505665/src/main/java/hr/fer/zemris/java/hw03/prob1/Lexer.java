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
        if (letterOrEscapingIsOn(currentIndex)) {
            tokenEnd = getTokenEnd(currentIndex, this::letterOrEscapingIsOn);
            String word = new String(data, currentIndex, tokenEnd-currentIndex+1);

            // TODO better replace for escaped characters
            String replaceDigitEscapes = word.replaceAll("\\\\(?=\\d)", "");
            String squashMultipleBackslashes = replaceDigitEscapes.replaceAll("\\\\+", "\\\\");

            token = new Token(TokenType.WORD, squashMultipleBackslashes);

        } else if (digitIsOn(currentIndex)) {
            tokenEnd = getTokenEnd(currentIndex, this::digitIsOn);
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
     * @param tester a tester that checks if the currently observed character still
     *               belongs to the current token
     * @return the index of the last character that belongs to the current token
     */
    private int getTokenEnd(int startIndex, CharacterTester tester) {
        int endIndex = startIndex;

        while (endIndex < data.length && tester.testCharOn(endIndex)) {
            if (data[endIndex] == '\\') {
                // if escaping occurred, skip both the backslash and the escaped char
                endIndex += 2;
            } else {
                // otherwise, just skip a normal character
                endIndex++;
            }
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
     * Returns {@code true} if a letter is on the specified index or if valid escaping
     * is attempted.
     *
     * Specifically, if {@code index} points to a '\' (the character used for
     * escaping) this method will check whether the following character can be escaped
     * and return {@code true} or {@code false} accordingly.
     *
     * @param index the index of the character/escaping to check
     * @return {@code true} if a letter is on the specified index or if valid escaping
     *         is attempted
     */
    private boolean letterOrEscapingIsOn(int index) {
        return Character.isLetter(data[index])
                || (data[index] == '\\' && canEscapeCharOn(index+1));
    }

    /**
     * Returns {@code true} if the character on the specified index can be escaped.
     *
     * @param index the index of the character to check
     * @return {@code true} if the character on the specified index can be escaped
     * @throws LexerException if {@code index} is out of bounds or the character can't
     *         be escaped
     */
    // TODO if exception is not thrown, method should always return true?
    private boolean canEscapeCharOn(int index) {
        if (index >= data.length || Character.isLetter(data[index])) {
            throw new LexerException("Invalid escaped character.");
        }

        return data[index] == '\\' || Character.isDigit(data[index]);
    }

    /**
     * Returns {@code true} if a digit is on the specified index.
     *
     * @param index the index of the character to check
     * @return {@code true} if a digit is on the specified index
     */
    private boolean digitIsOn(int index) {
        return Character.isDigit(data[index]);
    }

    /**
     * A functional interface that models a tester of character types.
     */
    @FunctionalInterface
    private static interface CharacterTester {

        /**
         * Returns {@code true} if the character on the given index satisfies this
         * tester's condition.
         *
         * @param index the index of the character to test
         * @return {@code true} if the given character satisfies this tester's
         *         condition
         */
        boolean testCharOn(int index);
    }
}
