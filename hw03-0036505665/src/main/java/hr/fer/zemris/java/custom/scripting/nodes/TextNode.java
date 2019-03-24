package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node that represents a piece of textual data.
 * It inherits from the {@code Node} class.
 *
 * @author Bruna Dujmović
 *
 */
public class TextNode extends Node {

    /**
     * The textual data that the text node represents.
     */
    private String text;

    /**
     * Constructs a text node based on the textual data that it represents.
     *
     * @param text the textual data that the text node represents
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * Returns the textual data that this text node represents.
     *
     * @return the textual data that this text node represents
     */
    public String getText() {
        return text;
    }
}
