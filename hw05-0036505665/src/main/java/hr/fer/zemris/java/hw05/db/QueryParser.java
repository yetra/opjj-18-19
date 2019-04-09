package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexerException;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

    private String queryExpression;

    private List<ConditionalExpression> conditionalExpressions;

    public QueryParser(String queryExpression) {
        this.queryExpression = queryExpression;
        this.conditionalExpressions = new ArrayList<>();
    }

    private void parseQuery() {
        try {
            addExpressionsToList();
        } catch (QueryLexerException e) {
            System.out.println("Invalid query structure!");
            System.exit(1);
        }
    }

    public boolean isDirectQuery() {
        if (conditionalExpressions.size() == 1) {
            ConditionalExpression expression = conditionalExpressions.get(0);

            return expression.getFieldGetter() == FieldValueGetters.JMBAG // TODO ==?
                    && expression.getComparisonOperator() == ComparisonOperators.EQUALS;
        }
        return false;
    }

    public String getQueriedJMBAG() {
        if (!isDirectQuery()) {
            throw new IllegalStateException("Query is not a direct query!");
        }

        return conditionalExpressions.get(0).getStringLiteral();
    }

    public List<ConditionalExpression> getQueryExpression() {
        return conditionalExpressions;
    }

    private void addExpressionsToList() {
        QueryLexer lexer = new QueryLexer(queryExpression);

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
            if (operatorToken.getType() != TokenType.STRING) {
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
