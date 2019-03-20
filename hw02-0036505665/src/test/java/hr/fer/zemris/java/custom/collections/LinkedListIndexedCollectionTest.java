package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

    @Test
    public void testEmptyConstructor() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        assertNotNull(collection);
        assertEquals(0, collection.size());
    }

    @Test
    public void testCollectionConstructor() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(5);

        LinkedListIndexedCollection testCollection = new LinkedListIndexedCollection(collection);
        assertEquals(5, testCollection.size());
        for (int i = 0; i < 5; i++) {
            assertTrue(testCollection.contains(i));
        }

        assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
    }

    @Test
    public void testSize() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(12);
        assertEquals(12, collection.size());
        collection = new LinkedListIndexedCollection();
        assertEquals(0, collection.size());
    }

    @Test
    public void testAdd() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(10);
        Object testElement = "test_element";

        collection.add(testElement);
        assertEquals(11, collection.size());
        assertTrue(collection.contains(testElement));

        assertThrows(NullPointerException.class, () -> collection.add(null));
    }

    @Test
    public void testGet() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(5);
        assertEquals(3, collection.get(3));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(collection.size()));
    }

    @Test
    public void testContains() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(10);

        for (int i = 0; i < 10; i++) {
            assertTrue(collection.contains(i));
        }
        assertFalse(collection.contains(null));
    }

    @Test
    public void testRemove1() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(5);

        assertEquals(5, collection.size());
        assertTrue(collection.remove(Integer.valueOf(3)));
        assertEquals(4, collection.size());
        assertFalse(collection.remove(Integer.valueOf(3)));
    }

    @Test
    public void testToArray() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(4);
        Object[] testArray = {0, 1, 2, 3};
        assertArrayEquals(testArray, collection.toArray());

        collection = new LinkedListIndexedCollection();
        testArray = new Object[0];
        assertArrayEquals(testArray, collection.toArray());
    }

    @Test
    public void testForEach() {
        class TestProcessor extends Processor {
            String values = "Values:";

            @Override
            public void process(Object value) {
                values += " " + value.toString();
            }
        }

        LinkedListIndexedCollection collection = prepareCollectionOfSize(4);
        String testString = "Values: 0 1 2 3";
        TestProcessor processor = new TestProcessor();

        collection.forEach(processor);
        assertEquals(testString, processor.values);
    }

    @Test
    public void testClear() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(5);

        assertEquals(5, collection.size());
        collection.clear();
        assertEquals(0, collection.size());
    }

    @Test
    public void testInsert() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(5);

        collection.insert("test1", 1);
        assertEquals("test1", collection.get(1));
        assertEquals(6, collection.size());

        collection.insert("testSize", collection.size());
        assertEquals("testSize", collection.get(collection.size()-1));
        assertEquals(7, collection.size());

        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("test", -1));
        assertFalse(collection.contains("test"));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("test", collection.size() + 1));
        assertFalse(collection.contains("test"));
    }

    @Test
    public void testIndexOf() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(5);

        for (int i = 0, size = collection.size(); i < size; i++) {
            assertEquals(i, collection.indexOf(i));
        }

        assertEquals(-1, collection.indexOf(null));
        assertEquals(-1, collection.indexOf("test"));
    }

    @Test
    public void testRemove() {
        LinkedListIndexedCollection collection = prepareCollectionOfSize(5);

        Object removedElement = collection.get(3);
        collection.remove(3);
        assertFalse(collection.contains(removedElement));
        assertEquals(4, collection.size());
    }

    /**
     * Returns a collection of specified size that is filled with numbers of the
     * range [0, {@code size}-1].
     *
     * @param size the size of the collection
     * @return a collection of specified size that is filled with numbers
     */
    private LinkedListIndexedCollection prepareCollectionOfSize(int size) {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        for (int i = 0; i < size; i++) {
            collection.add(i);
        }

        return collection;
    }
}
