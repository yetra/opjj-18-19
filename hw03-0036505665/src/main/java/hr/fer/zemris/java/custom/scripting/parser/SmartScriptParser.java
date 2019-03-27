package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class models a document parser. The document is parsed into different
 * {@link Node} objects depending on the content. The main node of the given
 * document is {@link #documentNode}.
 *
 * @author Bruna Dujmović
 *
 */
public class SmartScriptParser {

    // minimum number of for tag arguments
    private static final int MIN_FOR_TAG_ARGS = 3;

    // maximum number of for tag arguments
    private static final int MAX_FOR_TAG_ARGS = 4;

    /**
     * The lexer that this parser uses.
     */
    private SmartScriptLexer lexer;

    /**
     * The main node of a given parsed text.
     */
    private DocumentNode documentNode;

    /**
     * The stack which contains all the nodes that were parsed from a given text.
     */
    private ObjectStack stack;

    /**
     * The number of currently open non-empty tags that were parsed from the document.
     */
    private int openNonEmptyTags;

    /**
     * Constructs a parser for a given text.
     *
     * @param documentBody the text to parse
     */
    public SmartScriptParser(String documentBody) {
        Objects.requireNonNull(documentBody);

        this.lexer = new SmartScriptLexer(documentBody);
        this.openNonEmptyTags = 0;

        this.stack = new ObjectStack();
        this.documentNode = new DocumentNode();
        this.stack.push((Node) documentNode);

        try {
            parseDocument();
        } catch (Exception ex) {
            throw new SmartScriptParserException(ex.getMessage());
        }
    }

    /**
     * Returns this parser's document node.
     *
     * @return this parser's document node
     */
    public DocumentNode getDocumentNode() {
        return documentNode;
    }

    /**
     * Parses a given document.
     *
     * @throws SmartScriptParserException if the document cannot be properly parsed
     */
    private void parseDocument() {
        SmartScriptToken token = lexer.nextToken();

        while (token.getType() != SmartScriptTokenType.EOF) {

            if (lexer.getState() == SmartScriptLexerState.TAG) {
                parseTag();

            } else {
                switch (token.getType()) {
                    case STRING:
                        String text = (String) token.getValue();
                        Node parent = (Node) stack.peek();
                        parent.addChildNode(new TextNode(text));
                        break;

                    case TAG_START:
                        lexer.setState(SmartScriptLexerState.TAG);
                        break;

                    default:
                        throw new SmartScriptParserException(
                                "Invalid document structure.");
                }
            }

            token = lexer.nextToken();
        }

        if (openNonEmptyTags != 0) {
            throw new SmartScriptParserException("Non-empty tag was never closed.");
        }
    }

    /* ------------------------------------------------------------------------------
     * ------------------------------- HELPER METHODS -------------------------------
     * ------------------------------------------------------------------------------
     */

    /**
     * Parses the content of a tag depending on its name.
     *
     * @throws SmartScriptParserException if the given tag name or tag content is
     *         invalid
     */
    private void parseTag() {
        SmartScriptToken token = lexer.getToken();

        if (token.getType() != SmartScriptTokenType.STRING) {
            throw new SmartScriptParserException("Invalid token type for tag start.");
        }

        String tagName = token.getValue().toString();
        if (!isValidTagName(tagName)) {
            throw new SmartScriptParserException("Invalid token tag name.");
        }

        switch (tagName) {
            case "FOR":
                parseForTag();
                break;
            case "=":
                parseEchoTag();
                break;
            case "END":
                if (openNonEmptyTags == 0) {
                    throw new SmartScriptParserException(
                            "There are no non-empty tags to close.");
                }
                parseEndTag();
                break;
            default:
                throw new SmartScriptParserException(
                        "Unknown tag name \"" + tagName + "\".");
        }

        lexer.setState(SmartScriptLexerState.TEXT);
    }

    /**
     * Parses a for tag and adds it to the stack.
     *
     * @throws SmartScriptParserException if the for tag is invalid
     */
    private void parseForTag() {
        openNonEmptyTags++;

        ArrayIndexedCollection children = new ArrayIndexedCollection();
        SmartScriptToken token = lexer.nextToken();

        while (token.getType() != SmartScriptTokenType.TAG_END) {
            Element element = parseTagToken(token);

            if (children.size() == 0 && !(element instanceof ElementVariable)) {
                throw new SmartScriptParserException(
                        "First for tag element is not a variable.");
            }
            if (element instanceof ElementOperator
                    || element instanceof ElementFunction) {
                throw new SmartScriptParserException(
                        "For tag arguments cannot be operators or functions.");
            }

            children.add(element);
            token = lexer.nextToken();
        }

        if (children.size() < MIN_FOR_TAG_ARGS || children.size() > MAX_FOR_TAG_ARGS) {
            throw new SmartScriptParserException(
                    "Wrong number of for tag arguments: " + children.size() + ".");
        }

        ForLoopNode forLoopNode = new ForLoopNode(
                (ElementVariable) children.get(0),
                (Element) children.get(1),
                (Element) children.get(2),
                children.size() == 4 ? (Element) children.get(3) : null
        );

        Node parent = (Node) stack.peek();
        parent.addChildNode(forLoopNode);
        stack.push(forLoopNode);
    }

    /**
     * Parses an echo tag and adds it to the stack.
     *
     * @throws SmartScriptParserException if the echo tag is invalid
     */
    private void parseEchoTag() {
        ArrayIndexedCollection children = new ArrayIndexedCollection();
        SmartScriptToken token = lexer.nextToken();

        while (token.getType() != SmartScriptTokenType.TAG_END) {
            children.add(parseTagToken(token));
            token = lexer.nextToken();
        }

        Element[] childrenArray = Arrays.copyOf(children.toArray(),
                children.size(), Element[].class);

        EchoNode echoNode = new EchoNode(childrenArray);
        Node parent = (Node) stack.peek();
        parent.addChildNode(echoNode);
    }

    /**
     * Parses an end tag.
     *
     * @throws SmartScriptParserException if the end tag is invalid or
     *         too many end tags were in the document
     */
    private void parseEndTag() {
        SmartScriptToken token = lexer.nextToken();

        if (token.getType() != SmartScriptTokenType.TAG_END) {
            throw new SmartScriptParserException("Invalid end tag.");
        }

        stack.pop();
        if (stack.isEmpty()) {
            throw new SmartScriptParserException("Too many end tags in document");
        }
        openNonEmptyTags--;
    }

    /**
     * Parses a given token into a tag element.
     *
     * @param token the token to parse
     * @return the parsed element
     * @throws SmartScriptParserException if the given token is of invalid type
     */
    private Element parseTagToken(SmartScriptToken token) {
        switch (token.getType()) {
            case STRING:
                String strValue = (String) token.getValue();

                if (isValidVariableName(strValue)) {
                    return new ElementVariable(strValue);
                } else if (isValidFunctionName(strValue)) {
                    return new ElementFunction(strValue.substring(1));
                } else if (isValidOperator(strValue)) {
                    return new ElementOperator(strValue);
                } else {
                     return new ElementString(strValue);
                }

            case INTEGER:
                int intValue = (int) token.getValue();
                return new ElementConstantInteger(intValue);

            case DECIMAL:
                double decimalValue = (double) token.getValue();
                return new ElementConstantDouble(decimalValue);

            default:
                throw new SmartScriptParserException(
                        "Invalid tag token type " + token.getType() + ".");
        }
    }

    /**
     * Returns {@code true} if the given text is a valid variable name.
     *
     * @param text the text to check
     * @return {@code true} if the given text is a valid variable name
     */
    private boolean isValidVariableName(String text) {
        return text.matches("^[A-Za-z]\\w*$");
    }

    /**
     * Returns {@code true} if the given text is a valid function name.
     *
     * @param text the text to check
     * @return {@code true} if the given text is a valid function name
     */
    private boolean isValidFunctionName(String text) {
        return text.matches("^@[A-Za-z]\\w*$");
    }

    /**
     * Returns {@code true} if the given text is a valid tag name.
     *
     * @param text the text to check
     * @return {@code true} if the given text is a valid tag name
     */
    private boolean isValidTagName(String text) {
        return lexer.getState() == SmartScriptLexerState.TAG
                && (text.equals("=") || isValidVariableName(text));
    }

    /**
     * Returns {@code true} if the given text is a valid operator.
     *
     * @param text the character to check
     * @return {@code true} if the given text is a valid operator
     */
    private boolean isValidOperator(String text) {
        return text.matches("^[+\\-*/^]$");
    }
}
