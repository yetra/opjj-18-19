package hr.fer.zemris.java.custom.collections;

/**
 * This class models a stack of objects.
 *
 * Duplicate elements are allowed. The storage of {@code null} references is not
 * allowed.
 *
 * @author Bruna Dujmović
 *
 */
public class ObjectStack {

    /**c
     * The collection that forms the basis of the stack.
     */
    private ArrayIndexedCollection collection;

    /**
     * Constructs an empty stack.
     */
    public ObjectStack() {
        collection = new ArrayIndexedCollection();
    }

    /**
     * Returns {@code true} if this stack contains no elements.
     *
     * @return {@code true} if this stack contains no elements
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Returns the number of currently stored elements in this stack.
     *
     * @return the number of elements in this stack
     */
    public int size() {
        return collection.size();
    }

    /**
     * Adds a given element to the top of this stack.
     *
     * @param value the element to push to the top of the stack
     * @throws NullPointerException if the given element is {@code null}
     */
    public void push(Object value) {
        collection.add(value);
    }

    /**
     * Removes the last element that was pushed on this stack and returns it.
     *
     * @return the last element that was pushed on this stack
     * @throws EmptyStackException if this stack is empty when pop is called
     */
    public Object pop() {
        if (collection.isEmpty()) {
            throw new EmptyStackException("Cannot perform pop on an empty stack.");
        }

        Object popped = collection.get(collection.size()-1);
        collection.remove(collection.size()-1);
        return popped;
    }

    /**
     * Returns the last element that was pushed on this stack without removing it.
     *
     * @return the last element that was pushed on this stack
     * @throws EmptyStackException if this stack is empty when peek is called
     */
    public Object peek() {
        if (collection.isEmpty()) {
            throw new EmptyStackException("Cannot perform peek on an empty stack.");
        }

        return collection.get(collection.size()-1);
    }

    /**
     * Removes all elements from this stack.
     */
    public void clear() {
        collection.clear();
    }
}
