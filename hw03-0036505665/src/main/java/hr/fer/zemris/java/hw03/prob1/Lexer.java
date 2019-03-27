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
     * The current state of the lexer.
     */
    private LexerState state;

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
        setState(LexerState.BASIC);
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
     * Sets this lexer's state to the specified value.
     *
     * @param state the state to set
     */
    public void setState(LexerState state) {
        Objects.requireNonNull(state);

        this.state = state;
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

        if (state == LexerState.BASIC) {
            token = getBasicStateToken();
        } else {
            token = getExtendedStateToken();
        }

        return token;
    }

    /*
     * -----------------------------------------------------------------------------
     * ------------------------------ HELPER METHODS -------------------------------
     * -----------------------------------------------------------------------------
     */

    /**
     * Returns the next basic state token.
     *
     * @return the next basic state token
     */
    private Token getBasicStateToken() {
        if (letterOrEscapingIsOn(currentIndex)) {
            String tokenValue = readTokenStringWhile(this::letterOrEscapingIsOn);
            return new Token(TokenType.WORD, tokenValue);

        } else if (digitIsOn(currentIndex)) {
            String tokenValue = readTokenStringWhile(this::digitIsOn);

            try {
                return new Token(TokenType.NUMBER, Long.parseLong(tokenValue));
            } catch (NumberFormatException ex) {
                throw new LexerException("Can't parse " + tokenValue + " to long.");
            }

        } else {
            Character tokenValue = data[currentIndex++];
            return new Token(TokenType.SYMBOL, tokenValue);
        }
    }

    /**
     * Returns the next extended state token.
     *
     * @return the next extended state token
     */
    private Token getExtendedStateToken() {
        if (data[currentIndex] == '#') {
            Character tokenValue = data[currentIndex++];
            return new Token(TokenType.SYMBOL, tokenValue);

        } else {
            String tokenValue = readTokenStringWhile(this::blankOrHashIsNotOn);
            return new Token(TokenType.WORD, tokenValue);
        }
    }

    /**
     * Constructs a token value string of {@link #data} characters that satisfy
     * a given {@link CharacterTester}'s condition.
     *
     * @param tester the tester that checks if the currently observed character still
     *               belongs to the current token
     * @return a token value string of {@link #data} characters
     */
    private String readTokenStringWhile(CharacterTester tester) {
        StringBuilder sb = new StringBuilder();
        int endIndex = currentIndex;

        while (endIndex < data.length && tester.testCharOn(endIndex)) {
            if (state == LexerState.BASIC && data[endIndex] == '\\') {
                sb.append(data[endIndex+1]);
                endIndex += 2;
            } else {
                sb.append(data[endIndex]);
                endIndex++;
            }
        }

        currentIndex = endIndex; // TODO -1?
        return sb.toString();
    }

    /**
     * Skips blanks in the {@link #data} character array.
     */
    private void skipBlanks() {
        while (currentIndex < data.length &&
                Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }
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
     * Returns {@code true} if the character on the specified index is not a blank or
     * a hashtag.
     *
     * @param index the index of the character to check
     * @return {@code true} if the character on the specified index is not a blank or
     *         a hashtag
     */
    private boolean blankOrHashIsNotOn(int index) {
        return !Character.isWhitespace(data[index]) && data[index] != '#';
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
