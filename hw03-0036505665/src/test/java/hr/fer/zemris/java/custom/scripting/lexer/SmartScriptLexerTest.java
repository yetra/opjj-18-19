package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {

    private SmartScriptLexerState[] states = {
            SmartScriptLexerState.TEXT,
            SmartScriptLexerState.TAG
    };

    @Test
    public void testNotNull() {
        for (SmartScriptLexerState state : states) {
            SmartScriptLexer lexer = new SmartScriptLexer("");

            lexer.setState(state);
            assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
        }
    }

    @Test
    public void testNullInput() {
        // must throw!
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
    }

    @Test
	public void testNullState() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}

    @Test
    public void testEmpty() {
        for (SmartScriptLexerState state : states) {
            SmartScriptLexer lexer = new SmartScriptLexer("");

            lexer.setState(state);
            assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
                    "Empty input must generate only EOF token.");
        }
    }

    @Test
    public void testGetReturnsLastNext() {
        // Calling getToken once or several times after calling nextToken
        // must return each time what nextToken returned...
        for (SmartScriptLexerState state : states) {
            SmartScriptLexer lexer = new SmartScriptLexer("");

            lexer.setState(state);

            SmartScriptToken token = lexer.nextToken();
            assertEquals(token, lexer.getToken(),
                    "getToken returned different token than nextToken.");
            assertEquals(token, lexer.getToken(),
                    "getToken returned different token than nextToken.");
        }
    }

    @Test
    public void testReadAfterEOF() {
        for (SmartScriptLexerState state : states) {
            SmartScriptLexer lexer = new SmartScriptLexer("");

            lexer.setState(state);
            // will obtain EOF
            lexer.nextToken();
            // will throw!
            assertThrows(SmartScriptLexerException.class, lexer::nextToken);
        }
    }

    @Test
    public void testNoActualContentTextState() {
        // When input is only of spaces, tabs, newlines, etc...
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");

        lexer.setState(SmartScriptLexerState.TEXT);
        assertEquals(SmartScriptTokenType.STRING, lexer.nextToken().getType(),
                "Input had no content. SmartScriptLexer should generate STRING in TEXT state");
    }

    @Test
    public void testNoActualContentTagState() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");

        lexer.setState(SmartScriptLexerState.TAG);
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testNoContentClosingTag() {
        // No content but end tag exists
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    $}");

        lexer.setState(SmartScriptLexerState.TAG);
        assertEquals(SmartScriptTokenType.TAG_END, lexer.nextToken().getType());
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testTextStateNoEscaping() {
        SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil   ");

        lexer.setState(SmartScriptLexerState.TEXT);
        lexer.nextToken();
        SmartScriptToken token = lexer.getToken();

        assertEquals(SmartScriptTokenType.STRING, token.getType());
        assertEquals("  Štefanija\r\n\t Automobil   ", token.getValue());
    }

    @Test
    public void testTextStateEscapingPositiveCases() {
        String[] testTexts = {
                "Some \\\\ test X",
                "    Escaping an opening bracket: \\{. \n\n Ecaping a backslash:\\\\",
                "Example { bla } blu \\{$=1$}. Nothing interesting {=here}.",
                "  \\{1st}  \r\n\t   ",
                "  \\\\  ",
                "  ab\\\\2cd\\{ \r\r ab\\\\2\\{1cd}\\{4}\\\\ \r\n\t   "
        };

        String[] expectedValues = {
                "Some \\ test X",
                "    Escaping an opening bracket: {. \n\n Ecaping a backslash:\\",
                "Example { bla } blu {$=1$}. Nothing interesting {=here}.",
                "  {1st}  \r\n\t   ",
                "  \\  ",
                "  ab\\2cd{ \r\r ab\\2{1cd}{4}\\ \r\n\t   "
        };

        for (int i = 0; i < testTexts.length; i++) {
            SmartScriptLexer lexer = new SmartScriptLexer(testTexts[i]);

            lexer.setState(SmartScriptLexerState.TEXT);
            lexer.nextToken();
            SmartScriptToken token = lexer.getToken();

            assertEquals(SmartScriptTokenType.STRING, token.getType());
            assertEquals(expectedValues[i], token.getValue());
        }
    }

    @Test
    public void testTextStateEscapingNegativeCases() {
        String[] testTexts = {
                "Can't  \t\t escape     quotes \\\" in \n TEXT state",
                "    Escaping a closing bracket: \\}. \n\n\t",
                "Escaping a digit \\123 is \r not possible."
        };

        String[] invalidChars = {"\"", "}", "1"};

        for (int i = 0; i < testTexts.length; i++) {
            SmartScriptLexer lexer = new SmartScriptLexer(testTexts[i]);

            lexer.setState(SmartScriptLexerState.TEXT);

            Exception exc = assertThrows(SmartScriptLexerException.class, lexer::nextToken);
            assertEquals("Invalid escaped character \"" + invalidChars[i] + "\".", exc.getMessage());
        }
    }

    @Test
    public void testTextStateEscapingEnding() {
        SmartScriptLexer lexer = new SmartScriptLexer(
                "Can't  \t\t escape     end of text \\");

        lexer.setState(SmartScriptLexerState.TEXT);

        Exception exc = assertThrows(SmartScriptLexerException.class, lexer::nextToken);
        assertEquals("Reached end of data, nothing to escape.", exc.getMessage());
    }

    @Test
    public void testTagStateEscapingPositiveCase() {
        SmartScriptLexer lexer = new SmartScriptLexer("This \"\\\\is    \\\"correct\\\" escaping!\" $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "This"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\\is    \"correct\" escaping!"),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null),
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTagStateStringEscapingNegativeCases() {
        String[] testTexts = {
                "\"bracket: \\{. \n\n\t\"",
                "\"digit \\123 is\"",
        };

        String[] invalidChars = {"{", "1"};

        for (int i = 0; i < testTexts.length; i++) {
            SmartScriptLexer lexer = new SmartScriptLexer(testTexts[i]);

            lexer.setState(SmartScriptLexerState.TAG);

            Exception exc = assertThrows(SmartScriptLexerException.class, lexer::nextToken);
            assertEquals("Invalid escaped character \"" + invalidChars[i] + "\".", exc.getMessage());
        }
    }

    @Test
    public void testTagStateNumberString() {
        SmartScriptLexer lexer = new SmartScriptLexer("String \"-123.0\" Double -123.0 $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "String"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "-123.0"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "Double"),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, -123.0),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null),
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTagQuoteNeverClosed() {
        SmartScriptLexer lexer = new SmartScriptLexer("String: \"-123.0 $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "String"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, ":"),
        };
        checkTokenStream(lexer, expectedTagStateTokens);

        Exception exc = assertThrows(SmartScriptLexerException.class, lexer::nextToken);
        assertEquals("Quotation was never closed.", exc.getMessage());
    }

    @Test
    public void testTagStateEscapingOutsideOfString() {
        SmartScriptLexer lexer = new SmartScriptLexer("Symbol @and number \\1 text");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "Symbol"),
                new SmartScriptToken(SmartScriptTokenType.FUNCTION_NAME, "and"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "number"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "\\"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "text"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null),
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTextAndTagState() {
        SmartScriptLexer lexer = new SmartScriptLexer("Janko \t\r @cos {$3;   \n  @sin Jasmina! 5.32m - -24$}");

        lexer.setState(SmartScriptLexerState.TEXT);
        assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "Janko \t\r @cos "), lexer.nextToken());
        assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG_START, null), lexer.nextToken());

        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 3),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, ";"),
                new SmartScriptToken(SmartScriptTokenType.FUNCTION_NAME, "sin"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "Jasmina"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "!"),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, 5.32),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "m"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "-"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, -24),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null),
        };
        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTagStateNumbersCombined() {
        SmartScriptLexer lexer = new SmartScriptLexer("  -1234. \r\n\t .56.78.34   -0.12 -1$}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.INTEGER, -1234),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "."),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "."),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, 56.78),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "."),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 34),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, -0.12),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, -1),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTagStateTextWithManyTokens() {
        SmartScriptLexer lexer = new SmartScriptLexer("  ab \"23.\\\\cd \n\t@f0 \\\\a\"b \n\t -2.34 @f1   $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "ab"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "23.\\cd \n\t@f0 \\a"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "b"),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, -2.34),
                new SmartScriptToken(SmartScriptTokenType.FUNCTION_NAME, "f1"),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTextStateSymbols() {
        SmartScriptLexer lexer = new SmartScriptLexer("  -.?$} \r\n\t ##   \\{${$");

        lexer.setState(SmartScriptLexerState.TEXT);

        checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "  -.?$} \r\n\t ##   {$"));
        checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG_START, null));
        checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
    }

    @Test
    public void testTagStateSymbols() {
        SmartScriptLexer lexer = new SmartScriptLexer("  -.?} \r\n\t ##   \\{$$}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "-"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "."),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "?"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "}"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "#"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "#"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "\\"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "{"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "$"),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTextAndTagStateEscapings() {
        SmartScriptLexer lexer = new SmartScriptLexer(
                "A tag follows {$= \"Joe \\\"Long\\\" Smith\" \"Some \\\\ test X\" $}.");

        lexer.setState(SmartScriptLexerState.TEXT);
        assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "A tag follows "), lexer.nextToken());
        assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG_START, null), lexer.nextToken());

        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
                new SmartScriptToken(SmartScriptTokenType.STRING, "Joe \"Long\" Smith"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "Some \\ test X"),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
        };
        checkTokenStream(lexer, expectedTagStateTokens);

        lexer.setState(SmartScriptLexerState.TEXT);
        assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "."), lexer.nextToken());
        assertEquals(new SmartScriptToken(SmartScriptTokenType.EOF, null), lexer.nextToken());
    }

    @Test
    public void testNotVariableNames() {
        // Tests that _a21, 32, 3s_ee are not parsed into single VARIABLE_NAME tokens
        SmartScriptLexer lexer = new SmartScriptLexer("_a21 32 3s_ee");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "_"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "a21"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 32),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 3),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "s_ee"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testNotFunctionNames() {
        // Tests that @7_abc, @_func, @32 are not parsed into single FUNCTION_NAME tokens
        SmartScriptLexer lexer = new SmartScriptLexer("@7_abc @_func @32");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "@"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 7),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "_"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "abc"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "@"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "_"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE_NAME, "func"),
                new SmartScriptToken(SmartScriptTokenType.SYMBOL, "@"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 32),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    // Helper method for checking if lexer generates the same stream of tokens
    // as the given stream.
    private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
        int counter = 0;
        for(SmartScriptToken expected : correctData) {
            SmartScriptToken actual = lexer.nextToken();
            String msg = "Checking token "+counter + ":";
            assertEquals(expected.getType(), actual.getType(), msg);
            assertEquals(expected.getValue(), actual.getValue(), msg);
            counter++;
        }
    }

	private void checkToken(SmartScriptToken actual, SmartScriptToken expected) {
			String msg = "SmartScriptToken are not equal.";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
	}

}

