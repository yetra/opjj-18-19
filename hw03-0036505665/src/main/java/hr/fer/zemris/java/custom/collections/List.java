package hr.fer.zemris.java.custom.collections;

public interface List extends Collection {

    /**
     * Returns the element that is at the specified index in this collection.
     *
     * @param index the index of the element to return
     * @throws IndexOutOfBoundsException if the position is not in the correct range
     */
    Object get(int index);

    /**
     * Inserts the specified element at the specified position in this collection.
     * This method does not overwrite the current element at {@code position}, but
     * shifts it and any subsequent elements to the right.
     *
     * @param value the element to be inserted
     * @param position the index at which the specified element is to be inserted
     * @throws IndexOutOfBoundsException if the specified position is not in the
     *         correct range
     * @throws NullPointerException if the specified element is {@code null}
     */
    void insert(Object value, int position);

    /**
     * Returns the index of the first occurrence of the given element or -1 if
     * the value is not found in this collection.
     *
     * The average complexity of this method is n.
     *
     * @param value the element whose index is to be returned
     * @return the index of the first occurrence of the given element or -1 if
     *         the value is not found
     * @throws NullPointerException if the specified element is {@code null}
     */
    int indexOf(Object value);

    /**
     * Removes the element at the specified index from this collection.
     *
     * @param index the index of the element that is to be removed
     * @throws IndexOutOfBoundsException if the index is not in the correct range
     */
    void remove(int index);
}
