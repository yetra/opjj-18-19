package coloring.algorithms;

import java.util.Objects;

/**
 * This class models a pixel of a {@link marcupic.opjj.statespace.coloring.Picture}
 * that the user clicked on. It is represented by an x and y coordinate.
 *
 * @author Bruna Dujmović
 *
 */
public class Pixel {

    /**
     * The x coordinate.
     */
    public int x;

    /**
     * The y coordinate.
     */
    public int y;

    /**
     * Constructs a pixel of the given coordinates.
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     */
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x &&
                y == pixel.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
