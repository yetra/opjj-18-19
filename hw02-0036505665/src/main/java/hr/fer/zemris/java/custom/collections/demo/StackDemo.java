package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A demonstration of the {@code ObjectStack} class.
 *
 * This program accepts a single command-line argument - a POSIX expression and
 * evaluates it.
 */
public class StackDemo {

    /**
     * Main method. Evaluates given POSIX expression string.
     * @param args the command-line arguments - one must be given (the POSIX
     *             expression string)
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("The program accepts 1 argument, " + args.length + " were given.");
            System.exit(1);
        }
        String[] expression = args[0].split("\\s+");

        ObjectStack stack = new ObjectStack();

        for (String element : expression) {

            if (element.matches("^[+-]?[0-9]+$")) {
                stack.push(Integer.parseInt(element));

            } else {
                performOperation(stack, element);
            }
        }

        if (stack.size() != 1) {
            System.out.println("Unable to evaluate expression \"" + args[0] + "\".");
            System.exit(1);
        } else {
            System.out.println("Expression evaluates to " + stack.pop() + ".");
        }
    }

    /**
     * Helper function which performs a mathematical operation on the last
     * two elements that were pushed on the stack. The result of the operation
     * is pushed back on the stack.
     *
     * @param stack the stack to pop the operands from and push the result to
     * @param element the string that represents a mathematical operation
     * @throws ArithmeticException if division by zero is attempted
     * @throws IllegalArgumentException if the operator is illegal (not in
     *         {+, -, *, /, %})
     */
    private static void performOperation(ObjectStack stack, String element) {
        try {
            int secondOperand = (int) stack.pop();
            int firstOperand = (int) stack.pop();

            int result = evaluate(element, firstOperand, secondOperand);
            stack.push(result);

        } catch (ArithmeticException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);

        } catch (IllegalArgumentException | EmptyStackException ex) {
            System.out.println("This posix expression is invalid.");
            System.exit(1);
        }
    }

    /**
     * Helper function which evaluates a mathematical expression of
     * based on a given operator string.
     *
     * @param operator the operator of the operation
     * @param firstOperand the first operand of the operation
     * @param secondOperand the second operand of the operation
     * @return the result of the mathematical operation
     * @throws ArithmeticException if division by zero is attempted
     * @throws IllegalArgumentException if the operator is illegal (not in
     *         {+, -, *, /, %})
     */
    private static int evaluate(String operator, int firstOperand, int secondOperand) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;

            case "-":
                return firstOperand - secondOperand;

            case "*":
                return firstOperand * secondOperand;

            case "/":
                if (secondOperand == 0) {
                    throw new ArithmeticException("Cannot divide by zero.");
                }
                return firstOperand / secondOperand;

            case "%":
                return firstOperand % secondOperand;

            default:
                throw new IllegalArgumentException("Illegal operator " + operator + " was given.");
        }
    }
}
