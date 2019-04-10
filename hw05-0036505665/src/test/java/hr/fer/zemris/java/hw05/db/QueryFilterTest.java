package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryFilterTest {

    @Test
    void testNullList() {
        assertThrows(NullPointerException.class, () -> new QueryFilter(null));
    }

    @Test
    void testAllExpressionsSatisfied() {
        StudentRecord record = new StudentRecord("012345", "Last", "first", 5);

        List<ConditionalExpression> expressions = new ArrayList<>();
        expressions.add(new ConditionalExpression(
                FieldValueGetters.JMBAG,
                "012345",
                ComparisonOperators.EQUALS
        ));
        expressions.add(new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "L*st",
                ComparisonOperators.LIKE
        ));
        expressions.add(new ConditionalExpression(
                FieldValueGetters.FIRST_NAME,
                "First",
                ComparisonOperators.NOT_EQUALS
        ));

        QueryFilter filter = new QueryFilter(expressions);
        assertTrue(filter.accepts(record));
    }

    @Test
    void testOneExpressionNotSatisfied() {
        StudentRecord record = new StudentRecord("2398274", "Last", "first", 4);

        List<ConditionalExpression> expressions = new ArrayList<>();
        expressions.add(new ConditionalExpression(
                FieldValueGetters.JMBAG,
                "327346282",
                ComparisonOperators.LESS
        ));
        expressions.add(new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "First",
                ComparisonOperators.NOT_EQUALS
        ));
        expressions.add(new ConditionalExpression(
                FieldValueGetters.FIRST_NAME,
                "First",
                ComparisonOperators.EQUALS
        ));

        QueryFilter filter = new QueryFilter(expressions);
        assertFalse(filter.accepts(record));
    }

    @Test
    void testNoExpressionSatisfied() {
        StudentRecord record = new StudentRecord("20399", "SurNAME", "name", 1);

        List<ConditionalExpression> expressions = new ArrayList<>();
        expressions.add(new ConditionalExpression(
                FieldValueGetters.JMBAG,
                "1231231",
                ComparisonOperators.EQUALS
        ));
        expressions.add(new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Surname",
                ComparisonOperators.LESS
        ));
        expressions.add(new ConditionalExpression(
                FieldValueGetters.FIRST_NAME,
                "NAME",
                ComparisonOperators.GREATER_OR_EQUAL
        ));

        QueryFilter filter = new QueryFilter(expressions);
        assertFalse(filter.accepts(record));
    }
}
