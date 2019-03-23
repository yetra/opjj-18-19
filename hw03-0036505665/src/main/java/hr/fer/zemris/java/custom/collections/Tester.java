package hr.fer.zemris.java.custom.collections;

/**
 * An interface which models objects that can test whether a given object is acceptable
 * or not.
 *
 * @author Bruna Dujmović
 *
 */
public interface Tester {

    /**
     * Returns {@code true} if the given object is acceptable.
     *
     * @param obj the object to test
     * @return {@code true} if the given object is acceptable
     */
    boolean test(Object obj);
}
