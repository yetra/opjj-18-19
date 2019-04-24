package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;

/**
 * This class contains {@link BinaryOperator}s that represent all possible arithmetic
 * operations on {@link ValueWrapper} objects.
 *
 * @author Bruna Dujmović
 *
 */
class Operations {

    /**
     * Addition of two Double values.
     */
    static final BinaryOperator<Double> ADD = (first, second) -> first + second;

    /**
     * Subtraction of two Double values.
     */
    static final BinaryOperator<Double> SUB = (first, second) -> first - second;

    /**
     * Multiplication of two Double values.
     */
    static final BinaryOperator<Double> MUL = (first, second) -> first * second;

    /**
     * Division of two Double values.
     */
    static final BinaryOperator<Double> DIV = (first, second) -> first / second;

}
