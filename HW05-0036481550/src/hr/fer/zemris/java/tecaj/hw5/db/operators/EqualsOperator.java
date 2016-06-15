package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * {@code EqualsOperator} is a implementation of {@link IComparisonOperator} for
 * equals operator.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see IComparisonOperator
 */
public class EqualsOperator implements IComparisonOperator {

    @Override
    public boolean satisfied(String value1, String value2) {
        return value1.equals(value2);
    }

}
