package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This program displays the image of a Newton fractal drawn from a polynomial whose
 * roots are given through the console.
 *
 * @author Bruna Dujmović
 *
 */
public class Newton {

    /**
     * The main method. Receives the polynomial roots from the user by reading from the
     * console, starts the fractal viewer, and displays the Newton fractal.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {

        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots, one root per line. " +
                "Enter 'done' when done.");

        int rootNumber = 1;
        List<Complex> roots = new ArrayList<>();

        try (Scanner sc = new Scanner(System.in)) {
            String line;

            System.out.print("Root " + rootNumber++ + "> ");
            while (!(line = sc.nextLine()).equals("done")) {
                try {
                    roots.add(parseRoot(line));

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }

                System.out.print("Root " + rootNumber++ + "> ");
            }

            if (rootNumber < 2) {
                System.out.println("Not enough roots, need at least 2!");
                System.exit(1);
            }
        }

        FractalViewer.show(
                new NewtonProducer(
                        new ComplexRootedPolynomial(
                                Complex.ONE,
                                roots.toArray(new Complex[0])
                        )
                )
        );
    }

    /**
     * Parses a given root string into a {@link Complex} object.
     *
     * @param rootString the root string to parse
     * @return the {@link Complex} object parsed from the root string
     */
    private static Complex parseRoot(String rootString) {
        String normalized = rootString.replaceAll("\\s+", "").replaceAll("i$", "i1");

        try {
            if (normalized.matches("(.*\\d+[+-]i\\d+.*$)|(^-?i\\d+.*)")) {
                int middle = normalized.indexOf('i') - 1;
                normalized = normalized.replace("i", "");

                if (middle < 1) {
                    double im = Double.parseDouble(normalized);
                    return new Complex(0.0, im);

                } else {
                    double re = Double.parseDouble(normalized.substring(0, middle));
                    double im = Double.parseDouble(normalized.substring(middle));
                    return new Complex(re, im);
                }

            } else {
                double re = Double.parseDouble(normalized);
                return new Complex(re, 0.0);
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid root string!");
        }
    }
}
