package hr.fer.zemris.java.tecaj.hw5.db.fieldgetter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Implementation of {@link IFieldValueGetter} which returns JMBAG of the
 * student.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see IFieldValueGetter
 */
public class JmbagFieldGetter implements IFieldValueGetter {

    /**
     * Returns JMBAG from specified student record.
     * 
     * @param record
     *            student record from database
     * @return JMBAG from specified student record
     */
    @Override
    public String get(StudentRecord record) {
        return record.getJMBAG();
    }

}
