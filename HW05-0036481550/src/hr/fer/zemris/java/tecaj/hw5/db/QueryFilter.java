package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.parser.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.parser.Parser;

/**
 * {@code QueryFilter} class represents a implementation of a {@link IFilter}
 * interface.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see IFilter
 */
public class QueryFilter implements IFilter {

    /** List of a conditional expressions */
    private List<ConditionalExpression> expressionList;

    /**
     * Constructs a new {@code QueryFilter} object from a specified query
     * string.
     * 
     * @param query
     *            string representation of a query
     */
    public QueryFilter(String query) {
        Parser parser = new Parser(query);

        expressionList = parser.parse();
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expr : expressionList) {
            if (!expr.getOperator().satisfied(
                    expr.getFieldGetter().get(record),
                    expr.getStringLiteral().replace("\"", ""))) {
                return false;
            }
        }

        return true;
    }

}
