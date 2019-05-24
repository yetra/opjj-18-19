package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * A node that represents a piece of textual data.
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
     * @throws NullPointerException if the given text is {@code null}
     */
    public TextNode(String text) {
        this.text = Objects.requireNonNull(text);
    }

    /**
     * Returns the textual data that this text node represents.
     *
     * @return the textual data that this text node represents
     */
    public String getText() {
        return text;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitTextNode(this);
    }

    @Override
    public String toString() {
        String escapedBackslash = text.replace("\\", "\\\\");
        return escapedBackslash.replace("{", "\\{");
    }
}
