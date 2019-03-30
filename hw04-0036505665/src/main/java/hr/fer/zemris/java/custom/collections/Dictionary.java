package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * This class models a dictionary of key-value pairs. Each entry consists of a unique
 * non-{@code null} key, and a value.
 *
 * @param <K> the type of the key of a dictionary entry
 * @param <V> the type of the value of a dictionary entry
 *
 * @author Bruna Dujmović
 *
 */
public class Dictionary<K, V> {

    /**
     * The collection that stores all dictionary entries.
     */
    private ArrayIndexedCollection<Entry> collection;

    /**
     * This class models a dictionary entry (key-value pair).
     */
    private class Entry {

        /**
         * The key of this entry.
         */
        private K key;

        /**
         * The value of this entry.
         */
        private V value;

        /**
         * Constructs a dictionary entry that contains the given key and value.
         *
         * @param key the key of the entry
         * @param value the value of the entry
         * @throws NullPointerException if the given key is {@code null}
         */
        Entry(K key, V value) {
            Objects.requireNonNull(key);

            this.key = key;
            this.value = value;
        }
    }

    /**
     * Returns {@code true} if this dictionary contains no entries.
     *
     * @return {@code true} if this dictionary contains no entries
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Returns the number of entries in this dictionary.
     *
     * @return the number of entries in this dictionary
     */
    public int size() {
        return collection.size();
    }

    /**
     * Removes all entries from this dictionary.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Adds a new entry of the given key and value to the dictionary. If an entry of
     * the given key already exists, this method will override the value of the
     * existing entry.
     *
     * @param key the key of the new entry
     * @param value the value of the new entry
     * @throws NullPointerException if the given key is {@code null}
     */
    public void put(K key, V value) {
        ElementsGetter<Entry> getter = collection.createElementsGetter();

        while (getter.hasNextElement()) {
            Entry entry = getter.getNextElement();
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        collection.add(new Entry(key, value));
    }

    /**
     * Returns the value of the entry of a given key. If such an entry doesn't exist
     * in this dictionary, this method will return {@code null}.
     *
     * @param key the key of the entry to return
     * @return the value of the entry of the given key or {@code null} if no such
     *         entry exists
     */
    public V get(Object key) {
        ElementsGetter<Entry> getter = collection.createElementsGetter();

        while (getter.hasNextElement()) {
            Entry entry = getter.getNextElement();
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }
}
