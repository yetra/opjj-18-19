package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link ObjectMultistack} is a map-like collection which allows storing multiple
 * values for the same key in stacks.
 *
 * The keys are instances of the class {@link String}, and the values associated
 * with those keys are instances of class {@link ValueWrapper}.
 *
 * @author Bruna Dujmović
 */
public class ObjectMultistack {

    /**
     * The underlying map used to store pairs of key names and their stacks.
     */
    private Map<String, MultistackEntry> entryMap = new HashMap<>();

    /**
     * Adds a new stack entry of the given value to the top of the stack specified
     * by the given key name in O(1).
     *
     * @param keyName the key name that specifies which stack to push to
     * @param valueWrapper the value of the new stack entry
     * @throws NullPointerException if the given key name is {@code null}
     */
    public void push(String keyName, ValueWrapper valueWrapper) {
        Objects.requireNonNull(keyName);
        // TODO Objects.requireNonNull(valueWrapper); ?

        MultistackEntry newEntry = new MultistackEntry(valueWrapper);
        MultistackEntry topOfStack = entryMap.get(keyName);

        if (topOfStack == null) {
            entryMap.put(keyName, newEntry);
        } else {
            newEntry.next = topOfStack;
            topOfStack = newEntry;
        }
    }

    /**
     * Removes the entry that is at the top of a given key name's stack in O(1), and
     * returns the value of the popped entry.
     *
     * @param keyName the key name that specifies which stack to pop from
     * @return the value of the popped entry
     * @throws NullPointerException if the given key name is {@code null}
     */
    public ValueWrapper pop(String keyName) {
        Objects.requireNonNull(keyName);

        MultistackEntry topOfStack = entryMap.get(keyName);
        ValueWrapper poppedValue = topOfStack.value;
        topOfStack = topOfStack.next;

        return poppedValue;
    }

    /**
     * Returns (without removing) the value of the entry that is at the top of a given
     * key name's stack in O(1).
     *
     * @param keyName the key name that specifies which stack to peek from
     * @return the value of the entry that is at the top of a given key name's stack
     * @throws NullPointerException if the given key name is {@code null}
     */
    public ValueWrapper peek(String keyName) {
        Objects.requireNonNull(keyName);

        return entryMap.get(keyName).value;
    }

    /**
     * Returns {@code true} if a given key name's stack is empty.
     *
     * @param keyName the key name that specifies which stack to check
     * @return {@code true} if a given key name's stack is empty
     * @throws NullPointerException if the given key name is {@code null}
     */
    public boolean isEmpty(String keyName) {
        Objects.requireNonNull(keyName);

        return entryMap.get(keyName) == null;
    }

    /**
     * This class represents an entry in the {@link ObjectMultistack} collection.
     *
     * It consists of a value that is specified through its constructor and of a
     * reference to the next element in the stack.
     */
    public static class MultistackEntry {

        /**
         * The value of this entry.
         */
        private ValueWrapper value;

        /**
         * A reference to the next entry in this entry's stack.
         */
        private MultistackEntry next;

        /**
         * Constructs a new entry of the specified value.
         *
         * @param value the value of the new entry.
         */
        public MultistackEntry(ValueWrapper value) {
            this.value = value;
            // TODO this.value = Objects.requireNonNull(value); ?
            this.next = null;
        }
    }
}
