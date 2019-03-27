package hr.fer.zemris.java.custom.scripting.SmartScriptLexerTest;

import static org.junit.jupiter.api.Assertions.*;

import hr.fer.zemris.java.custom.scripting.lexer.*;
import org.junit.jupiter.api.Disabled;
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
            assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
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
    public void testNoContenClosingTag() {
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

        String[] invalidCars = {"\"", "}", "1"};

        for (int i = 0; i < testTexts.length; i++) {
            SmartScriptLexer lexer = new SmartScriptLexer(testTexts[i]);

            lexer.setState(SmartScriptLexerState.TEXT);

            Exception exc = assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
            assertEquals("Invalid escaped character \"" + invalidCars[i] + "\".", exc.getMessage());
        }
    }

    @Test
    public void testTextStateEscapingEnding() {
        SmartScriptLexer lexer = new SmartScriptLexer(
                "Can't  \t\t escape     end of text \\");

        lexer.setState(SmartScriptLexerState.TEXT);

        Exception exc = assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
        assertEquals("Reached end of data, nothing to escape.", exc.getMessage());
    }

    @Test
    public void testTagStateEscapingPositiveCase() {
        SmartScriptLexer lexer = new SmartScriptLexer("This \\\\is    \\\"correct\\\" escaping! $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.STRING, "This"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\\is"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\"correct\""),
                new SmartScriptToken(SmartScriptTokenType.STRING, "escaping!"),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTagStateNumberString() {
        SmartScriptLexer lexer = new SmartScriptLexer("String: \\\"-123.0\\\" \\\\Double: -123.0 $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.STRING, "String:"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\"-123.0\""),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\\Double:"),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, -123.0),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
        };

        checkTokenStream(lexer, expectedTagStateTokens);
    }

    @Test
    public void testTagInvalidQuotes() {
        SmartScriptLexer lexer = new SmartScriptLexer("String: \\\"-123.0 $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.STRING, "String:"),
        };

        checkTokenStream(lexer, expectedTagStateTokens);

        Exception exc = assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
        assertEquals("Quote never closed.", exc.getMessage());
    }

    @Test
    public void testTagStateEscapingDigit() {
        SmartScriptLexer lexer = new SmartScriptLexer("Throws \\1 exception $}");

        lexer.setState(SmartScriptLexerState.TAG);

        assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "Throws"),
                lexer.nextToken());

        Exception exc = assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
        assertEquals("Invalid escaped character \"1\".", exc.getMessage());
    }

    @Test
    public void testTextAndTagState() {
        SmartScriptLexer lexer = new SmartScriptLexer("Janko \t\r {$3!   \n   Jasmina 5.32; - -24$}");

        lexer.setState(SmartScriptLexerState.TEXT);

        assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "Janko \t\r "), lexer.nextToken());
        assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG_START, null), lexer.nextToken());

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] expectedTagStateTokens = {
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 3),
                new SmartScriptToken(SmartScriptTokenType.STRING, "!"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "Jasmina"),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, 5.32),
                new SmartScriptToken(SmartScriptTokenType.STRING, ";"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "-"),
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

        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.INTEGER, -1234),
                new SmartScriptToken(SmartScriptTokenType.STRING, "."),
                new SmartScriptToken(SmartScriptTokenType.STRING, "."),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, 56.78),
                new SmartScriptToken(SmartScriptTokenType.STRING, "."),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 34),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, -0.12),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, -1),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test
    public void testTagStateTextWithManyTokens() {
        SmartScriptLexer lexer = new SmartScriptLexer("  ab\\\\23.cd \\\\ab\\\\ \n\t -2.34\\\\1   $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken correctData[] = {
                new SmartScriptToken(SmartScriptTokenType.STRING, "ab\\"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 23),
                new SmartScriptToken(SmartScriptTokenType.STRING, ".cd"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\\ab\\"),
                new SmartScriptToken(SmartScriptTokenType.DECIMAL, -2.34),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\\"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
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
        SmartScriptLexer lexer = new SmartScriptLexer("  -.? \r\n\t ## {$   $}");

        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken[] correctDataTagState = {
                new SmartScriptToken(SmartScriptTokenType.STRING, "-.?"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "##"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "{$"),
                new SmartScriptToken(SmartScriptTokenType.TAG_END, null),
                new SmartScriptToken(SmartScriptTokenType.EOF, null),
        };

        checkTokenStream(lexer, correctDataTagState);
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

