package hr.fer.zemris.java.custom.collections;

import java.util.Objects;
import hr.fer.zemris.java.custom.collections.demo.EvenIntegerTester;

/**
 * This class is a general representation of a collection of objects.
 * It should be inherited by more specific implementations of collections.
 *
 * @author Bruna Dujmović
 *
 */
public interface Collection {

    /**
     * Returns {@code true} if this collection contains no elements.
     *
     * @return {@code true} if this collection contains no elements
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of currently stored elements in this collection.
     *
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Adds a given object to this collection.
     *
     * @param value value of object to add
     */
    void add(Object value);

    /**
     * Returns {@code true} if this collection contains a given element, as determined
     * by the {@code equals} method.
     *
     * @param value value of element to check for
     * @return {@code true} if this collection contains a given element
     */
    boolean contains(Object value);

    /**
     * Returns {@code true} if this collection contains a given element and removes
     * one occurrence of it. In this class, it is not specified which occurrence will
     * be removed.
     *
     * @param value value of element to remove
     * @return {@code true} if this collection contained a given element and one
     *         occurrence of it was removed
     */
    boolean remove(Object value);

    /**
     * Allocates a new array of size equal to the size of this collection, fills it
     * with this collection's content and returns the array. This method never
     * returns {@code null}.
     *
     * @return the newly allocated and filled array
     * @throws UnsupportedOperationException if the {@code toArray} method is
     *         not supported by this collection
     */
    Object[] toArray();

    /**
     * Iterates over each element of this collection and calls the
     * {@code processor.process} method for each element.
     *
     * @param processor the processor whose {@code process} method will be called
     *                  for each element
     * @throws NullPointerException if the specified processor is {@code null}
     */
    default void forEach(Processor processor) {
        Objects.requireNonNull(processor, "Processor cannot be null.");

        ElementsGetter getter = this.createElementsGetter();

        getter.processRemaining(processor);
    }

    /**
     * Adds all elements from a given collection into this collection. The given
     * collection remains unchanged.
     *
     * @param other the collection whose elements will be added into this
     *              collection
     */
    default void addAll(Collection other) {

        /**
         * An implementation of the {@code Processor} generic class which can add an
         * object to a given collection.
         */
        class AddToCollectionProcessor implements Processor {
            /**
             * Processes a given object so that it is added to the collection.
             *
             * @param value value of the object to process
             */
            @Override
            public void process(Object value) {
                Collection.this.add(value);
            }
        }

        other.forEach(new AddToCollectionProcessor());
    }

    /**
     * Removes all elements from this collection.
     */
    void clear();

    /**
     * Creates and returns an {@code ElementsGetter} object.
     *
     * @return an {@code ElementsGetter} object
     */
    ElementsGetter createElementsGetter();

    /**
     * Adds all elements of the {@code col} collection which satisfy the given tester
     * to this collection.
     *
     * @param col the collection whose elements will be added to this collection
     * @param tester the tester that checks if the elements are acceptable
     * @throws NullPointerException if {@code col} or {@code tester} are {@code null}
     */
    default void addAllSatisfying(Collection col, Tester tester) {
        Objects.requireNonNull(col);
        Objects.requireNonNull(tester);

        ElementsGetter getter = col.createElementsGetter();

        getter.processRemaining((value) -> {
            if (tester.test(value)) {
                this.add(value);
            }
        });
    }
}
