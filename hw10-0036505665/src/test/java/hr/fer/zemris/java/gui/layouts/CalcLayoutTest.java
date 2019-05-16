package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CalcLayoutTest {

    //Napišite junit testove koji će provjeriti:
    //1) da se iznimka baca ako se pokuša koristiti ograničenje (r,s) gdje je r<1 || r>5 ili s<1 || s>7,
    //2) da se iznimka baca ako se pokuša koristiti ograničenje (1,s) gdje je s>1 && s<6,
    //3) da se iznimka baca ako se uz isto ograničenje pokuša postaviti više komponenti
    // (komponente uspoređivati s ==, ne pozivanjem metode equals).

    @Test
    void testPositionOutOfBounds() {
        RCPosition[] invalidPositions = {
                new RCPosition(-2, 0), new RCPosition(0, 5), new RCPosition(3, 8), new RCPosition(0, 8)
        };

        JPanel p = new JPanel(new CalcLayout(2));

        for (RCPosition position : invalidPositions) {
            assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(""), position));
        }
    }

    @Test
    void testPositionOverlappingFirstPosition() {
        RCPosition[] invalidPositions = {
                new RCPosition(1, 2), new RCPosition(1, 3), new RCPosition(1, 4), new RCPosition(1, 5)
        };

        JPanel p = new JPanel(new CalcLayout());

        for (RCPosition position : invalidPositions) {
            assertThrows(CalcLayoutException.class, () -> p.add(new JButton("button"), position));
        }
    }

    @Test
    void testSamePositionForDifferentComponents() {
        JPanel p = new JPanel(new CalcLayout());

        p.add(new JButton("button1"), new RCPosition(2, 5));
        assertThrows(CalcLayoutException.class, () -> p.add(new JButton("button2"), new RCPosition(2, 5)));
    }

    @Test
    void testPreferredSize() {
        JPanel p = new JPanel(new CalcLayout(2));

        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));

        p.add(l1, new RCPosition(2,2));
        p.add(l2, new RCPosition(3,3));

        Dimension dim = p.getPreferredSize();
        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    void testPreferredSizeFirstPosition() {
        JPanel p = new JPanel(new CalcLayout(2));

        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));

        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));

        Dimension dim = p.getPreferredSize();
        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }
}
