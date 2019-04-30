package searching.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A utility class of methods for state-space search.
 *
 * @author Bruna Dujmović
 */
public class SearchUtil {

    /**
     * Returns the solution represented by a {@link Node} object for a given problem
     * defined as the tuple (s0, succ(s), goal(s)) using a breadth-first search algorithm.
     *
     * @param s0 the initial state
     * @param succ a {@link Function} that returns a list of neighboring states for a
     *             given state
     * @param goal a {@link Predicate} that returns {@code false} if a given state
     *             does not match the goal state
     * @param <S> the type of the states
     * @return he solution represented by a {@link Node} object for a given problem
     */
    public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ,
                                  Predicate<S> goal) {
        List<Node<S>> toExplore = new LinkedList<>();
        toExplore.add(new Node<>(null, s0.get(), 0));
        Node<S> node = null;

        while (!toExplore.isEmpty()) {
            node = toExplore.remove(0);

            if (goal.test(node.getState())) {
                return node;
            }

            List<Transition<S>> transitions = succ.apply(node.getState());
            for (Transition<S> transition : transitions) {
                toExplore.add(new Node<>(
                        node, transition.getState(), node.getCost() + transition.getCost()
                ));
            }
        }

        return node;
    }
}
