package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class models an immutable complex number. All the mathematical operations performed
 * on the objects of this class will return a new {@link Complex} object as the result,
 * without changing the current object.
 *
 * @author Bruna Dujmović
 *
 */
public class Complex {

    /**
     * A {@link Complex} object representing the number 0.
     */
    public static final Complex ZERO = new Complex(0,0);

    /**
     * A {@link Complex} object representing the number 1.
     */
    public static final Complex ONE = new Complex(1,0);

    /**
     * A {@link Complex} object representing the number -1.
     */
    public static final Complex ONE_NEG = new Complex(-1,0);

    /**
     * A {@link Complex} object representing the number i.
     */
    public static final Complex IM = new Complex(0,1);

    /**
     * A {@link Complex} object representing the number -i.
     */
    public static final Complex IM_NEG = new Complex(0,-1);

    /**
     * The real part of this complex number.
     */
    private double re;

    /**
     * The imaginary part of this complex number.
     */
    private double im;

    /**
     * The default constructor for the {@link Complex} class. Creates the complex
     * number 0.
     */
    public Complex() {}

    /**
     * Constructs a complex number of the given real and imaginary parts.
     *
     * @param re the real part of the complex number
     * @param im the imaginary part of the complex number
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Returns the module (absolute value) of this complex number.
     *
     * @return returns the module of this complex number
     */
    public double module() {
        return Math.sqrt(re * re + im * im);
    }

    /**
     * Multiplies this complex number with the given complex number and returns the
     * result in a new {@link Complex} object.
     *
     * @param c the complex number to multiply with
     * @return a new {@link Complex} object that is the result of the multiplication
     * @throws NullPointerException if the given complex number is {@code null}
     */
    public Complex multiply(Complex c) {
        Objects.requireNonNull(c);

        return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
    }

    /**
     * Divides this complex number with the given complex number and returns the
     * result in a new {@link Complex} object.
     *
     * @param c the complex number to divide with
     * @return a new {@link Complex} object that is the result of the division
     * @throws NullPointerException if the given complex number is {@code null}
     */
    public Complex divide(Complex c) {
        Objects.requireNonNull(c);

        double reNumerator = re * c.re + im * c.im;
        double imNumerator = im * c.re - re * c.im;
        double denominator = c.re * c.re + c.im * c.im;

        return new Complex(reNumerator / denominator, imNumerator / denominator);
    }

    /**
     * Adds this complex number with the given complex number and returns the result
     * in a new {@link Complex} object.
     *
     * @param c the complex number to add
     * @return a new {@link Complex} object that is the result of the addition
     * @throws NullPointerException if the given complex number is {@code null}
     */
    public Complex add(Complex c) {
        Objects.requireNonNull(c);

        return new Complex(re + c.re, im + c.im);
    }

    /**
     * Subtracts the given complex number from this complex number and returns the
     * result in a new {@link Complex} object.
     *
     * @param c the complex number to subtract
     * @return a new {@link Complex} object that is the result of the subtraction
     * @throws NullPointerException if the given complex number is {@code null}
     */
    public Complex sub(Complex c) {
        Objects.requireNonNull(c);

        return new Complex(re - c.re, im - c.im);
    }

    /**
     * Returns the negation of this complex number.
     *
     * @return the negation of this complex number
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * Returns the n-th power of this complex number.
     *
     * @param n the power to calculate
     * @return the n-th power of this complex number
     * @throws IllegalArgumentException if the given power is negative
     */
    public Complex power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The power must be a non-negative integer!");
        }

        double angle = Math.atan2(im, re);
        double modulePow = Math.pow(module(), n);

        return new Complex(
                modulePow * Math.cos(n * angle),
                modulePow * Math.sin(n * angle)
        );
    }

    /**
     * Returns the n-th roots of this complex number in a list.
     *
     * @param n the root to calculate
     * @return a list of the n-th roots of this complex number
     * @throws IllegalArgumentException if the given root is negative or zero
     */
    public List<Complex> root(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("The root must be a positive integer!");
        }

        List<Complex> results = new ArrayList<>(n);
        double angle = Math.atan2(im, re);
        double moduleRoot = Math.pow(module(), 1.0/n);

        for (int k = 0; k < n; k++) {
            double kthAngle = (angle + 2 * Math.PI * k) / n;
            double kthReal = moduleRoot * Math.cos(kthAngle);
            double kthImaginary = moduleRoot * Math.sin(kthAngle);

            results.add(new Complex(kthReal, kthImaginary));
        }

        return results;
    }

    @Override
    public String toString() {
        return re + (im >= 0 ? "+" : "-") + "i" + Math.abs(im);
    }
}
