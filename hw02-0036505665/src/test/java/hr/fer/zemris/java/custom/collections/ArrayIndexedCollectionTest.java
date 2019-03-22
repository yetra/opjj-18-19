package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

    @Test
    public void testInitialCapacityConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(10);
        assertNotNull(collection);
        assertEquals(0, collection.size());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
        assertEquals("Array capacity must not be less than 1.", exception.getMessage());
    }

    @Test
    public void testDefaultCapacityConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertNotNull(collection);
        assertEquals(0, collection.size());
    }

    @Test
    public void testInitialCapacityCollectionConstructor() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(5);

        ArrayIndexedCollection testCollection = new ArrayIndexedCollection(collection, 1);
        assertNotNull(testCollection);
        assertEquals(5, testCollection.size());
        assertArrayEquals(collection.toArray(), testCollection.toArray());


        Exception exception1 = assertThrows(NullPointerException.class,
                () -> new ArrayIndexedCollection(null, 1));
        assertEquals("Collection parameter cannot be null.", exception1.getMessage());
        Exception exception2 = assertThrows(IllegalArgumentException.class,
                () -> new ArrayIndexedCollection(collection, -5));
        assertEquals("Array capacity must not be less than 1.", exception2.getMessage());
    }

    @Test
    public void testCollectionConstructor() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(5);

        ArrayIndexedCollection testCollection = new ArrayIndexedCollection(collection, 1);
        assertNotNull(testCollection);
        assertEquals(5, testCollection.size());
        assertArrayEquals(collection.toArray(), testCollection.toArray());

        Exception exception = assertThrows(NullPointerException.class,
                () -> new ArrayIndexedCollection(null, 1));
        assertEquals("Collection parameter cannot be null.", exception.getMessage());
    }

    @Test
    public void testSize() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(12);
        assertEquals(12, collection.size());
        collection = new ArrayIndexedCollection();
        assertEquals(0, collection.size());
    }

    @Test
    public void testAdd() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(10);
        Object testElement = "test_element";

        collection.add(testElement);
        assertEquals(11, collection.size());
        assertTrue(collection.contains(testElement));

        Exception exception = assertThrows(NullPointerException.class, () -> collection.add(null));
        assertEquals("Cannot add null element to the collection.", exception.getMessage());
    }

    @Test
    public void testGet() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(5);
        assertEquals(3, collection.get(3));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(collection.size()));
    }

    @Test
    public void testContains() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(10);

        for (int i = 0; i < 10; i++) {
            assertTrue(collection.contains(i));
        }
        assertFalse(collection.contains(null));
    }

    @Test
    public void testRemove1() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(5);

        assertEquals(5, collection.size());
        assertTrue(collection.remove(Integer.valueOf(3)));
        assertEquals(4, collection.size());
        assertFalse(collection.remove(Integer.valueOf(3)));
    }

    @Test
    public void testToArray() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(4);
        Object[] testArray = {0, 1, 2, 3};
        assertArrayEquals(testArray, collection.toArray());

        collection = new ArrayIndexedCollection(3);
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

        ArrayIndexedCollection collection = prepareCollectionOfSize(4);
        String testString = "Values: 0 1 2 3";
        TestProcessor processor = new TestProcessor();

        collection.forEach(processor);
        assertEquals(testString, processor.values);
    }

    @Test
    public void testClear() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(5);

        assertEquals(5, collection.size());
        collection.clear();
        assertEquals(0, collection.size());
    }

    @Test
    public void testInsert() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(5);

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
        ArrayIndexedCollection collection = prepareCollectionOfSize(5);

        for (int i = 0, size = collection.size(); i < size; i++) {
            assertEquals(i, collection.indexOf(i));
        }

        assertEquals(-1, collection.indexOf(null));
        assertEquals(-1, collection.indexOf("test"));
    }

    @Test
    public void testRemove2() {
        ArrayIndexedCollection collection = prepareCollectionOfSize(5);

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
    private ArrayIndexedCollection prepareCollectionOfSize(int size) {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(size);
        for (int i = 0; i < size; i++) {
            collection.add(i);
        }

        return collection;
    }
}
