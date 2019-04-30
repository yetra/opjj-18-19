package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
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
     * Explores a subspace defined as the tuple (s0, process(s), succ(s), acceptable(s)).
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
            S state = toExplore.get(0);

            if (acceptable.test(state)) {
                process.accept(state);
                toExplore.addAll(toExplore.size(), succ.apply(state));
            }
        }
    }
}
