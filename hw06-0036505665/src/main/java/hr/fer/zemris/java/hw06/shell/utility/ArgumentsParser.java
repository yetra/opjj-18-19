package hr.fer.zemris.java.hw06.shell.utility;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * This class splits a given string of command arguments into separate strings.
 *
 * Repeatedly calling {@link #nextArgument()} while {@link #hasNextArgument()} is
 * {@code true} will return all the parsed arguments.
 *
 * @author Bruna Dujmović
 *
 */
class ArgumentsParser {

    /**
     * Character array of the given arguments string.
     */
    private char[] chars;

    /**
     * The index of the first unparsed character.
     */
    private int currentIndex;

    /**
     * {@code true} if a quotation is currently being parsed.
     */
    private boolean inQuotation;

    /**
     * Constructs a parser for the given string of arguments.
     *
     * @param arguments the string of arguments to parse
     * @throws NullPointerException if the given arguments string is {@code null}
     */
    ArgumentsParser(String arguments) {
        Objects.requireNonNull(arguments);

        chars = arguments.toCharArray();
        currentIndex = 0;
        inQuotation = false;
    }

    /**
     * Parses and returns the next argument.
     *
     * @return the next argument
     */
    String nextArgument() {
        skipBlanks();

        if (!hasNextArgument()) {
            return null;
        }

        if (quoteIsOn(currentIndex)) {
            inQuotation = true;
            currentIndex++;
            String argument = buildStringWhile(index -> !quoteIsOn(index));

            if (!quoteIsOn(currentIndex)) {
                throw new IllegalArgumentException("Quote is never closed!");
            }

            currentIndex++;
            inQuotation = false;
            return argument;

        } else {
            return buildStringWhile(index -> !whitespaceIsOn(index));
        }
    }

    /**
     * Returns {@code true} if the end of the arguments string has not been reached.
     *
     * @return {@code true} if the end of the arguments string has not been reached
     */
    boolean hasNextArgument() {
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
            if (inQuotation && escapingIsOn(currentIndex)) {
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
     * Returns {@code true} if a quote is on the specified index.
     *
     * @param index the index of the character to check
     * @return {@code true} if a quote is on the specified index
     */
    private boolean quoteIsOn(int index) {
        return index < chars.length && chars[index] == '"';
    }

    /**
     * Returns {@code true} if a whitespace is on the specified index.
     *
     * @param index the index of the character to check
     * @return {@code true} if a whitespace is on the specified index
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
                && ((chars[index + 1] == '"' || chars[index + 1] == '\\'));
    }

}
