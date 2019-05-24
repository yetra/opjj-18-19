package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The base class for all graph nodes.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class Node {

    /**
     * A collection of all child nodes of the current node.
     */
    private List<Node> nodes;

    /**
     * Adds a given child node to an internally managed collection of children.
     *
     * @param child the child node to add
     * @throws NullPointerException if the given child node is {@code null}
     */
    public void addChildNode(Node child) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }

        nodes.add(Objects.requireNonNull(child));
    }

    /**
     * Returns the number of (direct) children on this node.
     *
     * @return the number of (direct) children on this node
     */
    public int numberOfChildren() {
        return nodes.size();
    }

    /**
     * Returns the child node that is stored on the specified index.
     *
     * @return the child node that is stored on the specified index
     * @throws IndexOutOfBoundsException if the index is not in the correct range
     */
    public Node getChild(int index) {
        return nodes.get(index);
    }

    /**
     * Applies the operation specified in the given visitor to this node.
     *
     * @param visitor the visitor that specifies the operation to apply
     */
    public abstract void accept(INodeVisitor visitor);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0, end = numberOfChildren(); i < end; i++) {
            sb.append(getChild(i).toString());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(nodes.toString(), node.nodes.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }
}
