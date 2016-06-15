package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * {@code EqualsOperator} is a implementation of {@link IComparisonOperator} for
 * more operator.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see IComparisonOperator
 */
public class MoreOperator implements IComparisonOperator {

    @Override
    public boolean satisfied(String value1, String value2) {
        return value1.compareTo(value2) > 0;
    }

}
