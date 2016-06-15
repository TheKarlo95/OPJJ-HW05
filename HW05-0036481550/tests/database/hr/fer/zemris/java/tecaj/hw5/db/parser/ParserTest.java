package hr.fer.zemris.java.tecaj.hw5.db.parser;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * Test class for {@link Parser} class.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
@SuppressWarnings("javadoc")
public class ParserTest {

	@Test
	public void testValidInput1() {
		Parser p = new Parser("indexquery jmbag=\"0000000003\"");

		List<ConditionalExpression> list = p.parse();
		
		assertEquals("jmbag = \"0000000003\"", list.get(0).toString());
	}
	
	@Test
	public void testValidInput2() {
		Parser p = new Parser("query lastName = \"Blažić\"");

		List<ConditionalExpression> list = p.parse();
		
		assertEquals("lastName = \"Blažić\"", list.get(0).toString());
	}
	
	@Test
	public void testValidInput3() {
		Parser p = new Parser("query firstName>\"A\" and lastName LIKE \"B*ć\"");

		List<ConditionalExpression> list = p.parse();
		
		assertEquals("firstName > \"A\"", list.get(0).toString());
		assertEquals("lastName LIKE \"B*ć\"", list.get(1).toString());
	}

}
