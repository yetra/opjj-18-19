package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;

class Operations {

    static final BinaryOperator<Double> ADD = (first, second) -> first + second;

    static final BinaryOperator<Double> SUB = (first, second) -> first - second;

    static final BinaryOperator<Double> MUL = (first, second) -> first * second;

    static final BinaryOperator<Double> DIV = (first, second) -> first / second;

}
