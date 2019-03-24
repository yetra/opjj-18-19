package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.ArrayIndexedCollection;

/**
 * The base class for all graph nodes.
 *
 * @author Bruna Dujmović
 *
 */
public class Node {

    /**
     * A collection of all child nodes of the current node.
     */
    private ArrayIndexedCollection nodes;

    /**
     * Adds a given child node to an internally managed collection of children.
     *
     * @param child the child node to add
     * @throws NullPointerException if the given child node is {@code null}
     */
    public void addChildNode(Node child) {
        if (nodes == null) {
            nodes = new ArrayIndexedCollection();
        }

        nodes.add(child);
    }

    /**
     * Returns the number of (direct) children on this node.
     *
     * @return the number of (direct) children on this node
     */
    public int numberOfChildred() {
        return nodes.size();
    }

    /**
     * Returns the child node that is stored on the specified index.
     *
     * @return the child node that is stored on the specified index
     * @throws IndexOutOfBoundsException if the index is not in the correct range
     */
    public Node getChild(int index) {
        return (Node) nodes.get(index);
    }
}
