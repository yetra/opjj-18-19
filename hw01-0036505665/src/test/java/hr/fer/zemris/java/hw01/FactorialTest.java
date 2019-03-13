package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FactorialTest {

    @Test
    public void testPositiveNumber() {
        assertEquals(120L, Factorial.factorial(5));
    }

    @Test
    public void testLargeFactorial() {
        assertEquals(2432902008176640000L, Factorial.factorial(20));
    }

    @Test
    public void testFactorialOfZero() {
        assertEquals(1L, Factorial.factorial(0));
    }

    @Test
    public void testNegativeNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-5));
        assertEquals("Number can't be negative.", exception.getMessage());
    }
}
