package hr.fer.zemris.java.custom.collections;

public class ObjectStack {

    private static int TOP_OF_STACK = 0;

    private ArrayIndexedCollection collection;

    public ObjectStack() {
        collection = new ArrayIndexedCollection();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public int size() {
        return collection.size();
    }

    public void push(Object value) {
        collection.insert(value, TOP_OF_STACK);
    }

    public Object pop() {
        if (collection.isEmpty()) {
            throw new EmptyStackException("Cannot perform pop on an empty stack");
        }

        Object popped = collection.get(TOP_OF_STACK);
        collection.remove(TOP_OF_STACK);
        return popped;
    }

    public Object peek() {
        if (collection.isEmpty()) {
            throw new EmptyStackException("Cannot perform peek on an empty stack");
        }

        return collection.get(TOP_OF_STACK);
    }

    public void clear() {
        collection.clear();
    }
}
