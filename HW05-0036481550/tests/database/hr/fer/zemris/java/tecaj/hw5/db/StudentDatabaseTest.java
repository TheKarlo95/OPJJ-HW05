package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.parser.ParserException;

/**
 * Test class for {@link StudentDatabase} class.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
@SuppressWarnings("javadoc")
public class StudentDatabaseTest {

	private StudentDatabase db;

	@Before
	public void setUp() throws Exception {
		List<String> records = Files
				.readAllLines(Paths.get(".\\db\\db1.txt")
						, StandardCharsets.UTF_8);

		db = new StudentDatabase(records);
	}

	@Test
	public void testForJMBAG() {
		assertEquals("0000000003\tBosnić\tAndrea\t4", db.forJMBAG("0000000003").toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testForJMBAGNull() {
		db.forJMBAG(null);
	}
	
	@Test
	public void testFilterValidInput1() {
		String query = "query lastName    LIKE \"B*\"";
		List<StudentRecord> expected = new ArrayList<>();

		expected.add(new StudentRecord("0000000002", "Bakamović ", "Petra", 3));
		expected.add(new StudentRecord("0000000003", "Bosnić ", "Andrea", 4));
		expected.add(new StudentRecord("0000000004", "Božić", "Marin", 5));
		expected.add(new StudentRecord("0000000005", "Brezović", "Jusufadis", 2));
		
		List<StudentRecord> actual = db.filter(new QueryFilter(query));
		
		for(int i = 0; i < actual.size(); i++) {
			assertTrue(expected.get(i).equals(actual.get(i)));
		}
	}
	
	@Test
	public void testFilterValidInput2() {
		String query = "query lastName LIKE     \"Be*\"";
		
		assertEquals(0, db.filter(new QueryFilter(query)).size());
	}
	
	@Test(expected = ParserException.class)
	public void testFilterInvalidInput1() {
		db.filter(new QueryFilter("indexquery jmbag>\"Be*\""));
	}
	
	@Test(expected = ParserException.class)
	public void testFilterInvalidInput2() {
		db.filter(new QueryFilter("indexquery jmbag=\"Be*\" and jmbag<\"Be*\""));
	}
	
	@Test(expected = ParserException.class)
	public void testFilterInvalidInput3() {
		db.filter(new QueryFilter("indexquery jmbag=\"Be*\" and jmbag=\"Be*\""));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFilterInvalidInput4() {
		db.filter(new QueryFilter("query jmbag=\"Be*\" and jmbag=Be*\""));
	}
}
