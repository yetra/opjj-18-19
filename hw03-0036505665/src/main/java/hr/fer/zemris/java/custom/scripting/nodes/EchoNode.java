package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;

import java.util.Arrays;
import java.util.Objects;

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
        Objects.requireNonNull(elements);

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Element element : elements) {
            sb.append(element.asText()).append(" ");
        }

        return sb.toString();
    }
}
