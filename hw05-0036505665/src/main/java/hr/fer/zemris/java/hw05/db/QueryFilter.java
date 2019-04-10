package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * This class is an implementation of the {@link IFilter} interface which filters a
 * given student record using the list of conditional expressions that it accepts
 * through the constructor.
 *
 * @author Bruna Dujmović
 * 
 */
public class QueryFilter implements IFilter {

    /**
     * A list of {@link ConditionalExpression} objects to use for filtering.
     */
    private List<ConditionalExpression> expressions;

    /**
     * Constructs a {@link QueryFilter} based on a given list of
     * {@link ConditionalExpression} objects.
     *
     * @param expressions the list of conditional expressions to use for filtering
     * @throws NullPointerException if the given list is {@code null}
     */
    public QueryFilter(List<ConditionalExpression> expressions) {
        Objects.requireNonNull(expressions);

        this.expressions = expressions;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression : expressions) {
            IComparisonOperator operator = expression.getComparisonOperator();
            IFieldValueGetter getter = expression.getFieldGetter();
            String literal = expression.getStringLiteral();

            if (!(operator.satisfied(getter.get(record), literal))) {
                return false;
            }
        }

        return true;
    }
}
