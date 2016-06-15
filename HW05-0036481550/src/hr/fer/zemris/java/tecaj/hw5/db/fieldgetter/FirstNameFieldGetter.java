package hr.fer.zemris.java.tecaj.hw5.db.fieldgetter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Implementation of {@link IFieldValueGetter} which returns first name of the
 * student.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see IFieldValueGetter
 */
public class FirstNameFieldGetter implements IFieldValueGetter {

    /**
     * Returns first name from specified student record.
     * 
     * @param record
     *            student record from database
     * @return first name from specified student record
     */
    @Override
    public String get(StudentRecord record) {
        return record.getFirstName();
    }

}
