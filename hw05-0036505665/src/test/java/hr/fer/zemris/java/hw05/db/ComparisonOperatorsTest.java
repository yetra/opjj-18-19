package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonOperatorsTest {

    @Test
    void testLess() {
        String[] firstString = {"abcd123", "ABBBB", "1241", "000"};
        String[] secondString = {"bcde123", "AAAAA", "092.381", "000"};
        boolean[] expected = {true, false, false, false};

        IComparisonOperator oper = ComparisonOperators.LESS;

        for (int i = 0; i < firstString.length; i++) {
            assertEquals(expected[i], oper.satisfied(firstString[i], secondString[i]));
        }
    }

    @Test
    void testLessOrEqual() {
        String[] firstString = {"mMMM", "AAAAAA", "1241", "abc", "Def."};
        String[] secondString = {"Mmm", "AAAAA", "1241", "ABC", "Def."};
        boolean[] expected = {false, false, true, false, true};

        IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUAL;

        for (int i = 0; i < firstString.length; i++) {
            assertEquals(expected[i], oper.satisfied(firstString[i], secondString[i]));
        }
    }

    @Test
    void testGreater() {
        String[] firstString = {"mMMM", "AAAA", "9876", "pop", "*text"};
        String[] secondString = {"Mmm", "aaaa", "9875", "pOp", "*text"};
        boolean[] expected = {true, false, true, true, false};

        IComparisonOperator oper = ComparisonOperators.GREATER;

        for (int i = 0; i < firstString.length; i++) {
            assertEquals(expected[i], oper.satisfied(firstString[i], secondString[i]));
        }
    }

    @Test
    void testGreaterOrEqual() {
        String[] firstString = {"AAAA", ".123/", ".123/", "pOp", "*text"};
        String[] secondString = {"AAAAA", ".123/", ".123", "pop", "*text"};
        boolean[] expected = {false, true, true, false, true};

        IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUAL;

        for (int i = 0; i < firstString.length; i++) {
            assertEquals(expected[i], oper.satisfied(firstString[i], secondString[i]));
        }
    }

    @Test
    void testEquals() {
        String[] firstString = {"AAAA", "AAAA", "asdas", "23.1-0=1", "POP"};
        String[] secondString = {"AAAAA", "AAAA", "dfsdfs", "23.1-0=1", "pop"};
        boolean[] expected = {false, true, false, true, false};

        IComparisonOperator oper = ComparisonOperators.EQUALS;

        for (int i = 0; i < firstString.length; i++) {
            assertEquals(expected[i], oper.satisfied(firstString[i], secondString[i]));
        }
    }

    @Test
    void testNotEquals() {
        String[] firstString = {"121-230", "pop", "123//=", "pqow\"qw[", "text"};
        String[] secondString = {"apedo", "poP", "123//=", "pqow\"qw[", "teext"};
        boolean[] expected = {true, true, false, false, true};

        IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;

        for (int i = 0; i < firstString.length; i++) {
            assertEquals(expected[i], oper.satisfied(firstString[i], secondString[i]));
        }
    }

    @Test
    void testValidLike() {
        String[] firstString = {"Zagreb", "AAA", "AAAA", "Text", "="};
        String[] secondString = {"Aba*", "AA*AA", "AA*AA", "*Text", "*"};
        boolean[] expected = {false, false, true, true, true};

        IComparisonOperator oper = ComparisonOperators.LIKE;

        for (int i = 0; i < firstString.length; i++) {
            assertEquals(expected[i], oper.satisfied(firstString[i], secondString[i]));
        }
    }

    @Test
    void testInvalidLike() {
        IComparisonOperator oper = ComparisonOperators.LIKE;

        assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Zagreb", "Aba**"));
        assertThrows(IllegalArgumentException.class, () -> oper.satisfied("AAA", "*AA*AA"));
        assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Text", "***Text"));
        assertThrows(IllegalArgumentException.class, () -> oper.satisfied("=", "**"));
    }
}
