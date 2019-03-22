package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

    /**
     * Maximum precision for comparing doubles.
     */
    private static final double MAX_PRECISION = 10e-6;

    private final double[] real = {50.34, 3.0, 143.0, 1.333, 0.0, 123.09, 0.0023,
            25.234, 1.0, 1.0, -1.0, -1.0};

    private final double[] imaginary = {19.58, -4.0, 143.0, 234.24, 0.0, 5.34, 0.0,
            -121.312, 1.0, -1.0, 1.0, -1.0};

    @Test
    public void testConstructor() {
        for (int i = 0; i < real.length; i++) {
            ComplexNumber number = new ComplexNumber(real[i], imaginary[i]);

            assertNotNull(number);
            assertEquals(real[i], number.getReal());
            assertEquals(imaginary[i], number.getImaginary());
            assertEquals(calculateMagnitude(real[i], imaginary[i]), number.getMagnitude());
            assertEquals(calculateAngle(real[i], imaginary[i])+Math.PI, number.getAngle());
        }
    }

    @Test
    public void testFromReal() {
        for (int i = 0; i < real.length; i++) {
            ComplexNumber number = ComplexNumber.fromReal(real[i]);

            assertNotNull(number);
            assertEquals(new ComplexNumber(real[i], 0.0), number);
        }
    }

    @Test
    public void testFromImaginary() {
        for (int i = 0; i < imaginary.length; i++) {
            ComplexNumber number = ComplexNumber.fromImaginary(imaginary[i]);

            assertNotNull(number);
            assertEquals(new ComplexNumber(0.0, imaginary[i]), number);
        }
    }

    @Test
    public void testFromMagnitudeAndAngle() {
        for (int i = 0; i < real.length; i++) {
            double magnitude = calculateMagnitude(real[i], imaginary[i]);
            double angle = calculateAngle(real[i], imaginary[i]);

            ComplexNumber number = ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);

            assertNotNull(number);
            assertEquals(real[i], number.getReal(), MAX_PRECISION);
            assertEquals(imaginary[i], number.getImaginary(), MAX_PRECISION);
        }

    }

    @Test
    public void testParsePositiveCases() {
        assertEquals(ComplexNumber.parse("i"), ComplexNumber.fromImaginary(1.0));
        assertEquals(ComplexNumber.parse("+i"), ComplexNumber.fromImaginary(1.0));
        assertEquals(ComplexNumber.parse("-i"), ComplexNumber.fromImaginary(-1.0));

        assertEquals(ComplexNumber.parse("351i"), ComplexNumber.fromImaginary(351.0));
        assertEquals(ComplexNumber.parse("-317i"), ComplexNumber.fromImaginary(-317.0));
        assertEquals(ComplexNumber.parse("3.51i"), ComplexNumber.fromImaginary(3.51));
        assertEquals(ComplexNumber.parse("-3.17i"), ComplexNumber.fromImaginary(-3.17));

        assertEquals(ComplexNumber.parse("351"), ComplexNumber.fromReal(351.0));
        assertEquals(ComplexNumber.parse("-317"), ComplexNumber.fromReal(-317.0));
        assertEquals(ComplexNumber.parse("3.51"), ComplexNumber.fromReal(3.51));
        assertEquals(ComplexNumber.parse("-3.17"), ComplexNumber.fromReal(-3.17));

        assertEquals(ComplexNumber.parse("-2.71-3.15i"), new ComplexNumber(-2.71, -3.15));
        assertEquals(ComplexNumber.parse("31+24i"), new ComplexNumber(31.0, 24.0));
        assertEquals(ComplexNumber.parse("-1-i"), new ComplexNumber(-1.0, -1.0));

        assertEquals(ComplexNumber.parse("+2.17"), new ComplexNumber(2.17, 0.0));
        assertEquals(ComplexNumber.parse("+2.17+3.15i"), new ComplexNumber(2.17, 3.15));
    }

    @Test
    public void testParseNegativeCases() {
        String[] testData = {"i351", "-i317", "i3.51", "-i3.17", "-+2.71", "--2.71",
                "-2.71+-3.15i", "+2.71-+3.15i", "-+2.71", "", "a"};

        for (String invalidExpression : testData) {
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> ComplexNumber.parse(invalidExpression));
            assertEquals("The string \"" + invalidExpression +
                    "\" is not a valid complex number.", exception.getMessage());
        }
    }

    @Test
    public void testGetReal() {
        for (int i = 0; i < real.length; i++) {
            ComplexNumber number = new ComplexNumber(real[i], imaginary[i]);

            assertEquals(real[i], number.getReal());
        }
    }

    @Test
    public void testGetImaginary() {
        for (int i = 0; i < real.length; i++) {
            ComplexNumber number = new ComplexNumber(real[i], imaginary[i]);

            assertEquals(calculateMagnitude(real[i], imaginary[i]), number.getMagnitude());
        }
    }

    @Test
    public void testGetMagnitude() {
        for (int i = 0; i < real.length; i++) {
            ComplexNumber number = new ComplexNumber(real[i], imaginary[i]);

            assertEquals(calculateMagnitude(real[i], imaginary[i]), number.getMagnitude());
        }
    }

    @Test
    public void testGetAngle() {
        for (int i = 0; i < real.length; i++) {
            ComplexNumber number = new ComplexNumber(real[i], imaginary[i]);

            double angle = number.getAngle();
            assertEquals(calculateAngle(real[i], imaginary[i])+Math.PI, angle);
            assertTrue(0 <= angle && angle <= 2*Math.PI);

        }
    }

    @Test
    public void testAdd() {
        for (int i = 0; i < real.length-1; i += 2) {
            ComplexNumber number1 = new ComplexNumber(real[i], imaginary[i]);
            ComplexNumber number2 = new ComplexNumber(real[i+1], imaginary[i+1]);

            assertEquals(new ComplexNumber(real[i]+real[i+1],
                    imaginary[i]+imaginary[i+1]), number1.add(number2)
            );
        }
    }

    @Test
    public void testSub() {
        for (int i = 0; i < real.length-1; i += 2) {
            ComplexNumber number1 = new ComplexNumber(real[i], imaginary[i]);
            ComplexNumber number2 = new ComplexNumber(real[i+1], imaginary[i+1]);

            assertEquals(
                    new ComplexNumber(real[i]-real[i+1], imaginary[i]-imaginary[i+1]),
                    number1.sub(number2)
            );
        }

    }

    @Test
    public void testMul() {
        for (int i = 0; i < real.length-1; i += 2) {
            ComplexNumber number1 = new ComplexNumber(real[i], imaginary[i]);
            ComplexNumber number2 = new ComplexNumber(real[i+1], imaginary[i+1]);

            assertEquals(
                    new ComplexNumber(real[i]*real[i+1] - imaginary[i]*imaginary[i+1],
                            real[i]*imaginary[i+1] + imaginary[i]*real[i+1]),
                    number1.mul(number2)
            );
        }
    }

    @Test
    public void testDiv() {
        for (int i = 0; i < real.length-1; i += 2) {
            ComplexNumber number1 = new ComplexNumber(real[i], imaginary[i]);
            ComplexNumber number2 = new ComplexNumber(real[i+1], imaginary[i+1]);

            assertEquals(
                    new ComplexNumber(real[i]*real[i+1] - imaginary[i]*imaginary[i+1],
                            real[i]*imaginary[i+1] + imaginary[i]*real[i+1]),
                    number1.mul(number2)
            );
        }
    }

    @Test
    public void testPower() {
        double real = -7.89;
        double imaginary = 0.24;

        ComplexNumber number = new ComplexNumber(real, imaginary);

        assertEquals(new ComplexNumber(3853.81294641, -471.0860208),
                number.power(4));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> number.power(-1));
        assertEquals("Power must not be negative.", exception.getMessage());
    }

    @Test
    public void testRoot() {
        double real = -74.98;
        double imaginary = 102.35;

        ComplexNumber number = new ComplexNumber(real, imaginary);

        assertEquals(new ComplexNumber(2.3827591988074652, 1.1235373571998444),
                number.root(5)[0]);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> number.root(0));
        assertEquals("Root must not be negative or zero.", exception.getMessage());
    }

    @Test
    public void testToString() {
        assertEquals("i", ComplexNumber.fromImaginary(1.0).toString());
        assertEquals("-i", ComplexNumber.fromImaginary(-1.0).toString());

        assertEquals("3.324-i", new ComplexNumber(3.324, -1.0).toString());
        assertEquals("0.023+i", new ComplexNumber(0.023, 1.0).toString());

        assertEquals("-2342.12-123.23i", new ComplexNumber(-2342.12, -123.23).toString());
        assertEquals("12.042i", new ComplexNumber(0.0, 12.042).toString());

        assertEquals("0.0", ComplexNumber.fromReal(0.0).toString());
        assertEquals("-345.0", ComplexNumber.fromReal(-345).toString());
    }

    /**
     * Helper function which calculates the magnitude of a complex number.
     * @param real the real part of the complex number
     * @param imaginary the imaginary part of the complex number
     * @return the magnitude of the complex number
     */
    private double calculateMagnitude(double real, double imaginary) {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    /**
     * Helper function which calculates the angle of a complex number.
     * @param real the real part of the complex number
     * @param imaginary the imaginary part of the complex number
     * @return the angle of the complex number
     */
    private double calculateAngle(double real, double imaginary) {
        return Math.atan2(imaginary, real);
    }

}
