package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A program that calculates and prints a given rectangle's area and perimeter.
 * Input the width and height of the rectangle either as command-line arguments or though the console.
 *
 * @author Bruna Dujmović
 *
 */

public class Rectangle {

    /**
     * Main method. Determines a rectangle's dimensions and prints its data to the console.
     * @param args command-line arguments interpreted as width and height, if any are given
     */
    public static void main (String[] args) {
        double width = 0;
        double height = 0;

        if (args.length == 0) {
            try (Scanner sc = new Scanner(System.in)) {
                width = readRectangleDimension(sc,"širinu");
                height = readRectangleDimension(sc,"duljinu");
            }

        } else if (args.length == 2) {
            try {
                width = Double.parseDouble(args[0]);
                height = Double.parseDouble(args[1]);
            } catch (NumberFormatException ex) {
                System.out.println("Argumenti komandne linije ne mogu se protumačiti kao brojevi.");
                System.exit(1);
            }

        } else {
            System.out.println("Wrong number of input arguments.");
            System.exit(1);
        }

        System.out.format("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.%n",
                width, height, area(width, height), perimeter(width, height));
    }

    /**
     * Reads a rectangle dimension using a Scanner object.
     * @param scanner Scanner object that is used to read the dimension
     * @param dimension rectangle dimension to read (e.g. "širinu" or "visinu")
     * @return dimension value that was read from the console
     */
    private static double readRectangleDimension(Scanner scanner, String dimension) {
        while (true) {
            System.out.format("Unesite %s > ", dimension);

            if (scanner.hasNext()) {
                String input = scanner.next();

                try {
                    double value = Double.parseDouble(input);

                    if (value < 0) {
                        System.out.println("Unijeli ste negativnu vrijednost.");
                        continue;
                    }
                    return value;

                } catch (NumberFormatException ex) {
                    System.out.format("'%s' se ne može protumačiti kao broj.%n", input);
                }
            }
        }
    }

    /**
     * Calculates the perimeter of a given rectangle.
     * @param width width of the rectangle
     * @param height height of the rectangle.
     * @return perimeter of the rectangle
     */
    private static double perimeter(double width, double height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Rectangle dimensions can't be negative.");
        }

        return 2 * (width + height);
    }

    /**
     * Calculates the area of a given rectangle.
     * @param width width of the rectangle
     * @param height height of the rectangle.
     * @return area of the rectangle
     */
    private static double area(double width, double height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Rectangle dimensions can't be negative.");
        }

        return width * height;
    }
}
