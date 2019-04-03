package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    @Test
    public void testConstructor() {
        Dictionary<String, String> dictionary = new Dictionary<>();

        assertNotNull(dictionary);
    }

    @Test
    public void testIsEmpty() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testSize() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();

        assertEquals(0, dictionary.size());
    }

    @Test
    public void testPutNewEntry() {
        Dictionary<String, Double> dictionary = new Dictionary<>();

        assertEquals(0, dictionary.size());
        dictionary.put("test", -10.0);
        assertEquals(1, dictionary.size());
    }

    @Test
    public void testPutExistingEntry() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        assertEquals(0, dictionary.size());
        dictionary.put("test1", 1);
        dictionary.put("test2", 2);
        dictionary.put("test3", 3);
        assertEquals(3, dictionary.size());

        dictionary.put("test1", 1);
        assertEquals(3, dictionary.size());
    }

    @Test
    public void testPutNullKey() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        assertEquals(0, dictionary.size());
        assertThrows(NullPointerException.class, () -> dictionary.put(null, 1));
        assertEquals(0, dictionary.size());
    }

    @Test
    public void testPutNullValue() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        assertEquals(0, dictionary.size());
        dictionary.put("key", null);
        assertEquals(1, dictionary.size());
    }

    @Test
    public void testGetExistingEntry() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("test1", 1);
        dictionary.put("test2", 2);
        dictionary.put("test3", 3);
        assertEquals(3, dictionary.size());

        dictionary.put("test1", 1);
        assertEquals(3, dictionary.size());
    }

    @Test
    public void testGetValueAfterChange() {
        Dictionary<String, Double> dictionary = new Dictionary<>();

        dictionary.put("test1", 1.0);
        dictionary.put("test2", 2.2);
        dictionary.put("test3", 3.90);
        assertEquals(2.2, dictionary.get("test2"));

        dictionary.put("test2", 89.123);
        assertEquals(89.123, dictionary.get("test2"));
    }

    @Test
    public void testGetNonExistingEntry() {
        Dictionary<String, String> dictionary = new Dictionary<>();

        dictionary.put("test1", "t");
        dictionary.put("test2", "m");
        dictionary.put("test3", "p");
        assertNull(dictionary.get("non_existing"));
    }

    @Test
    public void testGetEmptyDictionary() {
        Dictionary<String, String> dictionary = new Dictionary<>();

        assertNull(dictionary.get("empty_dictionary"));
    }
}
