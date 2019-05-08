package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class models a polynomial with complex coefficients that is constructed from
 * a given complex constant and roots.
 *
 * The form of the polynomial is f(z) = z0 * (z - z1) * (z - z2) * ... * (z - zn),
 * where z0 is the constant and z1 to zn are its roots.
 *
 * @author Bruna Dujmović
 *
 */
public class ComplexRootedPolynomial {

    /**
     * The constant of this polynomial.
     */
    private Complex constant;

    /**
     * The roots of this polynomial.
     */
    private Complex[] roots;

    /**
     * Constructs a polynomial of the given complex constant and roots.
     *
     * @param constant the constant of the polynomial
     * @param roots the roots of the polynomial
     * @throws NullPointerException if the given constant or roots are {@code null}
     */
    public ComplexRootedPolynomial(Complex constant, Complex... roots) {
        this.constant = Objects.requireNonNull(constant);
        this.roots = Objects.requireNonNull(roots);
    }

    /**
     * Returns the value of this polynomial at a given complex point.
     *
     * @param z the point to calculate the value for
     * @return the value of this polynomial at a given complex point
     * @throws NullPointerException if the given point is {@code null}
     */
    public Complex apply(Complex z) {
        Objects.requireNonNull(z);

        Complex result = constant;
        for (Complex root : roots) {
            result = result.add(z.sub(root));
        }

        return result;
    }

    /**
     * Converts this polynomial to a {@link ComplexPolynomial} object.
     *
     * @return the {@link ComplexPolynomial} object that represents this polynomial
     */
    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial result = new ComplexPolynomial(constant);

        for (Complex root : roots) {
            result = result.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(").append(constant).append(")");

        for (Complex root : roots) {
            sb.append("*").append("(z-(").append(root).append("))");
        }
        
        return sb.toString();
    }

    /**
     * Finds the index of the closest root of this complex number which is within
     * the specified {@code threshold} for the given point {@code z}.
     *
     * Specifically, this method finds the index i of the root that has the smallest
     * value |z - root[i]| that is less than the threshold. If no such root exists, -1
     * is returned.
     *
     * @param z the point to calculate the value with
     * @param threshold the value of the threshold
     * @return the index of the closest root which is within the given threshold, or
     *         -1 if no such root exists
     * @throws NullPointerException if the given point is {@code null}
     * @throws IllegalArgumentException if the given threshold is less than zero
     */
    public int indexOfClosestRootFor(Complex z, double threshold) {
        Objects.requireNonNull(z);
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must be a non-negative number!");
        }

        int index = -1;
        double minDistance = threshold;

        for (int i = 0; i < roots.length; i++) {
            double distance = z.sub(roots[i]).module();

            if (distance < minDistance) {
                minDistance = distance;
                index = i;
            }
        }

        return index;
    }
}
