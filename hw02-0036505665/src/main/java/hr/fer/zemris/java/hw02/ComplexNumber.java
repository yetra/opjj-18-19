package hr.fer.zemris.java.hw02;

/**
 * A class that models a complex number.
 *
 * @author Bruna Dujmović
 *
 */
public class ComplexNumber {

    /**
     * Real part of the complex number.
     */
    private final double real;

    /**
     * Imaginary part of the complex number.
     */
    private final double imaginary;

    /**
     * Magnitude of the complex number.
     */
    private final double magnitude;

    /**
     * Angle of the complex number.
     */
    private final double angle;

    /**
     * Constructor which creates a complex number with a real and imaginary part.
     *
     * @param real the real part of the complex number
     * @param imaginary the imaginary part of the complex number
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;

        magnitude = Math.sqrt(real*real + imaginary*imaginary);
        // TODO [-pi/2, pi/2] => [0, 2pi]
        angle = Math.atan2(imaginary, real);
    }

    /**
     * Creates a complex number from a given real part.
     *
     * @param real the real part of the complex number
     * @return the complex number created from the given real part
     */
    public static ComplexNumber fromReal(double real) {
        return new ComplexNumber(real, 0.0);
    }

    /**
     * Creates a complex number from a given imaginary part.
     *
     * @param imaginary the imaginary part of the complex number
     * @return the complex number created from the given imaginary part
     */
    public static ComplexNumber fromImaginary(double imaginary) {
        return new ComplexNumber(0.0, imaginary);
    }

    /**
     * Creates a complex number from a given magnitude and angle.
     *
     * @param magnitude the magnitude of the complex number
     * @param angle the angle of the complex number
     * @return the complex number created from the given magnitude and angle
     */
    public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
        double real = magnitude * Math.cos(angle);
        double imaginary = magnitude * Math.sin(angle);

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Parses a given string into a complex number.
     *
     * @param s the string to parse
     * @return the complex number that was parsed from the string
     */
    public static ComplexNumber parse(String s) {
        //TODO with Scanner or regexps
        return null;
    }

    /**
     * Returns the real part of this complex number.
     *
     * @return the real part of this complex number
     */
    public double getReal() {
        return real;
    }

    /**
     * Returns the imaginary part of this complex number.
     *
     * @return the imaginary part of this complex number
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Returns the magnitude of this complex number.
     *
     * @return the magnitude of this complex number
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Returns the angle of this complex number.
     *
     * @return the angle of this complex number
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Adds a given complex number to this complex number.
     *
     * @param c the complex number to add to this complex number
     * @return the sum of this complex number and the given complex number
     */
    public ComplexNumber add(ComplexNumber c) {
        double real = this.real + c.getReal();
        double imaginary = this.imaginary + c.getImaginary();

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Subtracts a given complex number from this complex number.
     *
     * @param c the complex number that is subtracted from this complex number
     * @return the difference
     */
    public ComplexNumber sub(ComplexNumber c) {
        double real = this.real - c.getReal();
        double imaginary = this.imaginary - c.getImaginary();

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Multiplies this complex number with a given complex number.
     *
     * @param c the complex number to multiply this complex number with
     * @return the product of this complex number and the given complex number
     */
    public ComplexNumber mul(ComplexNumber c) {
        double real = this.real*c.getReal() - this.imaginary*c.getImaginary();
        double imaginary = this.real*c.getImaginary() + this.imaginary*c.getReal();

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Divides this complex number with a given complex number.
     *
     * @param c the complex number to divide this complex number with
     * @return the quotient
     */
    public ComplexNumber div(ComplexNumber c) {
        double realNumerator = this.real*c.getReal() + this.imaginary*c.getImaginary();
        double imaginaryNumerator = this.imaginary*c.getReal() - this.real*c.getImaginary();
        double denominator = c.getReal()*c.getReal() + c.getImaginary()*c.getImaginary();

        return new ComplexNumber(realNumerator/denominator, imaginaryNumerator/denominator);
    }

    /**
     * Calculates the nth power of this complex number.
     *
     * @param n the power to calculate
     * @return the nth power of this complex number
     */
    public ComplexNumber power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Power must not be negative.");
        }

        double powMagnitude = Math.pow(magnitude, n);
        double real = powMagnitude * Math.cos(n*angle);
        double imaginary = powMagnitude * Math.sin(n*angle);

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Calculates the nth roots of this complex number.
     *
     * @param n the root to calculate
     * @return an array of the nth roots of this complex number
     */
    public ComplexNumber[] root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Root must not be negative or zero.");
        }

        ComplexNumber[] nthRoot = new ComplexNumber[n];

        double nthRootOfMagnitude = Math.pow(magnitude, 1.0/n);
        for (int k = 0; k < n; k++) {
            double kthAngle = (angle + 2*Math.PI*k) / n;
            double kthReal = nthRootOfMagnitude * Math.cos(kthAngle);
            double kthImaginary = nthRootOfMagnitude * Math.sin(kthAngle);

            nthRoot[k] = new ComplexNumber(kthReal, kthImaginary);
        }

        return nthRoot;
    }

    @Override
    public String toString() {
        //TODO
        return "";
    }
}
