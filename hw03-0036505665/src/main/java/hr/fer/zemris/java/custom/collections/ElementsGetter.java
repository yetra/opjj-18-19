package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * An interface which models an iterator over a collection's elements.
 *
 * @author Bruna Dujmović
 *
 */
public interface ElementsGetter {

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
    Object getNextElement();

    /**
     * Calls the {@code processor.process} method for each remaining element in the
     * collection.
     *
     * @param p the processor whose {@code process} method will be called
     *                  for each remaining element
     */
    void processRemaining(Processor p);
}
