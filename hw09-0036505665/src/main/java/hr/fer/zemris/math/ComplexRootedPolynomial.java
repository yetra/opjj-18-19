package hr.fer.zemris.math;

public class ComplexRootedPolynomial {

    // ...

    // constructor
    public ComplexRootedPolynomial(Complex constant, Complex ... roots) {

    }

    // computes polynomial value at given point z
    public Complex apply(Complex z) {
        return null;
    }

    // converts this representation to ComplexPolynomial type
    public ComplexPolynomial toComplexPolynom() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    // finds index of closest root for given complex number z that is within
    // treshold; if there is no such root, returns -1
    // first root has index 0, second index 1, etc
    public int indexOfClosestRootFor(Complex z, double treshold) {
        return 0;
    }
}
