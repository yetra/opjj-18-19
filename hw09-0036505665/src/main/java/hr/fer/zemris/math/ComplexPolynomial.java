package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class models a polynomial with complex coefficients that is constructed from
 * its factors.
 *
 * The form of the polynomial is f(z) = zn * z^n + .. + z2 * z^2 + z1 * z + z0,
 * where z0 to zn are its factors.
 *
 * @author Bruna Dujmović
 *
 */
public class ComplexPolynomial {

    /**
     * The factors of this polynomial.
     */
    private Complex[] factors;

    /**
     * Constructs a polynomial of the given complex factors. The given factors are
     * interpreted from left to right meaning that the leftmost parameter is z0, the
     * following parameter is z1, then z2 etc.
     *
     * @param factors the factors of the polynomial
     */
    public ComplexPolynomial(Complex... factors) {
        this.factors = Objects.requireNonNull(factors);
    }

    /**
     * Returns the order of this polynomial. For example, this method would return 3
     * for the polynomial (7+2i)z^3+2z^2+5z+1.
     *
     * @return the order of this polynomial
     */
    public short order() {
        return (short) factors.length;
    }

    /**
     * Multiplies this polynomial with the given polynomial and returns the result in
     * a new {@link ComplexPolynomial} object.
     *
     * @param p the polynomial to multiply with
     * @return a {@link ComplexPolynomial} object that is the result of the multiplication
     * @throws NullPointerException if the given polynomial is {@code null}
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Objects.requireNonNull(p);

        Complex[] resultFactors = new Complex[factors.length + p.factors.length - 1];
        for (int i = 0; i < factors.length; i++) {
            for (int j = 0; j < p.factors.length; j++) {
                Complex result = factors[i].multiply(p.factors[j]);

                resultFactors[i + j] = (resultFactors[i + j] == null) ?
                        result : resultFactors[i + j].add(result);
            }
        }

        return new ComplexPolynomial(resultFactors);
    }

    /**
     * Returns the first derivative of this polynomial.
     *
     * @return the first derivative of this polynomial
     */
    public ComplexPolynomial derive() {
        if (order() == 1) {
            return new ComplexPolynomial(Complex.ZERO);
        }
        Complex[] newFactors = new Complex[factors.length - 1];

        for (int i = 1; i < factors.length; i++) {
            newFactors[i - 1] = new Complex(i, 0).multiply(factors[i]);
        }

        return new ComplexPolynomial(newFactors);
    }

    /**
     * Returns the value of this polynomial at a given complex point.
     *
     * @param z the point to calculate the value for
     * @return the value of the polynomial at a given complex point
     * @throws NullPointerException if the given point is {@code null}
     */
    public Complex apply(Complex z) {
        Objects.requireNonNull(z);

        Complex result = factors[0];
        for (int i = 1; i < factors.length; i++) {
            result = result.add(factors[i].multiply(z.power(i)));
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = factors.length - 1; i > 0; i--) {
            sb.append("(").append(factors[i]).append(")*z^").append(i).append("+");
        }
        sb.append("(").append(factors[0]).append(")");

        return sb.toString();
    }
}
