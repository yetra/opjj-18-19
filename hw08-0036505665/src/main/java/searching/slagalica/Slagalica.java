package searching.slagalica;

import searching.algorithms.Transition;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class represents a {@link Slagalica} game.
 *
 * Given a 3x3 board of 8 tiles labeled with the values 1-9 and one empty space, the
 * goal is to reorder the tiles so that all the tile values are sorted from top left to
 * bottom right and the empty space is at the bottom right position.
 *
 * @author Bruna Dujmović
 */
public class Slagalica {

    /**
     * A map of all possible transitions from a given index of the configuration array.
     */
    private static final Map<Integer, List<Integer>> TRANSITION_FUNCTION;

    /**
     * The goal configuration of the game - all the tile values are sorted from top left
     * to bottom right and the empty space is at the bottom right position.
     */
    private static final KonfiguracijaSlagalice GOAL_CONFIGURATION;

    static {
        GOAL_CONFIGURATION = new KonfiguracijaSlagalice(
                new int[] {1, 2, 3, 4, 5, 6, 7, 8, 0});

        TRANSITION_FUNCTION = new HashMap<>();
        TRANSITION_FUNCTION.put(0, List.of(1, 3));
        TRANSITION_FUNCTION.put(1, List.of(0, 2, 4));
        TRANSITION_FUNCTION.put(2, List.of(1, 5));
        TRANSITION_FUNCTION.put(3, List.of(0, 4, 6));
        TRANSITION_FUNCTION.put(4, List.of(1, 3, 5, 7));
        TRANSITION_FUNCTION.put(5, List.of(2, 4, 8));
        TRANSITION_FUNCTION.put(6, List.of(3, 7));
        TRANSITION_FUNCTION.put(7, List.of(4, 6, 8));
        TRANSITION_FUNCTION.put(8, List.of(5, 7));
    }

    /**
     * The initial configuration of this game.
     */
    private KonfiguracijaSlagalice initialConfiguration;

    /**
     * A {@link Supplier} that returns the initial configuration of this game.
     */
    public final Supplier<KonfiguracijaSlagalice> s0 = () -> initialConfiguration;

    /**
     * A {@link Function} that returns a list of configuration-cost pairs that represent
     * the neighboring states of a given configuration.
     */
    public final Function<KonfiguracijaSlagalice,
            List<Transition<KonfiguracijaSlagalice>>> succ = (configuration) -> {

        List<Transition<KonfiguracijaSlagalice>> transitions = new LinkedList<>();

        int[] polje = configuration.getPolje();
        int indexOfSpace = configuration.indexOfSpace();

        TRANSITION_FUNCTION.get(indexOfSpace).forEach(
                (index) -> {
                    int[] nextPolje = Arrays.copyOf(polje, polje.length);
                    nextPolje[indexOfSpace] = polje[index];
                    nextPolje[index] = 0;

                    transitions.add(new Transition<>(
                            new KonfiguracijaSlagalice(nextPolje), 1
                    ));
        });

        return transitions;
    };

    /**
     * A {@link Predicate} that returns {@code true} if a given configuration matches
     * the goal configuration.
     */
    public final Predicate<KonfiguracijaSlagalice> goal =
            (configuration) -> configuration.equals(GOAL_CONFIGURATION);

    /**
     * Constructs a {@link Slagalica} game given an initial game configuration.
     *
     * @param initialConfiguration the initial configuration of this game
     */
    public Slagalica(KonfiguracijaSlagalice initialConfiguration) {
        this.initialConfiguration = Objects.requireNonNull(initialConfiguration);
    }
}
