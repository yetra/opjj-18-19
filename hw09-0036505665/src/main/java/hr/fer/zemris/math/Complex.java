package hr.fer.zemris.math;

import java.util.List;

public class Complex {

    public static final Complex ZERO = new Complex(0,0);
    public static final Complex ONE = new Complex(1,0);
    public static final Complex ONE_NEG = new Complex(-1,0);
    public static final Complex IM = new Complex(0,1);
    public static final Complex IM_NEG = new Complex(0,-1);

    public Complex() {

    }

    public Complex(double re, double im) {

    }

    // returns module of complex number
    public double module() {
        return 0.0;
    }

    // returns this*c
    public Complex multiply(Complex c) {
        return null;
    }

    // returns this/c
    public Complex divide(Complex c) {
        return null;
    }

    // returns this+c
    public Complex add(Complex c) {
        return null;
    }

    // returns this-c
    public Complex sub(Complex c) {
        return null;
    }

    // returns -this
    public Complex negate() {
        return null;
    }

    // returns this^n, n is non-negative integer
    public Complex power(int n) {
        return null;
    }

    // returns n-th root of this, n is positive integer
    public List<Complex> root(int n) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
