package searching.algorithms;

/**
 * This case represents a search tree node. It contains a reference to its parent, the
 * state that it is in, and the cost of reaching that state.
 *
 * @param <S> the type of the state
 *
 * @author Bruna Dujmović
 */
public class Node<S> {

    /**
     * The parent node of this node.
     */
    private Node<S> parent;

    /**
     * The state that this node is in.
     */
    private S state;

    /**
     * The cost of reaching the state contained in this node.
     */
    private double cost;

    /**
     * Constructs a {@link Node} object given a parent node, state, and cost of reaching
     * that state.
     *
     * @param parent the parent node of this node
     * @param state the state that this node is in
     * @param cost the cost of reaching the state contained in this node
     */
    public Node(Node<S> parent, S state, double cost) {
        this.parent = parent;
        this.state = state;
        this.cost = cost;
    }

    /**
     * Returns the parent node of this node.
     *
     * @return the parent node of this node
     */
    public Node<S> getParent() {
        return parent;
    }

    /**
     * Returns the state that this node is in.
     *
     * @return the state that this node is in
     */
    public S getState() {
        return state;
    }

    /**
     * Returns the cost of reaching the state contained in this node.
     *
     * @return the cost of reaching the state contained in this node.
     */
    public double getCost() {
        return cost;
    }
}
