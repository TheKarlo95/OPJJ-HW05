package hr.fer.zemris.java.tecaj.hw5.db.fieldgetter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * interface for returning JMBAG, last name or first name of a student depending
 * of implementation.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public interface IFieldValueGetter {

    /**
     * Returns JMBAG, last name or first name of a student depending of
     * implementation.
     * 
     * @param record
     *            student record from database
     * @return JMBAG, last name or first name of a student depending of
     *         implementation
     */
    public String get(StudentRecord record);

}
