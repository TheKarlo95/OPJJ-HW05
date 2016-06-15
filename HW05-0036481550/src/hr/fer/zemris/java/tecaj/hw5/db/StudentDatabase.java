package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * {@code StudentDatabase} class represents simulation of a SQL database.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class StudentDatabase {

    /** Default separator. */
    private static final String DEFAULT_SEPARATOR = "\t";

    /** List of all student records. */
    List<StudentRecord> records;

    /** Hashtable of all student records added by jmbag as key. */
    SimpleHashtable<String, StudentRecord> indexJMBAG;

    /**
     * Constructs a new {@code StudentDatabase} object from list of records
     * 
     * @param records
     *            list of string representation of student records
     */
    public StudentDatabase(List<String> records) {
        if (records == null) {
            throw new IllegalArgumentException("Argument records can't be null");
        }

        this.records = new ArrayList<>(records.size());
        this.indexJMBAG = new SimpleHashtable<>(records.size());

        records.forEach(x -> this.records.add(StudentRecord.fromString(x, DEFAULT_SEPARATOR)));

        this.records.forEach(x -> indexJMBAG.put(x.getJMBAG(), x));
    }

    /**
     * Returns student record with specified jmbag.
     * 
     * @param jmbag
     *            jmbag of record
     * @return record with specified jmbag
     */
    public StudentRecord forJMBAG(String jmbag) {
        if (jmbag == null) {
            throw new IllegalArgumentException("JMBAG can't be a null reference!");
        }

        return indexJMBAG.get(jmbag);
    }

    /**
     * Returns student record with specified filter.
     * 
     * @param filter
     *            filter for database
     * @return list of all records which passed the filter
     */
    public List<StudentRecord> filter(IFilter filter) {
        return records.stream().filter(x -> filter.accepts(x)).collect(Collectors.toList());
    }

}
