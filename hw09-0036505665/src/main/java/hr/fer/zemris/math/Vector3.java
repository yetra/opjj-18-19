package hr.fer.zemris.math;

/**
 * This class models and immutable 3D vector. All the mathematical operations performed
 * on the objects of this class will return a new {@link Vector3} object as the result,
 * without changing the respective object.
 *
 * @author Bruna Dujmović
 *
 */
public class Vector3 {

    /**
     * The x coordinate of this vector.
     */
    private double x;

    /**
     * The y coordinate of this vector.
     */
    private double y;

    /**
     * The z coordinate of this vector.
     */
    private double z;

    /**
     * Constructs a vector with three coordinates. The origin is (0, 0, 0) by default.
     *
     * @param x the x coordinate of this vector
     * @param y the y coordinate of this vector
     * @param z the z coordinate of this vector
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the norm (length) of this vector.
     *
     * @return the norm (length) of this vector
     */
    public double norm() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Returns a new {@link Vector3} that represents the normalization of this vector.
     *
     * @return a new {@link Vector3} that represents the normalization of this vector
     */
    public Vector3 normalized() {
        return scale(1.0 / norm());
    }

    /**
     * Adds this vector with the given vector and returns the result in a new {@link
     * Vector3} object.
     *
     * @param other the vector to add
     * @return a new {@link Vector3} object that is the result of the addition
     */
    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Subtracts the given vector from this vector and returns the result in a new
     * {@link Vector3} object.
     *
     * @param other the vector to subtract
     * @return a new {@link Vector3} object that is the result of the subtraction
     */
    public Vector3 sub(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Performs the dot product operation on this vector and a given {@code other},
     * and returns the result in a new {@link Vector3} object.
     *
     * @param other the vector to perform the dot product with
     * @return a new {@link Vector3} object that is the result of the dot product
     */
    public double dot(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Performs the cross product operation on this vector and a given {@code other},
     * and returns the result in a new {@link Vector3} object.
     *
     * @param other the vector to perform the dot cross with
     * @return a new {@link Vector3} object that is the result of the cross product
     */
    public Vector3 cross(Vector3 other) {
        double newX = y * other.z - z * other.y;
        double newY = z * other.x - x * other.z;
        double newZ = x * other.y - y * other.x;

        return new Vector3(newX, newY, newZ);
    }

    /**
     * Scales this vector by a given factor and returns the result in a new {@link
     * Vector3} object.
     *
     * @param s the factor to scale by
     * @return a new {@link Vector3} object that is the result of the scaling
     */
    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    /**
     * Returns the cosine of the angle between this vector and a given {@code other}.
     *
     * @param other the other vector for calculating the cosine of the angle
     * @return the cosine of the angle between this vector and the given {@code other}
     */
    public double cosAngle(Vector3 other) {
        return dot(other) / (norm() * other.norm());
    }

    /**
     * Returns the x coordinate of this vector.
     *
     * @return the x coordinate of this vector
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate of this vector.
     *
     * @return the y coordinate of this vector
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the z coordinate of this vector.
     *
     * @return the z coordinate of this vector
     */
    public double getZ() {
        return z;
    }

    /**
     * Returns an array representation of this vector of the following form: [x, y, z].
     *
     * @return an array representation of this vector
     */
    public double[] toArray() {
        return new double[] {x, y, z};
    }

    @Override
    public String toString() {
        return String.format("(%.6f, %.6f, %.6f)", x, y, z);
    }
}
