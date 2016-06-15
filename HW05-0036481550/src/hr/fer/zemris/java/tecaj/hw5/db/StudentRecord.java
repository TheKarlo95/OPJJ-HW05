package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * {@code StudentRecord} represents one row in out simulated database. It
 * contains jmbag, last name, first name and final grade.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class StudentRecord {

    /** JMBAG of student */
    private String jmbag;

    /** Last name of student */
    private String lastName;

    /** First name of student */
    private String firstName;

    /** Final grade of student */
    private Integer finalGrade;

    /**
     * Constructs a new {@code StudentRecord} object from specified JMBAG, last
     * name, first name and final grade.
     * 
     * @param jmbag
     *            JMBAG of student
     * @param lastName
     *            last name of student
     * @param firstName
     *            first name of student
     * @param finalGrade
     *            final grade of student
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {

        if (jmbag == null || lastName == null || firstName == null) {
            throw new IllegalArgumentException("One of the arguments is null!");
        }

        if (!jmbag.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("JMBAG must consist of 10 digits!");
        }
        if (!lastName.matches("^\\p{L}[\\p{L} ,\\.'-]*$")) {
            throw new IllegalArgumentException("Invalid last name!");
        }
        if (!firstName.matches("^\\p{L}[\\p{L} ,\\.'-]*$")) {
            throw new IllegalArgumentException("Invalid first name!");
        }
        if (finalGrade > 5 || finalGrade < 1) {
            throw new IllegalArgumentException(
                    "Final grade must be integer value between 1 and 5!");
        }

        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    /**
     * Returns the JMBAG of a student
     * 
     * @return the JMBAG of a student
     */
    public String getJMBAG() {
        return jmbag;
    }

    /**
     * Returns the last name of a student
     * 
     * @return the last name of a student
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the first name of a student
     * 
     * @return the first name of a student
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the final grade of a student
     * 
     * @return the final grade of a student
     */
    public Integer getFinalGrade() {
        return finalGrade;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StudentRecord)) {
            return false;
        }

        return this.jmbag.equals(((StudentRecord) obj).jmbag);
    }

    @Override
    public int hashCode() {
        return jmbag.hashCode();
    }

    @Override
    public String toString() {
        return jmbag + "\t" + lastName + "\t" + firstName + "\t" + finalGrade;
    }

    @Override
    public Object clone() {
        return new StudentRecord(jmbag, lastName, firstName, finalGrade);
    }

    /**
     * Factory method for getting StudentRecord from {@code String}
     * representation of a record and a separator.(for example comma-separated
     * file, tab-separated file)
     * 
     * @param str
     *            {@code String} representation of a record
     * @param separator
     *            separator between student attributes
     * @return new {@code StudentRecord} object containing all information about
     *         the student
     */
    public static StudentRecord fromString(String str, String separator) {
        if (str == null || separator == null) {
            throw new IllegalArgumentException("Both arguments must be non-null string references");
        }

        String[] args = str.split(separator);

        if (args.length != 4) {
            throw new IllegalArgumentException(
                    "You need to input string with 4 arguments separated by"
                            + " separator you define!");
        }

        StudentRecord rec;
        try {
            rec = new StudentRecord(args[0], args[1], args[2], Integer.parseInt(args[3]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("4th argument must be integer between 1 and 5!");
        }

        return rec;
    }

}
