package hr.fer.zemris.java.hw06.shell.utility;

import java.util.Objects;
import java.util.function.Predicate;

public class NameBuilderLexer {

    /**
     * Character array of the given arguments string.
     */
    private char[] chars;

    /**
     * The index of the first unparsed character.
     */
    private int currentIndex;

    private boolean inSubstitution;

    public NameBuilderLexer(String expression) {
        chars = Objects.requireNonNull(expression).toCharArray();
        inSubstitution = false;
    }

    /**
     * Parses and returns the next argument.
     *
     * @return the next argument
     * @throws IllegalArgumentException if the argument cannot be properly parsed
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

    public boolean hasNextToken() {
        return currentIndex < chars.length;
    }

    /*
     * -----------------------------------------------------------------------------
     * ------------------------------ HELPER METHODS -------------------------------
     * -----------------------------------------------------------------------------
     */

    /**
     * Constructs an argument string of {@link #chars} characters that satisfy a
     * given {@link Predicate}'s condition.
     *
     * @param tester the tester that checks if the currently observed character
     *               still belongs to the current token
     * @return an argument string of {@link #chars} characters
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

    private boolean substitutionIsOn(int index) {
        return index + 1 < chars.length && chars[index] == '$' && chars[index + 1] == '{';
    }

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
        return index + 1 < chars.length && chars[index] == '\\' && chars[index + 1] == '\\';
    }
}
