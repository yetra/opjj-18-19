package hr.fer.zemris.java.custom.collections;

/**
 * This is a functional interface that models an object capable of performing some
 * operation on a passed object.
 *
 * @author Bruna Dujmović
 *
 */
@FunctionalInterface
public interface Processor {

    /**
     * Processes (performs a specific action on) a given object.
     *
     * @param value value of the object to process
     */
    void process(Object value);
}
