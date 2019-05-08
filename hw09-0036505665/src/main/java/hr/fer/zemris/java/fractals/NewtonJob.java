package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class represents a task to be called in the {@link NewtonProducer} class for
 * drawing a Newton fractal on a specified complex plane.
 *
 * It generates the Newton-Raphson iterations for a complex polynomial f(z) using the
 * formula z_n+1 = z_n - f(z_n) / f'(z_n) until {@link #maxIterations} is reached or
 * the module |z_n+1 - z_n| becomes adequately small (less than or equal to {@link
 * #CONVERGENCE_THRESHOLD}).
 *
 * Once stopped, it finds the closest root for the final point z_n and colors the point
 * based on the index of that root. If it stopped on a z_n that is further than the
 * predefined {@link #ROOT_THRESHOLD}, the point will be colored with a color associated
 * with the index 0.
 *
 * @author Bruna Dujmović
 *
 */
public class NewtonJob implements Callable<Void> {

    /**
     * The convergence threshold for the Newton-Raphson iterations.
     */
    private static final double CONVERGENCE_THRESHOLD = 0.001;
    /**
     * The root threshold for finding the index of the closest root for final point z_n.
     */
    private static final double ROOT_THRESHOLD = 0.002;

    /**
     * The minimum real value of the complex plane.
     */
    private double reMin;
    /**
     * The maximum real value of the complex plane.
     */
    private double reMax;

    /**
     * The minimum imaginary value of the complex plane.
     */
    private double imMin;
    /**
     * The maximum imaginary value of the complex plane.
     */
    private double imMax;

    /**
     * The width of the complex plane.
     */
    private int width;
    /**
     * The height of the complex plane.
     */
    private int height;

    /**
     * The initial y-coordinate for the currently observed section of the complex plane.
     */
    private int yMin;
    /**
     * The final y-coordinate for the currently observed section of the complex plane.
     */
    private int yMax;

    /**
     * The maximum number of Newton-Raphson iterations.
     */
    private int maxIterations;

    /**
     * An array to store the root indexes, each representing the color of a given point.
     */
    private short[] data;

    /**
     * The {@link ComplexRootedPolynomial} form of the given polynomial.
     */
    private ComplexRootedPolynomial rootedPolynomial;
    /**
     * The {@link ComplexPolynomial} form of the given polynomial.
     */
    private ComplexPolynomial polynomial;

    /**
     * A flag that signals that the task should be terminated if set to {@code true}.
     */
    private AtomicBoolean cancel;

    /**
     * Constructs a {@link NewtonJob} with all the necessary parameters.
     *
     * @param reMin the minimum real value of the complex plane
     * @param reMax the maximum real value of the complex plane
     * @param imMin the minimum imaginary value of the complex plane
     * @param imMax the maximum imaginary value of the complex plane
     * @param width the width of the complex plane
     * @param height the height of the complex plane
     * @param yMin the initial y-coordinate for the currently observed section of the complex plane
     * @param yMax the final y-coordinate for the currently observed section of the complex plane
     * @param maxIterations the maximum number of Newton-Raphson iterations
     * @param data an array to store the root indexes, each representing the color of a given point
     * @param polynomial the {@link ComplexPolynomial} form of the given polynomial
     * @param rootedPolynomial the {@link ComplexRootedPolynomial} form of the given polynomial
     * @param cancel a flag that signals that the task should be terminated if set to {@code true}
     */
    public NewtonJob(double reMin, double reMax, double imMin, double imMax,
                     int width, int height, int yMin, int yMax, int maxIterations,
                     short[] data, ComplexPolynomial polynomial,
                     ComplexRootedPolynomial rootedPolynomial, AtomicBoolean cancel) {

        this.reMin = reMin;
        this.reMax = reMax;
        this.imMin = imMin;
        this.imMax = imMax;
        this.width = width;
        this.height = height;
        this.yMin = yMin;
        this.yMax = yMax;
        this.maxIterations = maxIterations;
        this.data = data;
        this.rootedPolynomial = rootedPolynomial;
        this.polynomial = polynomial;
        this.cancel = cancel;
    }

    @Override
    public Void call() {
        for (int y = yMin; y <= yMax; y++) {
            for (int x = 0; x <= width; x++) {
                Complex zn = mapToComplexPlane(x, y);
                ComplexPolynomial derived = polynomial.derive();

                double module;
                int iteration = 0;

                do {
                    Complex numerator = polynomial.apply(zn);
                    Complex denominator = derived.apply(zn);

                    Complex znOld = zn;
                    zn = zn.sub(numerator.divide(denominator));

                    module = znOld.sub(zn).module();
                    iteration++;

                } while (module > CONVERGENCE_THRESHOLD && iteration < maxIterations);

                int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_THRESHOLD);
                data[x + y * height] = (index == -1) ? 0 : (short) (index + 1);
                // TODO explan index of data array?
            }
        }

        return null;
    }

    /**
     * Maps a given x- and y-coordinate to the complex plane.
     *
     * @param x the x-coordinate to map
     * @param y the y-coordinate to map
     * @return a {@link Complex} object representing the mapped point on the plane
     */
    private Complex mapToComplexPlane(int x, int y) {
        double re = x * (reMax - reMin) / (width - 1) + reMin;
        double im = (height - 1 - y) * (imMax - imMin) / (height - 1) + imMin;

        return new Complex(re, im);
    }
}
