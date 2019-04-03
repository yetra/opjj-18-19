package hr.fer.zemris.math;

/**
 * This class models a 2D vector with two real coordinates {@link #x} and {@link #y}.
 * The starting point of the vector is (0, 0) and cannot be changed.
 *
 * @author Bruna Dujmović
 *
 */
public class Vector2D {

    /**
     * The x-coordinate of the 2D vector.
     */
    private double x;

    /**
     * The y-coordinate of the 2D vector.
     */
    private double y;

    /**
     * Constructs a 2D vector out of its x- and y-coordinates.
     *
     * @param x the x-coordinate of the 2D vector
     * @param y the y-coordinate of the 2D vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this 2D vector.
     *
     * @return the x-coordinate of this 2D vector
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this 2D vector.
     *
     * @return the y-coordinate of this 2D vector
     */
    public double getY() {
        return y;
    }

    /**
     * Moves this 2D vector by a given offset.
     * 
     * NOTE: The starting point of the vector, which is (0, 0), will not be affected
     * by this translation.
     *
     * @param offset the offset by which to move this 2D vector
     */
    public void translate(Vector2D offset) {
        x += offset.getX();
        y += offset.getY();
    }

    /**
     * Returns a new 2D vector that is the result of translating this vector by a
     * given offset.
     *
     * NOTE: The starting point of the vector, which is (0, 0), will not be affected
     * by this translation.
     *
     * @param offset the offset by which to move this 2D vector
     * @return a new 2D vector that is the result of the translation
     */
    public Vector2D translated(Vector2D offset) {
        return new Vector2D(x + offset.getX(), y + offset.getY());
    }

    /**
     * Rotates this 2D vector by a given angle.
     *
     * @param angle the angle by which to rotate this 2D vector
     */
    public void rotate(double angle) {
        double rotatedX = x * Math.cos(angle) - y * Math.sin(angle);
        double rotatedY = x * Math.sin(angle) + y * Math.cos(angle);

        x = rotatedX;
        y = rotatedY;
    }

    /**
     * Returns a new 2D vector that is the result of rotating this vector by a given
     * angle.
     *
     * @param angle the angle by which to rotate this 2D vector
     * @return a new 2D vector that is the result of the rotation
     */
    public Vector2D rotated(double angle) {
        return new Vector2D(
                x * Math.cos(angle) - y * Math.sin(angle),
                x * Math.sin(angle) + y * Math.cos(angle)
        );
    }

    /**
     * Performs a uniform scaling on this 2D vector.
     *
     * @param scaler the value of the uniform scaling
     */
    public void scale(double scaler) {
        x *= scaler;
        y *= scaler;
    }

    /**
     * Returns a new 2D vector that is the result of uniformly scaling this vector by
     * a given scaler value.
     *
     * @param scaler the value of the uniform scaling
     * @return a new 2D vector that is the result of the uniform scaling
     */
    public Vector2D scaled(double scaler) {
        return new Vector2D(x * scaler, y * scaler);
    }

    /**
     * Returns a new 2D vector object that is the copy of this vector.
     *
     * @return a new 2D vector object that is the copy of this vector
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }
}
