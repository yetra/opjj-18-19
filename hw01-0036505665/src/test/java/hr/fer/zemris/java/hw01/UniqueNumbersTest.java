package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.UniqueNumbers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {

    @Test
    public void testTreeSize() {
        TreeNode head = null;
        assertEquals(0, treeSize(head));

        for (int i = 0; i < 5; i++) {
            head = addNode(head, i);
        }
        assertEquals(5, treeSize(head));
    }

    @Test
    public void testContainsValue() {
        TreeNode head = null;
        assertFalse(containsValue(head, -1));
        assertFalse(containsValue(head, 4));

        head = addNode(head, -1);
        assertTrue(containsValue(head, -1));
        assertFalse(containsValue(head, 4));
    }

    @Test
    public void testAddNode() {
        TreeNode head = null;
        head = addNode(head, 1);

        assertNotNull(head);
        assertTrue(containsValue(head, 1));
    }
}
