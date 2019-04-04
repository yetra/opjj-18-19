package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SmartScriptParserTest {

    @Test
    public void testUnclosedForTag() {
        String documentBody = "asd sds \\\\\\\\ {$ FOR i sin 23 $} adad sdfasf";

        Exception ex = assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser(documentBody));
        assertEquals("Non-empty tag was never closed.", ex.getMessage());

    }

    @Test
    public void testOnlyEndTag() {
        String documentBody = "{$END$} adad sdfasf";

        Exception ex = assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser(documentBody));
        assertEquals("There are no non-empty tags to close.", ex.getMessage());
    }

    @Test
    public void testMultipleEndTags() {
        String documentBody = "{$ FOR m 0 3 2 $} text {$END$} {$ END $} 90";

        Exception ex = assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser(documentBody));
        assertEquals("There are no non-empty tags to close.", ex.getMessage());
    }

    @Test
    public void testOpenEndTag() {
        String documentBody = "{$ FOR m 0 3 2 $} text {$END ";

        Exception ex = assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser(documentBody));
        assertEquals("Invalid end tag.", ex.getMessage());
    }

    @Test
    public void testFunctionInForTag() {
        String[] documentBodies = {
                "t123 {$ FOR m @cos 3 2 $} text {$END $} text ",
                "{$= i$} {$ FOR m + 8 $} test {$END $} test "
        };

        for (String documentBody : documentBodies) {
            Exception ex = assertThrows(SmartScriptParserException.class,
                    () -> new SmartScriptParser(documentBody));
            assertEquals("For tag arguments cannot be operators or functions.",
                    ex.getMessage());
        }
    }

    @Test
    public void testWrongNumberOfForTagArgs() {
        String[] documentBodies = {
                " {$ FOR m aaa 3 2 12.3 $} {$END $} test ",
                "{$= i 23 sdfj we $} {$ FOR m 9.12 $} test {$END $}",
                "{$ FOR $} {$END $}",
                " {$ FOR kk 23 74.3 sf wer 12 ou $} {$END $} test ",
                "{$ FOR m 0 3 2 text ii $}"
        };

        int[] numberOfArgs = {5, 2, 0, 7, 6};
        int i = 0;

        for (String documentBody : documentBodies) {
            Exception ex = assertThrows(SmartScriptParserException.class,
                    () -> new SmartScriptParser(documentBody));
            assertEquals("Wrong number of for tag arguments: "
                            + numberOfArgs[i++] + ".", ex.getMessage());
        }
    }

    @Test
    public void testFirstForTagArgIsNotAVariable() {
        String[] documentBodies = {
                "{$ FOR @cos 3 2 $} text {$END $}",
                " test {$ FOR -123.23 8 12 $} test {$END $} test "
        };

        for (String documentBody : documentBodies) {
            Exception ex = assertThrows(SmartScriptParserException.class,
                    () -> new SmartScriptParser(documentBody));
            assertEquals("First for tag element is not a variable.",
                    ex.getMessage());
        }
    }

    @Test
    public void testOpenTags() {
        String[] documentBodies = {
                "teajdh {$= m 0 3 2 text",
                "{$= i$} {$= 238 -13.24 "
        };

        for (String documentBody : documentBodies) {
            Exception ex = assertThrows(SmartScriptParserException.class,
                    () -> new SmartScriptParser(documentBody));
            assertEquals("Invalid tag token type EOF.", ex.getMessage());
        }
    }

    @Test
    public void testParsedOutputPositiveCases() {
        String[] documentBodies = {
                "   test \t \\\\ aa  {$ \nFOR m  aaa \"-3 \t\" 2\n $} \n oweh {$END$} text ",
                "{$= i 23 ou we $} \r\r {$FOR m   9.12\t 9 $} \ntext23\n {$END $}",
                "this is \r\t some \\{$text$} \t {$ = i \n45 $}",
                " \n\r\t {$FOR  \n\r\t  o     23 -12.3 \"\\\"hi\\\"-0.4\"$} {$END $} \n\n ",
        };

        String[] expectedOutput = {
                "   test \t \\\\ aa  {$ FOR m aaa \"-3 \t\" 2 $} \n oweh {$ END $} text ",
                "{$= i 23 ou we $} \r\r {$ FOR m 9.12 9 $} \ntext23\n {$ END $}",
                "this is \r\t some \\{$text$} \t {$= i 45 $}",
                " \n\r\t {$ FOR o 23 -12.3 \"\\\"hi\\\"-0.4\" $} {$ END $} \n\n ",
        };

        for (int i = 0; i < documentBodies.length; i++) {
            SmartScriptParser parser = new SmartScriptParser(documentBodies[i]);
            assertEquals(expectedOutput[i], parser.getDocumentNode().toString());
        }
    }

    @Test
    public void testDocumentStructure() {
        String[] fileNames = {"document1.txt", "document2.txt", "document3.txt"};

        for (String fileName : fileNames) {
            String docBody = loader(fileName);
            SmartScriptParser parser = new SmartScriptParser(docBody);
            DocumentNode document = parser.getDocumentNode();

            String originalDocumentBody = document.toString();
            SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
            DocumentNode document2 = parser2.getDocumentNode();

            assertEquals(document, document2);
        }
    }

    private String loader(String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try(InputStream is =
                    this.getClass().getClassLoader().getResourceAsStream(filename)) {
            byte[] buffer = new byte[1024];
            while(true) {
                int read = is.read(buffer);
                if(read<1) break;
                bos.write(buffer, 0, read);
            }
            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        } catch(IOException ex) {
            return null;
        }
    }
}
