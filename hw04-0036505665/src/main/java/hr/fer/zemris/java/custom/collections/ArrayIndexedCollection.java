package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class is an implementation of a resizable array-backed collection of
 * objects.
 *
 * Duplicate elements are allowed. The storage of {@code null} references is not
 * allowed.
 *
 * @param <E> the type of elements contained in the collection
 *
 * @author Bruna Dujmović
 *
 */
public class ArrayIndexedCollection<E> implements List<E> {

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * The current size of this collection (the number of elements actually stored in
     * the {@link #elements} array).
     */
    private int size;

    /**
     * An array of object references that form the elements of this collection.
     */
    private E[] elements;

    /**
     * A count of the modifications of this collection.
     */
    private long modificationCount = 0L;

    /**
     * Constructs an empty {@code ArrayIndexedCollection} collection of the specified
     * initial capacity.
     *
     * @param initialCapacity the initial capacity of this collection
     * @throws IllegalArgumentException if the specified initial capacity is less
     *         than 1
     */
    @SuppressWarnings("unchecked")
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException(
                    "Array capacity must not be less than 1.");
        }

        elements = (E[]) new Object[initialCapacity];
    }

    /**
     * Constructs an empty {@link ArrayIndexedCollection} collection with an initial
     * capacity of 16.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an {@link ArrayIndexedCollection} collection of specified initial
     * capacity which contains the elements
     * of the specified collection.
     *
     * If the given {@code initialCapacity} is smaller than the size of
     * {@code collection}, the size of {@code collection} will be used as this
     * collection's capacity.
     *
     * @param collection the collection whose elements will be copied into this
     *                   collection
     * @param initialCapacity the initial capacity of this collection
     * @throws NullPointerException if the specified collection is null
     * @throws IllegalArgumentException if the specified initial capacity is less
     *         than 1
     */
    @SuppressWarnings("unchecked")
    public ArrayIndexedCollection(Collection<E> collection, int initialCapacity) {
        this(initialCapacity);
        Objects.requireNonNull(collection, "Collection parameter cannot be null.");

        int capacity = Math.max(initialCapacity, collection.size());
        size = collection.size();
        elements = Arrays.copyOf((E[]) collection.toArray(), capacity);
    }

    /**
     * Constructs an {@link ArrayIndexedCollection} collection which contains the
     * elements of the specified collection.
     *
     * @param collection the collection whose elements will be copied into this
     *                   collection
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayIndexedCollection(Collection<E> collection) {
        this(collection, collection.size());
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Adds a given object into this collection into the first empty place.
     * If this collection is already full, this method will double its capacity.
     *
     * The average complexity of this method is 1.
     *
     * @param value the object to add to this collection
     * @throws NullPointerException if the given object is {@code null}
     */
    @Override
    public void add(E value) {
        Objects.requireNonNull(value, "Cannot add null element to this collection.");

        doubleCapacityIfNeeded();

        elements[size] = value;
        size++;
        modificationCount++;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    /**
     * Returns {@code true} if this collection contains a given element and removes
     * the first occurrence of it.
     *
     * @param value value of element to remove
     * @return {@code true} if this collection contained a given element and its
     *         first occurrence was removed
     * @throws NullPointerException if the specified element is {@code null}
     */
    @Override
    public boolean remove(Object value) {
        Objects.requireNonNull(value, "Value parameter cannot be null.");

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        modificationCount++;
    }

    /**
     * {@inheritDoc}
     * The average complexity of this method is 1.
     *
     * @throws IndexOutOfBoundsException if the index is not in range
     *         [0, {@link #size}-1]
     */
    @Override
    public E get(int index) {
        if (index < 0 || index > size-1) {
            throw new IndexOutOfBoundsException(
                    "The given index is not in range [0, " + (size-1) + "].");
        }

        return elements[index];
    }

    /**
     * {@inheritDoc}
     * The average complexity of this method is n.
     *
     * @throws IndexOutOfBoundsException if the specified position is not in range
     *         [0, {@link #size}]
     */
    @Override
    public void insert(E value, int position) {
        Objects.requireNonNull(value, "Value parameter cannot be null.");
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException(
                    "The given position is not in range [0, " + size + "].");
        }

        doubleCapacityIfNeeded();

        for (int i = size-1; i >= position; i--) {
            elements[i+1] = elements[i];
        }
        elements[position] = value;

        size++;
        modificationCount++;
    }

    /**
     * {@inheritDoc}
     * The average complexity of this method is n.
     */
    @Override
    public int indexOf(Object value) {
        Objects.requireNonNull(value, "Value parameter cannot be null.");

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IndexOutOfBoundsException if the specified index is not in range
     *         [0, {@link #size}-1]
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index > size-1) {
            throw new IndexOutOfBoundsException(
                    "The given index is not in range [0, " + (size-1) + "].");
        }

        for (int i = index; i < size-1; i++) {
            elements[i] = elements[i+1];
        }
        elements[size-1] = null;

        size--;
        modificationCount++;
    }

    /**
     * A helper method which doubles the capacity of this collection.
     */
    private void doubleCapacityIfNeeded() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, 2*elements.length);
        }
    }

    @Override
    public ElementsGetter<E> createElementsGetter() {
        return new ArrayElementsGetter(this);
    }

    /**
     * An implementation of the {@link ElementsGetter} interface for this collection.
     */
    private static class ArrayElementsGetter<E> implements ElementsGetter<E> {

        /**
         * The index of the currently observed element of the {@link #elements} array.
         */
        private int index;

        /**
         * A reference to the collection whose elements this getter will return.
         */
        private ArrayIndexedCollection<E> collection;

        /**
         * A count of the modifications of the collection at the time of this
         * {@link ArrayElementsGetter}'s creation.
         */
        private long savedModificationCount;

        /**
         * Sole constructor. Accepts a reference to the collection whose elements this
         * getter will return.
         *
         * @param collection a reference to the collection whose elements this getter
         *                    will return
         * @throws NullPointerException if the given collection is {@code null}
         */
        public ArrayElementsGetter(ArrayIndexedCollection<E> collection) {
            Objects.requireNonNull(collection);

            this.index = 0;
            this.collection = collection;
            this.savedModificationCount = collection.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            checkModifications();

            return index < collection.size;
        }

        @Override
        public E getNextElement() {
            checkModifications();

            if (!hasNextElement()) {
                throw new NoSuchElementException();
            }

            return collection.elements[index++];
        }

        @Override
        public void processRemaining(Processor<E> p) {
            checkModifications();

            while (hasNextElement()) {
                p.process(collection.elements[index]);
                index++;
            }
        }

        /**
         * A helper method which compares the {@link #collection}'s current
         * modification count with {@link #savedModificationCount}.
         *
         * @throws ConcurrentModificationException if the {@link #collection} has been
         *         modified after this getter's creation
         */
        private void checkModifications() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayIndexedCollection that = (ArrayIndexedCollection) o;
        return size == that.size &&
                Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }
}
