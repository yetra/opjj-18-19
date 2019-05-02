package searching.algorithms;

/**
 * This class models an ordered pair (s_j,c_j) of a given state and cost.
 *
 * @param <S> the type of the state
 */
public class Transition<S> {

    /**
     * The state of this {@link Transition}.
     */
    private S state;

    /**
     * The cost of this {@link Transition}.
     */
    private double cost;

    /**
     * Constructs a {@link Transition} object representing an ordered pair of the
     * given a state and cost.
     *
     * @param state the state of this {@link Transition}
     * @param cost the cost of this {@link Transition}
     */
    public Transition(S state, double cost) {
        this.state = state;
        this.cost = cost;
    }

    /**
     * Returns the state of this {@link Transition}.
     *
     * @return the state of this {@link Transition}
     */
    public S getState() {
        return state;
    }

    /**
     * Returns the cost of this {@link Transition}.
     *
     * @return the cost of this {@link Transition}
     */
    public double getCost() {
        return cost;
    }
}
