package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node that represents a command which generates some textual output dynamically.
 * It inherits from the {@code Node} class.
 *
 * @author Bruna Dujmović
 *
 */
public class EchoNode extends Node {

    /**
     * The elements of the echo node.
     */
    private Element[] elements;

    /**
     * Constructs an echo node based on the elements that it contains.
     *
     * @param elements the elements of the echo node
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    /**
     * Returns the elements of this echo node.
     *
     * @return the elements of this echo node
     */
    public Element[] getElements() {
        return elements;
    }
}
