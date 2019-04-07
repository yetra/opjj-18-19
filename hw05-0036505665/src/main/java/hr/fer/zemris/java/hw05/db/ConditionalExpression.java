package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class models a conditional expression in a database query, Each expression consists of
 * three parts, for example firstName > "A".
 *
 * @author Bruna Dujmović
 *
 */
public class ConditionalExpression {

    /**
     * The getter for the student record field that will be compared in the expression.
     */
    private IFieldValueGetter fieldGetter;

    /**
     * The string literal that will be compared against a student record attribute.
     */
    private String stringLiteral;

    /**
     * The comparison operator of the expression.
     */
    private IComparisonOperator comparisonOperator;

    /**
     * Constructs a {@link ConditionalExpression} object out of the three parts of every
     * conditional expression in the databse query.
     *
     * @param fieldGetter the getter for the student record field that will be compared in the
     *                    expression
     * @param stringLiteral the string literal that will be compared against a student record field
     * @param comparisonOperator the comparison operator of the expression
     * @throws NullPointerException if any of the constructor parameters are {@code null}
     */
    public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
                                 IComparisonOperator comparisonOperator) {
        Objects.requireNonNull(fieldGetter);
        Objects.requireNonNull(stringLiteral);
        Objects.requireNonNull(comparisonOperator);

        this.fieldGetter = fieldGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Returns the getter for the student record field that will be compared in the expression.
     *
     * @return the getter for the student record field that will be compared in the expression
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    /**
     * Returns the string literal that will be compared against a student record attribute.
     *
     * @return the string literal that will be compared against a student record attribute.
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     * Returns the comparison operator of the expression.
     *
     * @return he comparison operator of the expression
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
