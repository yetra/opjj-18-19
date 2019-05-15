package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrimListModelTest {

    @Test
    void testGetElementAtIndex() {
        PrimListModel model = new PrimListModel();

        assertEquals(1, model.getElementAt(0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> model.getElementAt(1));

        model.next();
        assertEquals(2, model.getElementAt(1));
    }

    @Test
    void testNext() {
        PrimListModel model = new PrimListModel();

        assertEquals(1, model.getElementAt(0));
        model.next();
        assertEquals(2, model.getElementAt(1));
        model.next();
        assertEquals(3, model.getElementAt(2));
        model.next();
        assertEquals(5, model.getElementAt(3));
        model.next();
        assertEquals(7, model.getElementAt(4));
        model.next();
        assertEquals(11, model.getElementAt(5));
    }

    @Test
    void testGetSize() {
        PrimListModel model = new PrimListModel();

        assertEquals(1, model.getSize());
        model.next();
        assertEquals(2, model.getSize());
        model.next();
        assertEquals(3, model.getSize());
        model.next();
        assertEquals(4, model.getSize());
    }
}
