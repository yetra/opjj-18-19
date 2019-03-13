package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A program that calculates and prints the factorials of integers between 3 and 20.
 * Inputs are accepted through the console. Typing "kraj" ends the program.
 *
 * @author Bruna Dujmović
 *
 */

public class Factorial {

    /**
     * Main method. Reads from the console and prints the factorials of numbers between 3 and 20.
     * @param args command-line arguments, not used
     */
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.format("Unesite broj > ");

            if (sc.hasNextInt()) {
                int number = sc.nextInt();

                if (number >= 3 && number <= 20) {
                    System.out.format("%d! = %d%n", number, factorial(number));
                } else {
                    System.out.format("'%d' nije broj u dozvoljenom rasponu.%n", number);
                }

            } else {
                String input = sc.next();

                if (input.equals("kraj")) {
                    System.out.println("Doviđenja.");
                    break;
                }
                System.out.format("'%s' nije cijeli broj.%n", input);
            }
        }

        sc.close();
    }

    /**
     * Calculates the factorial of a given non-negative integer.
     * @param number number whose factorial should be calculated
     * @return factorial of the given number
     */
    public static long factorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number can't be negative.");
        }

        long factorial = 1L;
        while (number > 0) {
            factorial *= number;
            number -= 1;
        }

        return factorial;
    }
}
