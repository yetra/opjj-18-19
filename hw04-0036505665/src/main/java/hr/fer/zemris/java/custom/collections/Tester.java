package hr.fer.zemris.java.custom.collections;

/**
 * A functional interface which models objects that can test whether a given object is
 * acceptable or not.
 *
 * @param <T> the type of the input to test
 *
 * @author Bruna Dujmović
 *
 */
@FunctionalInterface
public interface Tester<T> {

    /**
     * Returns {@code true} if the given object is acceptable.
     *
     * @param obj the object to test
     * @return {@code true} if the given object is acceptable
     */
    boolean test(T obj);
}
