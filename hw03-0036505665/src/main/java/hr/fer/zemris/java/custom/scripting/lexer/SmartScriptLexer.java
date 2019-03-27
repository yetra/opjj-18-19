package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * This class models a lexer that tokenizes a given text.
 *
 * @author Bruna Dujmović
 *
 */
public class SmartScriptLexer {

    /**
     * Character array of the input text.
     */
    private char[] data;

    /**
     * The current token.
     */
    private SmartScriptToken token;

    /**
     * The index of the first untokenized character.
     */
    private int currentIndex;

    /**
     * The current state of the lexer.
     */
    private SmartScriptLexerState state;

    /**
     * Constructs a lexer which tokenizes the given text.
     *
     * @param text the text to tokenize
     * @throws NullPointerException if the given text is {@code null}
     */
    public SmartScriptLexer(String text) {
        Objects.requireNonNull(text);

        data = text.toCharArray();
        currentIndex = 0;
        setState(SmartScriptLexerState.TEXT);
    }

    /**
     * Generates and returns the next token.
     *
     * @return the next token
     * @throws SmartScriptLexerException if there are no more characters to tokenize
     */
    public SmartScriptToken nextToken() {
        if (token != null && token.getType() == SmartScriptTokenType.EOF) {
            throw new SmartScriptLexerException(
                    "There are no more characters to tokenize.");
        }

        skipBlanksIfTagState();

        if (currentIndex >= data.length) {
            return token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
        }

        if (state == SmartScriptLexerState.TAG) {
            token = getNextTagToken();
        } else {
            token = getNextTextToken();
        }

        return token;
    }

    /**
     * Returns the last generated token. Calling this method does not start the
     * generation of the next token.
     *
     * @return the last generated token
     */
    public SmartScriptToken getToken() {
        return token;
    }

    /**
     * Sets this lexer's state to the specified value.
     *
     * @param state the state to set
     */
    public void setState(SmartScriptLexerState state) {
        Objects.requireNonNull(state);

        this.state = state;
    }

    /**
     * Returns this lexer's current state.
     *
     * @return this lexer's current state
     */
    public SmartScriptLexerState getState() {
        return state;
    }

    /* ------------------------------------------------------------------------------
     * ------------------------------- HELPER METHODS -------------------------------
     * ------------------------------------------------------------------------------
     */

    /**
     * Returns the next text state token.
     *
     * @return the next text state token
     * @throws SmartScriptLexerException if the input is not valid in the text state
     */
    private SmartScriptToken getNextTextToken() {

        if (tagStartIsOn(currentIndex)) {
            currentIndex += 2;
            return new SmartScriptToken(SmartScriptTokenType.TAG_START, null);

        } else {
            String tokenValue = readStringUntil(index -> !tagStartIsOn(index), true);
            return new SmartScriptToken(SmartScriptTokenType.STRING, tokenValue);
        }
    }

    /**
     * Returns the next tag state token.
     *
     * @return the next tag state token
     * @throws SmartScriptLexerException if the input is not valid in the tag state
     */
    private SmartScriptToken getNextTagToken() {

        if (tagEndIsOn(currentIndex)) {
            currentIndex += 2;
            return new SmartScriptToken(SmartScriptTokenType.TAG_END, null);

        } else if (escapingIsOn(currentIndex, '\"')) {
            return getNextQuotedStringToken();

        } else if (letterIsOn(currentIndex) || escapingIsOn(currentIndex, '\\')) {
            String tokenValue = readStringUntil(
                    index -> letterIsOn(index) || escapingIsOn(index, '\\'), true);
            return new SmartScriptToken(SmartScriptTokenType.STRING, tokenValue);

        } else if (numberStartsOn(currentIndex)) {
            try {
                return getNextNumberToken();
            } catch (NumberFormatException ex) {
                throw new SmartScriptLexerException("Invalid number format.");
            }

        } else {
            Character tokenValue = data[currentIndex++];
            return new SmartScriptToken(SmartScriptTokenType.SYMBOL, tokenValue);
        }
    }

    /**
     * Returns a string token that is inside escaped quotations.
     *
     * @return a string token that is inside escaped quotations
     * @throws SmartScriptLexerException if the end of the input is reached but the
     *         quotation is never closed
     */
    private SmartScriptToken getNextQuotedStringToken() {
        String tokenValue = "\"";
        currentIndex += 2;

        tokenValue += readStringUntil(index -> !escapingIsOn(index, '\"'), true);

        if (currentIndex >= data.length) {
            throw new SmartScriptLexerException("Quote never closed.");
        }

        tokenValue += "\"";
        currentIndex += 2;

        return new SmartScriptToken(SmartScriptTokenType.STRING, tokenValue);
    }

    /**
     * Returns a number token.
     *
     * @return a number token
     * @throws NumberFormatException if a number token cannot be constructed
     */
    private SmartScriptToken getNextNumberToken() {
        String tokenValue = "";

        if (data[currentIndex] == '-') {
            tokenValue += "-";
            currentIndex++;
        }

        tokenValue += readStringUntil(this::digitIsOn, false);

        if (currentIndex < data.length && data[currentIndex] == '.'
                && digitIsOn(currentIndex+1)) {
            tokenValue += ".";
            currentIndex++;

            tokenValue += readStringUntil(this::digitIsOn, false);
            return new SmartScriptToken(
                    SmartScriptTokenType.DECIMAL, Double.parseDouble(tokenValue));
        }

        return new SmartScriptToken(
                SmartScriptTokenType.INTEGER, Integer.parseInt(tokenValue));
    }

    /**
     * Constructs a string of {@link #data} characters that satisfy a given
     * tester.
     *
     * @param tester the tester that checks if a given character satisfies its
     *               condition
     * @param escapingAllowed {@code true} if escaping is allowed in the string that
     *                        will be constructed
     * @return the constructed string
     * @throws SmartScriptLexerException if escaping is not valid
     */
    private String readStringUntil(CharacterTester tester, boolean escapingAllowed) {
        StringBuilder tokenValue = new StringBuilder();
        int endIndex = currentIndex;

        while (endIndex < data.length && tester.testCharOn(endIndex)) {

            if (escapingAllowed && (escapingIsOn(endIndex, '\\') ||
                    (state == SmartScriptLexerState.TEXT && escapingIsOn(endIndex, '{')))) {
                tokenValue.append(data[endIndex+1]);
                endIndex += 2;

            } else {
                tokenValue.append(data[endIndex]);
                endIndex++;
            }
        }

        currentIndex = endIndex;
        return tokenValue.toString();
    }

    /**
     * A helper method that skips all the blanks in the character array
     * if the lexer is in TAG state.
     */
    private void skipBlanksIfTagState() {
        if (state == SmartScriptLexerState.TAG) {
            while (currentIndex < data.length
                    && Character.isWhitespace(data[currentIndex])) {
                currentIndex++;
            }
        }
    }

    /**
     * Returns {@code true} if a tag start "{$" begins on the specified index.
     *
     * @param index the index of the first character to check
     * @return {@code true} if a tag start "{$" begins on the specified index
     */
    private boolean tagStartIsOn(int index) {
        return index < data.length-1 && data[index] == '{' && data[index+1] == '$';
    }

    /**
     * Returns {@code true} if a tag end "$}" begins on the specified index.
     *
     * @param index the index of the first character to check
     * @return {@code true} if a tag end "$}" begins on the specified index
     */
    private boolean tagEndIsOn(int index) {
        return index < data.length-1 && data[index] == '$' && data[index+1] == '}';
    }

    /**
     * Returns {@code true} if a letter is on the specified index.
     *
     * @param index the index of the character to check
     * @return {@code true} if a letter is on the specified index
     */
    private boolean letterIsOn(int index) {
        return index < data.length && Character.isLetter(data[index]);
    }

    /**
     * Returns {@code true} if a digit is on the specified index.
     *
     * @param index the index of the character to check
     * @return {@code true} if a digit is on the specified index
     */
    private boolean digitIsOn(int index) {
        return index < data.length && Character.isDigit(data[index]);
    }

    /**
     * Returns {@code true} if a valid escaping begins on the specified index.
     * A valid escaping begins on a specified index if the character on that index
     * is a backslash and the character on the next index can be escaped.
     *
     * @param index the index of the first character to check
     * @return {@code true} if a valid escaping begins on the specified index
     * @throws SmartScriptLexerException if escaping is not valid
     */
    private boolean escapingIsOn(int index, char escaped) {
        if (data[index] == '\\') {
            if (index + 1 >= data.length) {
                throw new SmartScriptLexerException(
                        "Reached end of data, nothing to escape.");
            }

            boolean invalidTagStateEscaping = state == SmartScriptLexerState.TAG
                    && data[index+1] != '\\' && data[index+1] != '\"';
            boolean invalidTextStateEscaping = state == SmartScriptLexerState.TEXT
                    && data[index+1] != '\\' && data[index+1] != '{';

            if (invalidTagStateEscaping || invalidTextStateEscaping) {
                throw new SmartScriptLexerException(
                        "Invalid escaped character \"" + data[index+1] + "\".");
            }

            return data[index+1] == escaped; // TODO return true?
        }
        return false;
    }

    /**
     * Returns {@code true} if a number begins on the specified index.
     * A number begins on a given index if the character on that index is a digit,
     * or a minus sign and the next character is a digit.
     *
     * @param index the index of the first character to check
     * @return {@code true} if a number begins on the specified index
     */
    private boolean numberStartsOn(int index) {
        return digitIsOn(index) || (data[index] == '-' && digitIsOn(index+1));
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
