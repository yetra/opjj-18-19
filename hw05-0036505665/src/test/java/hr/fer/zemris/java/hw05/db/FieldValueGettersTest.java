package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldValueGettersTest {

    @Test
    void testFirstNameGetter() {
        StudentRecord[] records = {
                new StudentRecord("0000000027", "Komunjer", "Luka", 4),
                new StudentRecord("0000000028", "Kosanović", "Nenad", 5),
                new StudentRecord("0000000029", "Kos-Grabar", "Ivo", 2)
        };

        String[] expected = {"Luka", "Nenad", "Ivo"};

        IFieldValueGetter firstNameGetter = FieldValueGetters.FIRST_NAME;

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], firstNameGetter.get(records[i]));
        }
    }

    @Test
    void testLastNameGetter() {
        StudentRecord[] records = {
                new StudentRecord("0000000027", "Komunjer", "Luka", 4),
                new StudentRecord("0000000028", "Kosanović", "Nenad", 5),
                new StudentRecord("0000000029", "Kos-Grabar", "Ivo", 2)
        };

        String[] expected = {"Komunjer", "Kosanović", "Kos-Grabar"};

        IFieldValueGetter lastNameGetter = FieldValueGetters.LAST_NAME;

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], lastNameGetter.get(records[i]));
        }
    }

    @Test
    void testJMBAGGetter() {
        StudentRecord[] records = {
                new StudentRecord("0000000027", "Komunjer", "Luka", 4),
                new StudentRecord("0000000028", "Kosanović", "Nenad", 5),
                new StudentRecord("0000000029", "Kos-Grabar", "Ivo", 2)
        };

        String[] expected = {"0000000027", "0000000028", "0000000029"};

        IFieldValueGetter JMBAGGetter = FieldValueGetters.JMBAG;

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], JMBAGGetter.get(records[i]));
        }
    }
}
