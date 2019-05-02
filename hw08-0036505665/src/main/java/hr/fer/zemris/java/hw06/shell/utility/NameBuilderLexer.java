package hr.fer.zemris.java.hw06.shell.utility;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A lexer for the {@link NameBuilderParser}. It separates a given expression into
 * tokens of two different types - string or substitution.
 *
 * @see NameBuilderLexerTokenType
 * @author Bruna Dujmović
 * 
 */
public class NameBuilderLexer {

    /**
     * Character array of the given arguments string.
     */
    private char[] chars;

    /**
     * The index of the first unparsed character.
     */
    private int currentIndex;

    /**
     * {@code true} is this lexer is currently inside of a substitution (between ${
     * and }).
     */
    private boolean inSubstitution;

    /**
     * Constructs a {@link NameBuilderLexer} for the given expression string.
     *
     * @param expression the expression string
     */
    public NameBuilderLexer(String expression) {
        chars = Objects.requireNonNull(expression).toCharArray();
        inSubstitution = false;
    }

    /**
     * Parses and returns the next token.
     *
     * @return the next token
     * @throws IllegalArgumentException if the token cannot be properly parsed
     */
    public NameBuilderLexerToken nextToken() {
        skipBlanks();

        if (!hasNextToken()) {
            return null;
        }

        if (substitutionIsOn(currentIndex)) {
            currentIndex += 2;
            inSubstitution = true;
            String value = buildStringWhile((index) -> chars[index] != '}');

            if (currentIndex >= chars.length) {
                throw new IllegalArgumentException("Substitution bracket was never closed.");
            }

            inSubstitution = false;
            currentIndex++;
            return new NameBuilderLexerToken(NameBuilderLexerTokenType.SUBSTITUTION, value);

        } else {
            String value = buildStringWhile((index) -> !substitutionIsOn(index));
            return new NameBuilderLexerToken(NameBuilderLexerTokenType.STRING, value);
        }
    }

    /**
     * Returns {@code true} if this lexer still has more input to tokenize.
     *
     * @return {@code true} if this lexer still has more input to tokenize
     */
    public boolean hasNextToken() {
        return currentIndex < chars.length;
    }

    /*
     * -----------------------------------------------------------------------------
     * ------------------------------ HELPER METHODS -------------------------------
     * -----------------------------------------------------------------------------
     */

    /**
     * Constructs a token string of {@link #chars} characters that satisfy a given
     * {@link Predicate}'s condition.
     *
     * @param tester the tester that checks if the currently observed character still
     *               belongs to the current token
     * @return a token string of {@link #chars} characters
     */
    private String buildStringWhile(Predicate<Integer> tester) {
        StringBuilder sb = new StringBuilder();

        while (currentIndex < chars.length && tester.test(currentIndex)) {
            if (!inSubstitution && escapingIsOn(currentIndex)) {
                currentIndex++;
            }

            sb.append(chars[currentIndex++]);
        }

        return sb.toString();
    }

    /**
     * Skips blanks in the {@link #chars} character array.
     */
    private void skipBlanks() {
        while (currentIndex < chars.length && whitespaceIsOn(currentIndex)) {
            currentIndex++;
        }
    }

    /**
     * Returns {@code true} if a substitution starts on the given index. A substitution
     * starts if the character on the current index is '$' and the character on the
     * next index is '{'.
     *
     * @param index the index to check
     * @return {@code true} if a substitution starts on the given index
     */
    private boolean substitutionIsOn(int index) {
        return index + 1 < chars.length && chars[index] == '$'
                && chars[index + 1] == '{';
    }

    /**
     * Returns {@code true} if a whitespace character is on the given index.
     *
     * @param index the index to check
     * @return {@code true} if a whitespace character is on the given index
     */
    private boolean whitespaceIsOn(int index) {
        return Character.isWhitespace(chars[index]);
    }

    /**
     * Returns {@code true} if escaping starts on the current index and a valid
     * escaped character follows.
     *
     * @param index the index of the first character to check
     * @return {@code true} if escaping starts on the current index and a valid
     *         escaped character follows
     */
    private boolean escapingIsOn(int index) {
        return index + 1 < chars.length && chars[index] == '\\'
                && chars[index + 1] == '\\';
    }
}
