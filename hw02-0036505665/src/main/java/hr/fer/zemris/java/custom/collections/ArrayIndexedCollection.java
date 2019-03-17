package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class is an implementation of a resizable array-backed collection of objects.
 * Duplicate elements are allowed. Storage of {@code null} references is not allowed.
 *
 * @author Bruna Dujmović
 *
 */
public class ArrayIndexedCollection extends Collection {

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * The capacity (maximum number of elements that can be stored in {@code elements}) of this collection.
     */
    private static int capacity;

    /**
     * The current size of this collection (the number of elements actually stored in the {@code elements} array).
     */
    private int size;

    /**
     * An array of object references whose length is determined by the {@code capacity} variable.
     */
    private Object[] elements;

    /**
     * Constructs an empty {@code ArrayIndexedCollection} collection of the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of this collection
     * @throws IllegalArgumentException if the specified initial capacity is less than 1
     */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Array capacity must not be less than 1.");
        }
        capacity = initialCapacity;
        elements = new Object[capacity];
    }

    /**
     * Constructs an empty {@code ArrayIndexedCollection} collection with an initial capacity of 16.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an {@code ArrayIndexedCollection} collection which contains the elements of the specified collection.
     *
     * @param collection the collection whose elements will be copied into this collection
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayIndexedCollection(Collection collection) {
        Objects.requireNonNull(collection, "Collection must not be null.");
        capacity = collection.size();
        size = capacity;
        elements = collection.toArray();
    }

    /**
     * Constructs an {@code ArrayIndexedCollection} collection of specified initial capacity which contains the elements
     * of the specified collection.
     *
     * If the given {@code initialCapacity} is smaller than the size of {@code collection}, the size of {@code collection}
     * will be used as this collection's capacity.
     *
     * @param collection the collection whose elements will be copied into this collection
     * @param initialCapacity the initial capacity of this collection
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayIndexedCollection(Collection collection, int initialCapacity) {
        Objects.requireNonNull(collection, "Collection must not be null.");

        if (initialCapacity < collection.size()) {
            capacity = collection.size();
        } else {
            capacity = initialCapacity;
        }
        size = capacity;
        elements = collection.toArray();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    /**
     * Adds a given object into this collection into the first empty place.
     * If this collection is already full, {@code add} will double its capacity.
     *
     * @param value the object to add to this collection
     * @throws NullPointerException if the given object is null
     */
    public void add(Object value) {
        Objects.requireNonNull(value);

        if (size == capacity) {
            capacity *= 2;
            elements = Arrays.copyOf(elements, capacity);
        }

        for (int i = 0; i < capacity; i++) {
            if (elements[i] == null) {
                elements[i] = value;
            }
        }
    }

    /**
     * Returns the element at the specified position in this collection.
     *
     * @param index the index of the element to return
     * @throws IndexOutOfBoundsException if the index is not in range [0, {@code size}-1]
     */
    public Object get(int index) {
        if (index < 0 || index > size-1) {
            throw new IndexOutOfBoundsException("The given index is not in range [0, " + size + "-1].");
        }
        return elements[index];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Inserts the specified element at the specified position in this collection.
     * This method does not overwrite the current element at {@code position}, but shifts it
     * and any subsequent elements to the right.
     *
     * @param value the element to be inserted
     * @param position the index at which the specified element is to be inserted
     * @throws IndexOutOfBoundsException if the specified position is not in range [0, {@code size}]
     */
    public void insert(Object value, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("The given position is not in range [0, " + size + "].");
        }

        if (size == capacity) {
            capacity += 1;
            elements = Arrays.copyOf(elements, capacity);
        }

        for (int i = size-1; i >= position; i++) {
            elements[i+1] = elements[i];
        }

        elements[position] = value;
        size++;
    }

    /**
     * Returns the index of the first occurrence of the given element or -1 if the value is not found in this collection.
     *
     * @param value the element whose index is to be returned
     * @return the index of the first occurrence of the given element or -1 if the value is not found
     */
    public int indexOf(Object value) {
        if (value != null) {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Removes the element at the specified index from this collection.
     *
     * @param index the index of the element that is to be removed
     */
    public void remove(int index) {
        if (index < 0 || index > size-1) {
            throw new IndexOutOfBoundsException("The given index is not in range [0, " + size + "-1].");
        }

        for (int i = index; i < size-1; i++) {
            elements[i] = elements[i+1];
        }
        elements[size-1] = null;
        size--;
    }
}
