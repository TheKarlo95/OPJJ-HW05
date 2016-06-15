package hr.fer.zemris.java.tecaj.hw5.db.parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for {@link Tokenizer} class.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
@SuppressWarnings("javadoc")
public class TokenizerTest {

	@Test
	public void testValidInput1() {
		Tokenizer t = new Tokenizer("indexquery jmbag=\"0000000003\"");
		
		assertEquals(new Token(TokenType.KEYWORD, "indexquery"), t.nextToken());
		
		assertEquals(new Token(TokenType.ATTRIBUTE, "jmbag"), t.nextToken());
		assertEquals(new Token(TokenType.OPERATOR, "="), t.nextToken());
		assertEquals(new Token(TokenType.STRING, "\"0000000003\""), t.nextToken());
	}
	
	@Test
	public void testValidInput2() {
		Tokenizer t = new Tokenizer("query lastName = \"Blažić\"");
		
		assertEquals(new Token(TokenType.KEYWORD, "query"), t.nextToken());
		
		assertEquals(new Token(TokenType.ATTRIBUTE, "lastname"), t.nextToken());
		assertEquals(new Token(TokenType.OPERATOR, "="), t.nextToken());
		assertEquals(new Token(TokenType.STRING, "\"Blažić\""), t.nextToken());
	}
	
	@Test
	public void testValidInput3() {
		Tokenizer t = new Tokenizer("query firstName>\"A\" and lastName LIKE \"B*ć\"");
		
		assertEquals(new Token(TokenType.KEYWORD, "query"), t.nextToken());
		
		assertEquals(new Token(TokenType.ATTRIBUTE, "firstname"), t.nextToken());
		assertEquals(new Token(TokenType.OPERATOR, ">"), t.nextToken());
		assertEquals(new Token(TokenType.STRING, "\"A\""), t.nextToken());
		
		assertEquals(new Token(TokenType.OPERATOR, "and"), t.nextToken());
		
		assertEquals(new Token(TokenType.ATTRIBUTE, "lastname"), t.nextToken());
		assertEquals(new Token(TokenType.OPERATOR, "like"), t.nextToken());
		assertEquals(new Token(TokenType.STRING, "\"B*ć\""), t.nextToken());
	}
	
	@Test(expected = NullPointerException.class)
	public void testEmptyInput() {
		Tokenizer t = new Tokenizer("");
		
		assertEquals(new Token(TokenType.EOF, null), t.nextToken());
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullInput() {
		Tokenizer t = new Tokenizer("");
		
		assertEquals(new Token(TokenType.EOF, null), t.nextToken());
	}

}
