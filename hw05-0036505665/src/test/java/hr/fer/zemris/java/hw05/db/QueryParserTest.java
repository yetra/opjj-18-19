package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryParserTest {

    @Test
    void testQueriesWithWhitespaces() {
        String[] queries = {
                "         lastName=\"Bosnić\"",
                " lastName     =\"Bosnić\"",
                " lastName=      \"Bosnić\" ",
                " lastName=\"Bosnić\"   ",
                " lastName      =        \"Bosnić\""
        };

        ConditionalExpression expectedExpression = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bosnić",
                ComparisonOperators.EQUALS
        );

        for (String query : queries) {
            QueryParser parser = new QueryParser(query);

            List<ConditionalExpression> expressions = parser.getQuery();
            assertEquals(expectedExpression, expressions.get(0));
        }
    }

    @Test
    void testInvalidFieldName() {
        String[] queries = {
                "         jmbg=\"1312\"",
                " lastName1     =\"Bosnić\"",
                " firstname=      \"Ivana\" ",
                " finalGrade=\"1\"   ",
                "firstName=\"Ivana\" and lastname !=\"Prezime\"",
                "LIKE = \"name\"",
                "> = \"name\"",
                "firstNameLIKE \"A*\""
        };

        for (String query : queries) {
            assertThrows(IllegalArgumentException.class, () -> new QueryParser(query));
        }
    }

    @Test
    void testInvalidComparisonOperator() {
        String[] queries = {
                "         jmbag==\"1312\"",
                " lastName <> \"Bosnić\"",
                " firstName >>  \"Ivana\" ",
                " jmbag !!= \"1\"   ",
                "lastName like \"B*\"",
                "lastName LIKE \"B*\" and jmbag==\"1312\"",
                "lastName firstName \"B*\""
        };

        for (String query : queries) {
            assertThrows(IllegalArgumentException.class, () -> new QueryParser(query));
        }
    }

    @Test
    void testInvalidStringLiteral() {
        String[] queries = {
                "         jmbag =   1312",
                " lastName > Bosnić",
                " firstName !=  LIKE ",
                " jmbag != 65   ",
                "lastName LIKE LIKE",
                "lastName LIKE lastName"
        };

        for (String query : queries) {
            assertThrows(IllegalArgumentException.class, () -> new QueryParser(query));
        }
    }

    @Test
    void testInvalidLogicalOperator() {
        String[] queries = {
                "         jmbag =   \"1312\" or lastName=\"Name\"",
                " lastName > \"Bosnić\" xor jmbag=\"1\"",
                " firstName !=  \"name\" not lastName=\"name\" ",
                "firstName !=  \"name\" and lastName=\"Name\" or jmbag =   \"1312\""
        };

        for (String query : queries) {
            assertThrows(IllegalArgumentException.class, () -> new QueryParser(query));
        }
    }

    @Test
    void testInvalidQueryStructure() {
        String[] queries = {
                "         jmbag =   ",
                " > \"Bosnić\" and ",
                " firstName  ",
                "\"name\" and",
                "jmbag =   \"1312\" firstName !=  \"name\" ",
                "lastName=\"Name\" and",
                " lastName > \"Bosnić\" and jmbag =  "
        };

        for (String query : queries) {
            assertThrows(IllegalArgumentException.class, () -> new QueryParser(query));
        }
    }

    @Test
    void testCombinedQuery() {
        String query = "    jmbag > \"1312\" AND firstName LIKE \"B*a\" " +
                "AnD lastName <= \"Žubrinić\" and lastName != \"Test\"";

        List<ConditionalExpression> expectedExpressions = new ArrayList<>();

        expectedExpressions.add(new ConditionalExpression(
                FieldValueGetters.JMBAG,
                "1312",
                ComparisonOperators.GREATER
        ));
        expectedExpressions.add(new ConditionalExpression(
                FieldValueGetters.FIRST_NAME,
                "B*a",
                ComparisonOperators.LIKE
        ));
        expectedExpressions.add(new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Žubrinić",
                ComparisonOperators.LESS_OR_EQUAL
        ));
        expectedExpressions.add(new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Test",
                ComparisonOperators.NOT_EQUALS
        ));

        QueryParser parser = new QueryParser(query);
        List<ConditionalExpression> result = parser.getQuery();

        assertEquals(expectedExpressions, result);
    }
}
