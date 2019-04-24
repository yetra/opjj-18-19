package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectMultistackTest {

    @Test
    void testIsEmpty() {
        ObjectMultistack multistack = new ObjectMultistack();

        assertTrue(multistack.isEmpty("key"));
    }

    @Test
    void testPushSingleEntry() {
        ObjectMultistack multistack = new ObjectMultistack();

        assertTrue(multistack.isEmpty("key"));
        multistack.push("key", new ValueWrapper("value"));
        assertFalse(multistack.isEmpty("key"));
    }

    @Test
    void testPushNull() {
        ObjectMultistack multistack = new ObjectMultistack();

        assertThrows(NullPointerException.class, () -> multistack.push(null, null));
        assertThrows(NullPointerException.class, () -> multistack.push(null, new ValueWrapper(1)));
        assertThrows(NullPointerException.class, () -> multistack.push("key", null));
    }

    @Test
    void testPopEmptyStack() {
        ObjectMultistack multistack = new ObjectMultistack();

        assertThrows(NoStackException.class, () -> multistack.pop("key"));
    }

    @Test
    void testPopNullKey() {
        ObjectMultistack multistack = new ObjectMultistack();

        assertThrows(NullPointerException.class, () -> multistack.pop(null));
    }

    @Test
    void testPeekEmptyStack() {
        ObjectMultistack multistack = new ObjectMultistack();

        assertThrows(NoStackException.class, () -> multistack.peek("key"));
    }

    @Test
    void testPeekNullKey() {
        ObjectMultistack multistack = new ObjectMultistack();

        assertThrows(NullPointerException.class, () -> multistack.peek(null));
    }

    @Test
    void testPeekSingleEntry() {
        ObjectMultistack multistack = new ObjectMultistack();

        multistack.push("key", new ValueWrapper("value"));
        assertEquals("value", multistack.peek("key").getValue());
        assertFalse(multistack.isEmpty("key"));
    }

    @Test
    void testPeekMultipleEntries() {
        ObjectMultistack multistack = new ObjectMultistack();

        multistack.push("key1", new ValueWrapper("value1"));
        multistack.push("key1", new ValueWrapper("value2"));
        multistack.push("key2", new ValueWrapper(-12.0));

        assertEquals("value2", multistack.peek("key1").getValue());
        assertEquals("value2", multistack.peek("key1").getValue());
        assertEquals(-12.0, multistack.peek("key2").getValue());
    }

    @Test
    void testPopLastEntry() {
        ObjectMultistack multistack = new ObjectMultistack();

        multistack.push("key", new ValueWrapper("value"));
        multistack.pop("key");
        assertTrue(multistack.isEmpty("key"));
        assertThrows(NoStackException.class, () -> multistack.pop("key"));
    }

    @Test
    void testPushPopSameKey() {
        ObjectMultistack multistack = new ObjectMultistack();

        multistack.push("key", new ValueWrapper("value"));
        multistack.push("key", new ValueWrapper(1));

        assertEquals(1, multistack.pop("key").getValue());
        assertEquals("value", multistack.pop("key").getValue());

        assertThrows(NoStackException.class, () -> multistack.pop("key"));
        assertTrue(multistack.isEmpty("key"));
    }

    @Test
    void testPushPopMultipleKeys() {
        ObjectMultistack multistack = new ObjectMultistack();

        multistack.push("key1", new ValueWrapper("value1"));
        multistack.push("key1", new ValueWrapper("value2"));
        multistack.push("key2", new ValueWrapper(1));

        assertEquals("value2", multistack.pop("key1").getValue());
        assertEquals("value1", multistack.pop("key1").getValue());
        assertTrue(multistack.isEmpty("key1"));
        assertFalse(multistack.isEmpty("key2"));

        assertEquals(1, multistack.pop("key2").getValue());
        assertTrue(multistack.isEmpty("key2"));
    }
}
