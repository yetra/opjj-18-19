package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class is an implementation of an indexed, linked list-backed collection
 * of objects.
 *
 * Duplicate elements are allowed. The storage of {@code null} references is not
 * allowed.
 *
 * @author Bruna Dujmović
 *
 */
public class LinkedListIndexedCollection implements Collection {

    /**
     * The {@code ListNode} class represents a node in this collection.
     */
    private static class ListNode {
        /**
         * A reference to the next node in this collection.
         */
        ListNode next;

        /**
         * A reference to the previous node in this collection.
         */
        ListNode previous;

        /**
         * The value of this node.
         */
        Object value;
    }

    /**
     * The current size of this collection (the number of elements actually stored
     * in the {@code elements} array).
     */
    private int size;

    /**
     * A reference to the first node in this collection.
     */
    private ListNode first;

    /**
     * A reference to the last node in this collection.
     */
    private ListNode last;

    /**
     * Constructs an empty {@code LinkedListIndexedCollection} collection.
     * This collection's {@code size} is set to zero, and {@code first = last = null}
     * (the variables are initialized by default).
     */
    public LinkedListIndexedCollection() {
    }

    /**
     * Constructs a {@code LinkedListIndexedCollection} collection
     * which contains the elements of the specified collection.
     *
     * @param collection the collection whose elements will be copied into this collection
     * @throws NullPointerException if the specified collection is null
     */
    public LinkedListIndexedCollection(Collection collection) {
        Objects.requireNonNull(collection, "Collection parameter cannot be null.");

        this.addAll(collection);
        size = collection.size();
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Adds the given object to the end of this collection. The newly added element
     * becomes the element at the biggest index.
     *
     * The average complexity of this method is 1.
     *
     * @param value the object to add to this collection
     * @throws NullPointerException if the given object is {@code null}
     */
    @Override
    public void add(Object value) {
        Objects.requireNonNull(value, "Value parameter cannot be null.");

        ListNode node = new ListNode();
        node.value = value;

        if (first == null) {
            first = node;
        } else {
            node.previous = last;
            last.next = node;
        }

        node.next = null;
        last = node;

        size++;
    }

    @Override
    public boolean contains(Object value) {
        Objects.requireNonNull(value, "Value parameter cannot be null.");

        for (ListNode node = first; node != null; node = node.next) {
            if (node.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object value) {
        Objects.requireNonNull(value, "Value parameter cannot be null.");

        for (ListNode node = first; node != null; node = node.next) {
            if (node.value.equals(value)) {
                node.previous.next = node.next;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] listArray = new Object[size];

        ListNode node = first;
        for (int i = 0; i < size; i++) {
            listArray[i] = node.value;
            node = node.next;
        }

        return listArray;
    }

    /**
     * Iterates over each element of this collection in the order in which they are
     * stored in the {@code elements} array and calls the {@code processor.process}
     * method for each element.
     *
     * @param processor {@inheritDoc}
     * @throws NullPointerException if the specified processor is {@code null}
     */
    @Override
    public void forEach(Processor processor) {
        Objects.requireNonNull(processor, "Processor cannot be null.");

        for (ListNode node = first; node != null; node = node.next) {
            processor.process(node.value);
        }
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    /**
     * Returns the element at the specified position in this collection.
     *
     * The complexity of this method is not greater than n/2+1.
     *
     * @param index the index of the element to return
     * @throws IndexOutOfBoundsException if the index is not in range
     *         [0, {@code size}-1]
     */
    public Object get(int index) {
        if (index < 0 || index > size-1) {
            throw new IndexOutOfBoundsException(
                    "The given index is not in range [0, " + size + "-1].");
        }

        ListNode node;

        if (index < size/2) {
            node = first;
            for (int i = 0; i != index; i++) {
                node = node.next;
            }

        } else {
            node = last;
            for (int i = size-1; i != index; i--) {
                node = node.previous;
            }
        }

        return node.value;
    }

    /**
     * Inserts the specified element at the specified position in this collection.
     * This method does not overwrite the current element at {@code position}, but
     * shifts it and any subsequent elements to the right.
     *
     * The average complexity of this method is n.
     *
     * @param value the element to be inserted
     * @param position the index at which the specified element is to be inserted
     * @throws IndexOutOfBoundsException if the specified position is not in range
     *         [0, {@code size}]
     * @throws NullPointerException if the specified element is {@code null}
     */
    public void insert(Object value, int position) {
        Objects.requireNonNull(value, "Value parameter cannot be null.");
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException(
                    "The given position is not in range [0, " + size + "].");
        }

        if (position == size) {
            add(value);

        } else {
            ListNode node = first;
            for (int i = 0; i < size; i++) {
                if (i == position) {
                    ListNode newNode = new ListNode();
                    newNode.value = value;

                    newNode.previous = node.previous;
                    newNode.next = node;

                    node.previous.next = newNode;
                    node.previous = node;

                    size++;
                    break;
                }
                node = node.next;
            }
        }
    }

    /**
     * Returns the index of the first occurrence of the given element or -1 if the
     * value is not found in this collection.
     *
     * The average complexity of this method is n.
     *
     * @param value the element whose index is to be returned
     * @return the index of the first occurrence of the given element or -1 if the
     *         value is not found
     * @throws NullPointerException if the specified element is {@code null}
     */
    public int indexOf(Object value) {
        Objects.requireNonNull(value, "Value parameter cannot be null.");

        ListNode node = first;
        for (int i = 0; i < size; i++) {
            if (node.value.equals(value)) {
                return i;
            }
            node = node.next;
        }

        return -1;
    }

    /**
     * Removes the element at the specified index from this collection.
     *
     * @param index the index of the element that is to be removed
     * @throws IndexOutOfBoundsException if the specified index is not in range
     *         [0, {@code size}-1]
     */
    public void remove(int index) {
        if (index < 0 || index > size-1) {
            throw new IndexOutOfBoundsException(
                    "The given index is not in range [0, " + size + "-1].");
        }

        ListNode node = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                node.previous.next = node.next;
                size--;
                break;
            }
            node = node.next;
        }
    }
}
