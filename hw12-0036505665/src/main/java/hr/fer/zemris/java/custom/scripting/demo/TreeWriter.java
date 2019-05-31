package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This demo program parses a specified file into a tree and reproduces its approximate
 * original form onto the standard output using the Visitor design pattern.
 *
 * @author Bruna Dujmović
 *
 */
public class TreeWriter {

    /**
     * The main method. Accepts a file path as a command-line argument, opens and
     * parses the specified file and prints its reproduced form to the standard output.
     *
     * @param args the command-line arguments, one is expected - the file path
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(
                    "One argument expected, " + args.length + " were given.");
            System.exit(1);
        }

        try {
            String docBody = Files.readString(Paths.get(args[0]));
            SmartScriptParser p = new SmartScriptParser(docBody);
            WriterVisitor visitor = new WriterVisitor();
            p.getDocumentNode().accept(visitor);

        } catch (IOException e) {
            System.out.println("Can't read from the given path!");
            System.exit(1);
        } catch(SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(1);
        }
    }

    /**
     * An {@link INodeVisitor} that prints the nodes to the standard output.
     */
    private static class WriterVisitor implements INodeVisitor {

        @Override
        public void visitTextNode(TextNode node) {
            System.out.println(node.toString());
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            System.out.println(node.toString());
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            System.out.println(node.toString());
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            System.out.println(node.toString());
        }
    }
}
