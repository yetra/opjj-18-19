package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * An interface which models an iterator over a collection's elements.
 *
 * @param <E> the type of elements returned by the {@link ElementsGetter}
 *
 * @author Bruna Dujmović
 *
 */
public interface ElementsGetter<E> {

    /**
     * Returns {@code true} if the collection has more elements
     *
     * @return {@code true} if the collection has more elements
     */
    boolean hasNextElement();

    /**
     * Returns the next object in the collection.
     *
     * @return the next object in the collection
     * @throws NoSuchElementException if no more elements exist in the collection
     */
    E getNextElement();

    /**
     * Calls the {@link Processor#process(Object)} method for each remaining element
     * in the collection.
     *
     * @param p the processor whose {@link Processor#process(Object)} method will be
     *          called for each remaining element
     */
    void processRemaining(Processor<E> p);
}
