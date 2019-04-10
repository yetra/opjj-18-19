package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a parser of query strings. Each query string is passed to
 * the parser through its constructor and is separated into a list of
 * {@link ConditionalExpression} objects.
 *
 * @author Bruna Dujmović
 *
 */
public class QueryParser {

    /**
     * The query string to parse.
     */
    private String query;

    /**
     * A list of {@link ConditionalExpression} objects that the given query string was
     * parsed into.
     */
    private List<ConditionalExpression> conditionalExpressions;

    /**
     * Construct a {@link QueryParser} object for the given query string, and parses
     * the string into {@link ConditionalExpression} objects.
     *
     * @param query the query to parse
     */
    public QueryParser(String query) {
        this.query = query;
        this.conditionalExpressions = new ArrayList<>();

        parseQuery();
    }

    /**
     * Returns {@code true} if the current query is a direct query. A direct query is
     * any query of the form jmbag="xxx" (i.e. it must have only one comparison of the
     * attribute jmbag, and the operator must be equals)
     *
     * @return {@code true} if the current query is a direct query
     */
    public boolean isDirectQuery() {
        if (conditionalExpressions.size() == 1) {
            ConditionalExpression expression = conditionalExpressions.get(0);

            return expression.getFieldGetter() == FieldValueGetters.JMBAG
                    && expression.getComparisonOperator() == ComparisonOperators.EQUALS;
        }

        return false;
    }

    /**
     * Returns the string literal that was compared to the jmbag attribute of the
     * given direct query. If the given query was not direct, this method will throw
     * the {@link IllegalStateException} exception.
     *
     * @return the string literal that was compared to the jmbag attribute of the
     *         given direct query
     * @throws IllegalStateException if the given query was not direct
     */
    public String getQueriedJMBAG() {
        if (!isDirectQuery()) {
            throw new IllegalStateException("Query is not a direct query!");
        }

        return conditionalExpressions.get(0).getStringLiteral();
    }

    /**
     * Returns a list of conditional expressions parsed from the given query.
     *
     * @return a list of conditional expressions parsed from the given query
     */
    public List<ConditionalExpression> getQuery() {
        return conditionalExpressions;
    }

    /* ----------------------------------------------------------------------------
     * ------------------------------ HELPER METHODS ------------------------------
     * ----------------------------------------------------------------------------
     */

    /**
     * Separates a given query into {@link ConditionalExpression} objects and adds
     * them to the {@link #conditionalExpressions} list.
     *
     * @throws IllegalArgumentException if the given query is invalid
     */
    private void parseQuery() {
        QueryLexer lexer = new QueryLexer(query);

        do {
            Token fieldToken = lexer.nextToken();
            if (fieldToken.getType() != TokenType.FIELD_NAME) {
                throw new IllegalArgumentException(
                        "Invalid field token \"" + fieldToken.getValue() + "\"");
            }

            Token operatorToken = lexer.nextToken();
            if (operatorToken.getType() != TokenType.COMPARISON_OPERATOR) {
                throw new IllegalArgumentException(
                        "Invalid comparison operator \"" + operatorToken.getValue() + "\"");
            }

            Token stringToken = lexer.nextToken();
            if (stringToken.getType() != TokenType.STRING) {
                throw new IllegalArgumentException(
                        "Invalid string literal \"" + stringToken.getValue() + "\"");
            }

            conditionalExpressions.add(new ConditionalExpression(
                    getFieldValueGetter(fieldToken.getValue()),
                    stringToken.getValue(),
                    getComparisonOperator(operatorToken.getValue())
            ));

            Token separator = lexer.nextToken();
            if (separator.getType() != TokenType.EOF &&
                    separator.getType() != TokenType.LOGICAL_OPERATOR) {
                throw new IllegalArgumentException(
                        "Invalid logical operator \"" + separator.getValue() + "\"");
            }

        } while (lexer.getToken().getType() != TokenType.EOF);
    }

    /**
     * Returns an {@link IFieldValueGetter} instance parsed from a given field value
     * name string.
     *
     * @param fieldName the field value name to parse
     * @return an {@link IFieldValueGetter} instance parsed from a given field value
     *         name string
     * @throws IllegalArgumentException if the given field value name string cannot be
     *         parsed into a {@link IFieldValueGetter} object
     */
    private IFieldValueGetter getFieldValueGetter(String fieldName) {
        switch (fieldName) {
            case "firstName":
                return FieldValueGetters.FIRST_NAME;
            case "lastName":
                return FieldValueGetters.LAST_NAME;
            case "jmbag":
                return FieldValueGetters.JMBAG;
            default:
                throw new IllegalArgumentException(
                        "Unknown field name \"" + fieldName + "\".");
        }
    }

    /**
     * Returns an {@link IComparisonOperator} instance parsed from a given operator
     * string.
     *
     * @param operator the operator string to parse
     * @return an {@link IComparisonOperator} instance parsed from a given operator
     *         string
     * @throws IllegalArgumentException if the given operator string cannot be parsed
     *         into a {@link IComparisonOperator} object
     */
    private IComparisonOperator getComparisonOperator(String operator) {
        switch (operator) {
            case "<":
                return ComparisonOperators.LESS;
            case "<=":
                return ComparisonOperators.LESS_OR_EQUAL;
            case ">":
                return ComparisonOperators.GREATER;
            case ">=":
                return ComparisonOperators.GREATER_OR_EQUAL;
            case "=":
                return ComparisonOperators.EQUALS;
            case "!=":
                return ComparisonOperators.NOT_EQUALS;
            case "LIKE":
                return ComparisonOperators.LIKE;
            default:
                throw new IllegalArgumentException(
                        "Unknown operator name \"" + operator + "\".");
        }
    }
}
