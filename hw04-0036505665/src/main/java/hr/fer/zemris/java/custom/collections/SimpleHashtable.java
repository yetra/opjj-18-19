package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * This class is an implementation of a simple hashtable.
 *
 * @param <K> the type of the key in a table entry
 * @param <V> the type of the value in a table entry
 *
 * @author Bruna Dujmović
 *
 */
public class SimpleHashtable<K, V> {

    /**
     * The default number of slots in the hashtable.
     */
    public static int DEFAULT_CAPACITY = 16;

    /**
     * An array of slots in the hashtable.
     */
    private TableEntry<K, V>[] table;

    /**
     * The size of the hashtable (the number of currently stored table entries).
     */
    private int size;

    /**
     * This class models an entry (key-value pair) in a {@link SimpleHashtable} object.
     *
     * @param <K> the type of the key in a table entry
     * @param <V> the type of the value in a table entry
     */
    public static class TableEntry<K, V> {

        /**
         * The key of this table entry.
         */
        private K key;

        /**
         * The value of this table entry.
         */
        private V value;

        /**
         * A reference to the next table entry in the hashtable slot.
         */
        private TableEntry<K, V> next;

        /**
         * Constructs a table entry of a given key and value.
         *
         * @param key the key of the table entry
         * @param value the value of the table entry
         * @throws NullPointerException if the given key is {@code null}
         */
        public TableEntry(K key, V value) {
            Objects.requireNonNull(key);

            this.key = key;
            this.value = value;
            this.next = null;
        }

        /**
         * Returns the key of this table entry.
         *
         * @return the key of this table entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value of this table entry.
         *
         * @return the value of this table entry
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value of this table entry to the specified value.
         *
         * @param value the table entry value to set
         */
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key.toString() + "=" + value.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableEntry<?, ?> that = (TableEntry<?, ?>) o;
            return key.equals(that.key) &&
                    Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    /**
     * Constructs a hashtable of default capacity (16 slots).
     */
    public SimpleHashtable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs a hastable of specified capacity.
     *
     * @param capacity the capacity of the hashtable
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        table = (TableEntry<K,V>[]) new TableEntry[nextPowerOfTwo(capacity)];
        size = 0;
    }

    /**
     * Adds a new table entry to this hashtable. If an entry with the specified key
     * already exists, this method will not add another pair with the same key.
     *
     * If the slot that the new entry should be added to already
     *
     * @param key the key of the table entry
     * @param value the value of the table entry
     * @throws NullPointerException if the given key is {@code null}
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key);

        int slotIndex = getSlotIndex(key);
        TableEntry<K, V> currentEntry = table[slotIndex];

        if (currentEntry == null) {
            table[slotIndex] = new TableEntry<>(key, value);
            size++;
            return;
        }

        while (currentEntry.next != null) {
            if (currentEntry.key.equals(key)) {
                currentEntry.value = value;
                return;
            }
            currentEntry = currentEntry.next;
        }

        currentEntry.next = new TableEntry<>(key, value);
        size++;
    }

    /**
     * Returns the value of the table entry specified by a given key. If no such entry
     * exists, this method will return {@code null}.
     *
     * @param key the key of the table entry
     * @return the value of the table entry specified by a given key, or {@code null}
     *         if no such value exists
     */
    public V get(Object key) {
        if (key == null) {
            return null;
        }

        int slotIndex = getSlotIndex(key);
        TableEntry<K, V> currentEntry = table[slotIndex];

        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                return currentEntry.value;
            }

            currentEntry = currentEntry.next;
        }

        return null;
    }

    /**
     * Return the size of this hashtable.
     *
     * @return the size of this hashtable
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this hashtable contains an entry with the given key.
     *
     * @param key the key to check for
     * @return {@code true} if this hashtable contains an entry with the given key
     */
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }

        int slotIndex = getSlotIndex(key);
        TableEntry<K, V> currentEntry = table[slotIndex];

        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                return true;
            }

            currentEntry = currentEntry.next;
        }

        return false;
    }

    /**
     * Returns {@code true} if this hashtable contains an entry with the given value.
     *
     * @param value the value to check for
     * @return {@code true} if this hashtable contains an entry with the given value
     */
    public boolean containsValue(Object value) {

        for (TableEntry<K, V> currentEntry : table) {
            while (currentEntry != null) {
                if (currentEntry.value.equals(value)) {
                    return true;
                }

                currentEntry = currentEntry.next;
            }
        }

        return false;
    }

    /**
     * Removes the entry with the given key if it exists in this hashtable.
     *
     * @param key the key of the entry to remove
     */
    public void remove(Object key) {
        if (key != null) {
            int slotIndex = getSlotIndex(key);
            TableEntry<K, V> currentEntry = table[slotIndex];

            if (currentEntry.key.equals(key)) {
                table[slotIndex] = currentEntry.next;
                size--;
                return;
            }

            while (currentEntry.next != null) {
                if (currentEntry.next.key.equals(key)) {
                    currentEntry.next = currentEntry.next.next;
                }
            }
        }
    }

    /**
     * Returns {@code true} if this hashtable contains no entries.
     *
     * @return {@code true} if this hashtable contains no entries
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (TableEntry<K, V> entry : table) {
            if (entry != null) {
                sb.append(entry.toString()).append(", ");
            }
        }

        return sb.append("]").toString();
    }

    /*
     * --------------------------------------------------------------------------
     * ----------------------------- HELPER METHODS -----------------------------
     * --------------------------------------------------------------------------
     */

    /**
     * Calculates the index of the slot for a given table entry key.
     *
     * @param key the table entry key whose slot index is calculated
     * @return the index of the slot for a given table entry
     */
    private int getSlotIndex(Object key) {
        return Math.abs(key.hashCode()) % 2;
    }

    /**
     * Returns the smallest power of 2 that is greater than or equal to the given
     * integer.
     * @param number the number whose power of 2 is returned
     * @return the smallest power of 2 that is greater than or equal to the given
     *         integer
     * @throws IllegalArgumentException if the given number is less than 1
     */
    private int nextPowerOfTwo(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Number cannot be less than 1.");
        }

        int highestOneBit = Integer.highestOneBit(number);

        return number == highestOneBit ? number : highestOneBit << 1;
    }
}
