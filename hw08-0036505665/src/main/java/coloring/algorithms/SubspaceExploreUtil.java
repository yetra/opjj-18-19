package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Utility class that contains the functionality for subspace exploration.
 *
 * @author Bruna Dujmović
 *
 */
public class SubspaceExploreUtil {

    /**
     * Explores a subspace defined as the tuple (s0, process(s), succ(s), acceptable(s))
     * using a breadth-first search algorithm.
     *
     * @param s0 the initial state
     * @param process a {@link Consumer} that processes a given state
     * @param succ a {@link Function} that returns a list of neighboring states for a
     *             given state
     * @param acceptable a {@link Predicate} that returns {@code false} if a given state
     *                   is no longer part of the observed subspace
     * @param <S> the type of the state
     */
    public static <S> void bfs(Supplier<S> s0, Consumer<S> process,
                               Function<S, List<S>> succ, Predicate<S> acceptable) {
        List<S> toExplore = new LinkedList<>();
        toExplore.add(s0.get());

        while (!toExplore.isEmpty()) {
            S state = toExplore.remove(0);

            if (acceptable.test(state)) {
                process.accept(state);
                toExplore.addAll(toExplore.size(), succ.apply(state));
            }
        }
    }

    /**
     * Explores a subspace defined as the tuple (s0, process(s), succ(s), acceptable(s))
     * using an optimized breadth-first search algorithm.
     *
     * This implementation keeps track of already visited states and removes them from
     * the returned list of neighbors before expanding the list of states to explore.
     *
     * @param s0 the initial state
     * @param process a {@link Consumer} that processes a given state
     * @param succ a {@link Function} that returns a list of neighboring states for a
     *             given state
     * @param acceptable a {@link Predicate} that returns {@code false} if a given state
     *                   is no longer part of the observed subspace
     * @param <S> the type of the state
     */
    public static <S> void bfsv(Supplier<S> s0, Consumer<S> process,
                                Function<S,List<S>> succ, Predicate<S> acceptable) {
        Set<S> visited = new HashSet<>();
        List<S> toExplore = new LinkedList<>();

        visited.add(s0.get());
        toExplore.add(s0.get());

        while (!toExplore.isEmpty()) {
            S state = toExplore.remove(0);

            if (acceptable.test(state)) {
                process.accept(state);

                succ.apply(state).forEach((neighbor) -> {
                    if (visited.add(neighbor)) {
                        toExplore.add(neighbor);
                    }
                });
            }
        }
    }

    /**
     * Explores a subspace defined as the tuple (s0, process(s), succ(s), acceptable(s))
     * using a depth-first search algorithm.
     *
     * @param s0 the initial state
     * @param process a {@link Consumer} that processes a given state
     * @param succ a {@link Function} that returns a list of neighboring states for a
     *             given state
     * @param acceptable a {@link Predicate} that returns {@code false} if a given state
     *                   is no longer part of the observed subspace
     * @param <S> the type of the state
     */
    public static <S> void dfs(Supplier<S> s0, Consumer<S> process,
                               Function<S, List<S>> succ, Predicate<S> acceptable) {
        List<S> toExplore = new LinkedList<>();
        toExplore.add(s0.get());

        while (!toExplore.isEmpty()) {
            S state = toExplore.remove(0);

            if (acceptable.test(state)) {
                process.accept(state);
                toExplore.addAll(0, succ.apply(state));
            }
        }
    }

}
