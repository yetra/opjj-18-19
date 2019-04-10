package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

public class QueryLexer {
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
     * Constructs a lexer which tokenizes the given query.
     *
     * @param query the query to tokenize
     * @throws NullPointerException if the given query is {@code null}
     */
    public QueryLexer(String query) {
        Objects.requireNonNull(query);

        data = query.toCharArray();
        currentIndex = 0;
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
     * Generates and returns the next toxen.
     *
     * @return the next token
     * @throws QueryLexerException if there are no more characters to tokenize
     */
    public Token nextToken() {
        if (token != null && token.getType() == TokenType.EOF) {
            throw new QueryLexerException("There are no more characters to tokenize.");
        }

        skipBlanks();

        if (currentIndex >= data.length) {
            return token = new Token(TokenType.EOF, null);
        }

        return token = getNextToken();
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
    private Token getNextToken() {
        if (stringIsOn(currentIndex)) {
            currentIndex++;
            String tokenValue = readTokenStringWhile(index -> !stringIsOn(index));
            currentIndex++;
            return new Token(TokenType.STRING, tokenValue);

        } else if (twoCharComparisonOperatorIsOn(currentIndex)) {
            String tokenValue = new String(data, currentIndex, 2);
            currentIndex += 2;
            return new Token(TokenType.COMPARISON_OPERATOR, tokenValue);
        } else if (singleCharComparisonOperatorIsOn(currentIndex)) {
            String tokenValue = String.valueOf(data[currentIndex]);
            currentIndex++;
            return new Token(TokenType.COMPARISON_OPERATOR, tokenValue);
        } else if (LIKEIsOn(currentIndex)) {
            currentIndex += 4;
            return new Token(TokenType.COMPARISON_OPERATOR, "LIKE");

        } else if (logicalOperatorIsOn(currentIndex)) {
            currentIndex += 4;
            return new Token(TokenType.LOGICAL_OPERATOR, "AND");

        } else {
            String tokenValue = readTokenStringWhile(this::fieldNameIsOn);
            return new Token(TokenType.FIELD_NAME, tokenValue);
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
        int endIndex = currentIndex; // TODO remove?

        while (endIndex < data.length && tester.testCharOn(endIndex)) {
            sb.append(data[endIndex]);
            endIndex++;
        }

        currentIndex = endIndex;
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
     * Returns {@code true} if a string is on the specified index.
     * @see TokenType#STRING
     *
     * @param index the index of the first character to check
     * @return {@code true} if a string is on the specified index
     */
    private boolean stringIsOn(int index) {
        return data[index] == '"';
    }

    private boolean singleCharComparisonOperatorIsOn(int index) {
        return data[index] == '<'
                || data[index] == '>' || data[index] == '=';
    }

    private boolean twoCharComparisonOperatorIsOn(int index) {
        return index < data.length-1
                && (data[index] == '>' || data[index] == '<'
                || data[index] == '!')
                && data[index+1] == '=';
    }

    private boolean LIKEIsOn(int index) {
        return index < data.length-3
                && data[index] == 'L' && data[index+1] == 'I'
                && data[index+2] == 'K' && data[index+3] == 'E';
    }

    private boolean logicalOperatorIsOn(int index) {
        return index < data.length-2
                && (data[index] == 'A' || data[index] == 'a')
                && (data[index+1] == 'N' || data[index+1] == 'n')
                && (data[index+2] == 'D' || data[index+2] == 'd');
    }

    private boolean fieldNameIsOn(int index) {
        return !Character.isWhitespace(data[index])
                && !singleCharComparisonOperatorIsOn(index)
                && !twoCharComparisonOperatorIsOn(index)
                && !LIKEIsOn(index);
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
