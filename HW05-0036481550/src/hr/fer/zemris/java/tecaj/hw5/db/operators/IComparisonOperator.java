package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Interface for comparison operators.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public interface IComparisonOperator {

    /**
     * Compares specified string values.
     * 
     * @param value1
     *            First string value
     * @param value2
     *            Second string value
     * @return {@code true} if comparison is true; {@code false} otherwise
     */
    public boolean satisfied(String value1, String value2);
}
