package hr.fer.zemris.java.custom.collections;

/**
 * This is a functional interface that models an object capable of performing some
 * operation on a passed object.
 *
 * @param <T> the type of the value to process
 *
 * @author Bruna Dujmović
 *
 */
@FunctionalInterface
public interface Processor<T> {

    /**
     * Processes (performs a specific action on) a given object.
     *
     * @param value value of the object to process
     */
    void process(T value);
}
