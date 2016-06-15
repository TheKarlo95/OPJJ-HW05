package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Represents a filter (boolean-valued function) of one argument.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public interface IFilter {

    /**
     * Evaluates this filter on the given argument.
     * 
     * @param record
     *            student record
     * @return {@code true} if evaluation is true; {@code false} otherwise
     */
    public boolean accepts(StudentRecord record);
}
