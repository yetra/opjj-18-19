package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
     * @return the solution represented by a {@link Node} object for a given problem or
     *         {@code null} if no solution is found
     */
    public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ,
                                  Predicate<S> goal) {
        List<Node<S>> toExplore = new LinkedList<>();
        toExplore.add(new Node<>(null, s0.get(), 0));

        while (!toExplore.isEmpty()) {
            Node<S> node = toExplore.remove(0);

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

        return null;
    }

    /**
     * Returns the solution represented by a {@link Node} object for a given problem
     * defined as the tuple (s0, succ(s), goal(s)) using an optimized breadth-first
     * search algorithm.
     *
     * This implementation keeps track of already visited states and doesn't add them
     * again to the list of states to explore.
     *
     * @param s0 the initial state
     * @param succ a {@link Function} that returns a list of neighboring states for a
     *             given state
     * @param goal a {@link Predicate} that returns {@code false} if a given state
     *             does not match the goal state
     * @param <S> the type of the states
     * @return the solution represented by a {@link Node} object for a given problem or
     *         {@code null} if no solution is found
     */
    public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ,
                                  Predicate<S> goal) {
        Set<S> visited = new HashSet<>();
        List<Node<S>> toExplore = new LinkedList<>();

        toExplore.add(new Node<>(null, s0.get(), 0));
        visited.add(s0.get());

        while (!toExplore.isEmpty()) {
            Node<S> node = toExplore.remove(0);

            if (goal.test(node.getState())) {
                return node;
            }

            List<Transition<S>> transitions = succ.apply(node.getState());
            for (Transition<S> transition : transitions) {

                if (visited.add(transition.getState())) {
                    toExplore.add(new Node<>(node, transition.getState(),
                            node.getCost() + transition.getCost()));
                }
            }
        }

        return null;
    }
}
