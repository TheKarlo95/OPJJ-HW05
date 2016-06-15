package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Test class for {@link StudentRecord} class.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
@SuppressWarnings("javadoc")
public class StudentRecordTest {

	// legal arguments for constructor tests

	@Test
	public void testConstructor() {
		makeRecords();
	}

	// illegal arguments for constructor tests

	@Test(
		expected = IllegalArgumentException.class)
	public void testConstructorJMBAG9Digits() {
		new StudentRecord(
				"000000022",
				"Jurina",
				"Filip",
				3);
	}

	@Test(
		expected = IllegalArgumentException.class)
	public void testConstructorJMBAG11Digits() {
		new StudentRecord(
				"00364815500",
				"Karlo",
				"Vrbić",
				5);
	}

	@Test(
		expected = IllegalArgumentException.class)
	public void testConstructorJMBAGDigitsAndLetters() {
		new StudentRecord(
				"00000002d52",
				"Jurina",
				"Filip",
				3);
	}

	@Test(
		expected = IllegalArgumentException.class)
	public void testConstructorIllegalFirstName() {
		new StudentRecord(
				"0003000022",
				"1Martin",
				"Luther King, Jr.",
				2);
	}

	@Test(
		expected = IllegalArgumentException.class)
	public void testConstructorIllegalLastName() {
		new StudentRecord(
				"0003000022",
				"Martin",
				"+Luther King, Jr.",
				2);
	}

	@Test(
		expected = IllegalArgumentException.class)
	public void testConstructorFinalGradeMoreThan5() {
		new StudentRecord(
				"0003000022",
				"Hector",
				"Sausage-Hausen",
				6);
	}

	@Test(
		expected = IllegalArgumentException.class)
	public void testConstructorFinalGradeLessThan1() {
		new StudentRecord(
				"0003000022",
				"Hector",
				"Sausage-Hausen",
				0);
	}

	// tests for methods

	@Test
	public void testToString() {
		StudentRecord[] rec = makeRecords();

		assertEquals("0000000022\tFilip\tJurina\t3", rec[0].toString());

		assertEquals("0003000022\tMathias\td'Arras\t5", rec[1].toString());

		assertEquals(
				"0003000022\tHector\tSausage-Hausen\t1",
				rec[2].toString());

		assertEquals(
				"0003000022\tMartin\tLuther King, Jr.\t2",
				rec[3].toString());

		assertEquals("0036481550\tKarlo\tVrbić\t5", rec[4].toString());

	}

	@Test
	public void testEquals() {
		StudentRecord[] rec = makeRecords();

		StudentRecord rec1 = new StudentRecord(
				"0000000022",
				"Filip",
				"Jurina",
				3);
		StudentRecord rec2 = new StudentRecord(
				"0003000022",
				"Mathias",
				"d'Arras",
				5);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (i == j) {
					assertTrue(rec[i].equals(rec[j]));
				} else {
					assertFalse(rec[i].equals(rec[j]));
				}
			}
		}

		assertTrue(rec[0].equals(rec1));
		assertTrue(rec[1].equals(rec2));
	}

	@Test
	public void testHashcode() {
		StudentRecord[] rec = makeRecords();

		for (int i = 0; i < 5; i++) {
			Integer hash1 = rec[i].hashCode();
			Integer hash2 = rec[i].clone().hashCode();

			assertTrue(hash1.equals(hash2));
		}
	}

	private StudentRecord[] makeRecords() {
		StudentRecord[] rec = new StudentRecord[5];

		rec[0] = new StudentRecord("0000000022", "Filip", "Jurina", 3);
		rec[1] = new StudentRecord("0003000022", "Mathias", "d'Arras", 5);
		rec[2] = new StudentRecord("0003000022", "Hector", "Sausage-Hausen", 1);
		rec[3] = new StudentRecord(
				"0003000022",
				"Martin",
				"Luther King, Jr.",
				2);
		rec[4] = new StudentRecord("0036481550", "Karlo", "Vrbić", 5);

		return rec;
	}

}
