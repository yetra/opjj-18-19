package hr.fer.zemris.java.custom.collections;

/**
 * This class is a general representation of a collection of objects.
 * It should be inherited by more specific implementations of collections.
 *
 * @author Bruna Dujmović
 *
 */
public class Collection {

    /**
     * Sole constructor for the {@code Collection} class.
     */
    protected Collection() {
    }

    /**
     * Returns {@code true} if this collection contains no elements.
     *
     * @return {@code true} if this collection contains no elements
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of currently stored elements in this collection.
     *
     * @return the number of elements in this collection
     */
    public int size() {
        return 0;
    }

    /**
     * Adds a given object to this collection.
     *
     * @param value value of object to add
     */
    public void add(Object value) {
    }

    /**
     * Returns {@code true} if this collection contains a given element, as determined
     * by the {@code equals} method.
     *
     * @param value value of element to check for
     * @return {@code true} if this collection contains a given element
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Returns {@code true} if this collection contains a given element and removes
     * one occurrence of it. In this class, it is not specified which occurrence will
     * be removed.
     *
     * @param value value of element to remove
     * @return {@code true} if this collection contained a given element and one
     *         occurrence of it was removed
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Allocates a new array of size equal to the size of this collection, fills it
     * with this collection's content and returns the array. This method never
     * returns {@code null}.
     *
     * @return the newly allocated and filled array
     * @throws UnsupportedOperationException if the {@code toArray} method is
     *         not supported by this collection
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Iterates over this collection and calls the {@code processor.process} method
     * for each element. The order in which elements will be sent is undefined in
     * this class.
     *
     * @param processor the processor whose {@code process} method will be called
     *                  for each element
     */
    public void forEach(Processor processor) {
    }

    /**
     * Adds all elements from a given collection into this collection. The given
     * collection remains unchanged.
     *
     * @param other the collection whose elements will be added into this
     *              collection
     */
    public void addAll(Collection other) {

        /**
         * An implementation of the {@code Processor} generic class which can add an
         * object to a given collection.
         */
        class AddToCollectionProcessor extends Processor {
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
    public void clear() {
    }

}
