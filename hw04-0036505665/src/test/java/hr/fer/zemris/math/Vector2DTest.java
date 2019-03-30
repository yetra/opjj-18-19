package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {

    private static final double MAX_PRECISION = 10e-6;

    @Test
    public void testConstructor() {
        Vector2D vector = new Vector2D(1, 2);

        assertNotNull(vector);
    }

    @Test
    public void testGetters() {
        Vector2D vector = new Vector2D(-12.312, 234.12);

        assertEquals(-12.312, vector.getX());
        assertEquals(234.12, vector.getY());
    }

    @Test
    public void testTranslate() {
        double[] x = {-12.31, 0.0, 90.23, -5.006};
        double[] y = {-8.90, 0.0, -3.89, 10.01};

        double[] xOffset = {1.0, 0.0, -9.12, 7.08};
        double[] yOffset = {90.12, 0.0, -1.0, 0.0};

        double[] xExpected = {-11.31, 0.0, 81.11, 2.074};
        double[] yExpected = {81.22, 0.0, -4.89, 10.01};

        for (int i = 0; i < x.length; i++) {
            Vector2D vector = new Vector2D(x[i], y[i]);
            Vector2D offset = new Vector2D(xOffset[i], yOffset[i]);
            vector.translate(offset);

            assertEquals(xExpected[i], vector.getX(), MAX_PRECISION);
            assertEquals(yExpected[i], vector.getY(), MAX_PRECISION);
        }
    }

    @Test
    public void testTranslated() {
        double[] x = {-12.31, 0.0, 90.23, -5.006};
        double[] y = {-8.90, 0.0, -3.89, 10.01};

        double[] xOffset = {1.0, 0.0, -9.12, 7.08};
        double[] yOffset = {90.12, 0.0, -1.0, 0.0};

        double[] xExpected = {-11.31, 0.0, 81.11, 2.074};
        double[] yExpected = {81.22, 0.0, -4.89, 10.01};

        for (int i = 0; i < x.length; i++) {
            Vector2D vector = new Vector2D(x[i], y[i]);
            Vector2D offset = new Vector2D(xOffset[i], yOffset[i]);

            Vector2D translated = vector.translated(offset);

            assertEquals(xExpected[i], translated.getX(), MAX_PRECISION);
            assertEquals(yExpected[i], translated.getY(), MAX_PRECISION);
        }
    }

    @Test
    public void testRotate() {
        double[] x = {-1.0, 0.0, 0.0, 78.12};
        double[] y = {0.0, 0.0, 1.0, 32.90};

        double[] angle = {Math.PI, 2.34212, Math.PI/2, 2.356};

        double[] xExpected = {1.0, 0.0, -1.0, -78.4967744670274};
        double[] yExpected = {0.0, 0.0, 0.0, 31.990636102969845};

        for (int i = 0; i < x.length; i++) {
            Vector2D vector = new Vector2D(x[i], y[i]);
            vector.rotate(angle[i]);

            assertEquals(xExpected[i], vector.getX(), MAX_PRECISION);
            assertEquals(yExpected[i], vector.getY(), MAX_PRECISION);
        }
    }

    @Test
    public void testRotated() {
        double[] x = {-1.0, 0.0, 0.0, 78.12};
        double[] y = {0.0, 0.0, 1.0, 32.90};

        double[] angle = {Math.PI, 2.34212, Math.PI/2, 2.356};

        double[] xExpected = {1.0, 0.0, -1.0, -78.4967744670274};
        double[] yExpected = {0.0, 0.0, 0.0, 31.990636102969845};

        for (int i = 0; i < x.length; i++) {
            Vector2D vector = new Vector2D(x[i], y[i]);

            Vector2D rotated = vector.rotated(angle[i]);

            assertEquals(xExpected[i], rotated.getX(), MAX_PRECISION);
            assertEquals(yExpected[i], rotated.getY(), MAX_PRECISION);
        }
    }

    @Test
    public void testScale() {
        double[] x = {-1.0, 0.0, 0.0, 123.234};
        double[] y = {0.0, 0.0, 1.0, 56.453};

        double[] scaler = {2.12, 7823.12, 5.678, 20.2};

        double[] xExpected = {-2.12, 0.0, 0.0, 2489.3268};
        double[] yExpected = {0.0, 0.0, 5.678, 1140.3506};

        for (int i = 0; i < x.length; i++) {
            Vector2D vector = new Vector2D(x[i], y[i]);
            vector.scale(scaler[i]);

            assertEquals(xExpected[i], vector.getX(), MAX_PRECISION);
            assertEquals(yExpected[i], vector.getY(), MAX_PRECISION);
        }
    }

    @Test
    public void testScaled() {
        double[] x = {-1.0, 0.0, 0.0, 123.234};
        double[] y = {0.0, 0.0, 1.0, 56.453};

        double[] scaler = {2.12, 7823.12, 5.678, 20.2};

        double[] xExpected = {-2.12, 0.0, 0.0, 2489.3268};
        double[] yExpected = {0.0, 0.0, 5.678, 1140.3506};

        for (int i = 0; i < x.length; i++) {
            Vector2D vector = new Vector2D(x[i], y[i]);

            Vector2D scaled = vector.scaled(scaler[i]);

            assertEquals(xExpected[i], scaled.getX(), MAX_PRECISION);
            assertEquals(yExpected[i], scaled.getY(), MAX_PRECISION);
        }
    }

    @Test
    public void testCopy() {
        Vector2D vector = new Vector2D(3.122, -8.234);
        Vector2D copyVector = vector.copy();

        assertEquals(vector.getX(), copyVector.getX());
        assertEquals(vector.getY(), copyVector.getY());
    }
}
