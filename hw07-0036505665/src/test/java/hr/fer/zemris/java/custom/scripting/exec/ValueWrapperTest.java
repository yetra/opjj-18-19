package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValueWrapperTest {

    private Object[] intFirst = {10, 0, null, "-123", "45"};
    private Object[] intSecond = {-1, "23", null, "123", null};

    private Object[] doubleFirst = {10.23, 0, null, "-12.12", "45", "15.5E-2"};
    private Object[] doubleSecond = {-1.0, "0.123", "10E2", "90", 0.0, "1E0"};

    @Test
    void testAddIntegers() {
        Integer[] expected = {9, 23, 0, 0, 45};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(intFirst[i]);
            firstWrapper.add(intSecond[i]);

            assertTrue(firstWrapper.getValue() instanceof Integer);
            assertEquals(expected[i], firstWrapper.getValue());
        }
    }

    @Test
    void testAddDoubles() {
        Double[] expected = {9.23, 0.123, 1000.0, 77.88, 45.0, 1.155};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(doubleFirst[i]);
            firstWrapper.add(doubleSecond[i]);

            assertTrue(firstWrapper.getValue() instanceof Double);
            assertEquals(expected[i], firstWrapper.getValue());
        }
    }

    @Test
    void testSubtractIntegers() {
        Integer[] expected = {11, -23, 0, -246, 45};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(intFirst[i]);
            firstWrapper.subtract(intSecond[i]);

            assertTrue(firstWrapper.getValue() instanceof Integer);
            assertEquals(expected[i], firstWrapper.getValue());
        }
    }

    @Test
    void testSubtractDoubles() {
        Double[] expected = {11.23, -0.123, -1000.0, -102.12, 45.0, -0.845};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(doubleFirst[i]);
            firstWrapper.subtract(doubleSecond[i]);

            assertTrue(firstWrapper.getValue() instanceof Double);
            assertEquals(expected[i], firstWrapper.getValue());
        }
    }

    @Test
    void testMultiplyIntegers() {
        Integer[] expected = {-10, 0, 0, -15129, 0};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(intFirst[i]);
            firstWrapper.multiply(intSecond[i]);

            assertTrue(firstWrapper.getValue() instanceof Integer);
            assertEquals(expected[i], firstWrapper.getValue());
        }
    }

    @Test
    void testMultiplyDoubles() {
        Double[] expected = {-10.23, 0.0, 0.0, -1090.8, 0.0, 0.155};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(doubleFirst[i]);
            firstWrapper.multiply(doubleSecond[i]);

            assertTrue(firstWrapper.getValue() instanceof Double);
            assertEquals(expected[i], firstWrapper.getValue());
        }
    }

    @Test
    void testDivideIntegers() {
        Object[] intFirst = {10, 0, null, "-123", "45"};
        Object[] intSecond = {-1, "23", 10, "123", 1};

        Integer[] expected = {-10, 0, 0, -1, 45};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(intFirst[i]);
            firstWrapper.divide(intSecond[i]);

            assertTrue(firstWrapper.getValue() instanceof Integer);
            assertEquals(expected[i], firstWrapper.getValue());
        }
    }

    @Test
    void testDivideDoubles() {
        Object[] doubleFirst = {10.23, 0, null, "-12.12", "45", "15.5E-2"};
        Object[] doubleSecond = {-1.0, "0.123", "10E2", "10", 1.0, "1E0"};

        Double[] expected = {-10.23, 0.0, 0.0, -1.212, 45.0, 0.155};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(doubleFirst[i]);
            firstWrapper.divide(doubleSecond[i]);

            assertTrue(firstWrapper.getValue() instanceof Double);
            assertEquals(expected[i], firstWrapper.getValue());
        }
    }

    @Test
    void testNumCompare() {
        Object[] first = {10, 0, null, "-123", "45", 10.23, 0, null, "-12.12", "45", "15.5E-2", null};
        Object[] second = {-1, "23", null, "123", null, -1.0, "1.0", "10E2", "10", 1.0, "1E0", 0};

        int[] expected = {1, -1, 0, -1, 1, 1, -1, -1, -1, 1, -1, 0};

        for (int i = 0; i < expected.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(first[i]);
            int result = firstWrapper.numCompare(second[i]);

            assertEquals(expected[i], result);
        }
    }

    @Test
    void testInvalidOperands() {
        Object[] first = {"a10.23", "12", null, "-12.", "4..5", "150.E-2", "1,2"};
        Object[] second = {-1.0, ".12p", "10e2", "90", 0.0, "1E0.1", 0};

        for (int i = 0; i < first.length; i++) {
            ValueWrapper firstWrapper = new ValueWrapper(first[i]);
            Object secondOperand = second[i];

            assertThrows(RuntimeException.class, () -> firstWrapper.add(secondOperand));
            assertThrows(RuntimeException.class, () -> firstWrapper.subtract(secondOperand));
            assertThrows(RuntimeException.class, () -> firstWrapper.multiply(secondOperand));
            assertThrows(RuntimeException.class, () -> firstWrapper.divide(secondOperand));
            assertThrows(RuntimeException.class, () -> firstWrapper.numCompare(secondOperand));
        }
    }
}
