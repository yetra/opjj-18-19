package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConditionalExpressionTest {

    @Test
    void testConditionalExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                ComparisonOperators.LIKE
        );

        StudentRecord record = new StudentRecord("0000000049", "Bosot", "Branimir", 2);
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record), // returns lastName from given record
                expr.getStringLiteral() // returns "Bos*"
        );

        assertTrue(recordSatisfies);
    }
}
