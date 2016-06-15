package hr.fer.zemris.java.tecaj.hw5.db.parser;

/**
 * The {@code Tokenizer} class allows an application to break a string into
 * {@link Token}-s.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class Tokenizer {

	/** Char array of text that tokenizer needs to split into tokens. */
	private char[] data;

	/** Current token index. */
	private int index;

	/** Current token. */
	private Token token;

	/**
	 * Constructs a new {@code Tokenizer} object from specified text.
	 * 
	 * @param text
	 *            text to be tokenized
	 */
	public Tokenizer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("You tried to run lexical analyzer on null reference");
		}

		this.data = text.trim().toCharArray();
	}

	/**
	 * Returns current token.
	 * 
	 * @return current token
	 */
	public Token getToken() {
		return this.token;
	}

	/**
	 * Finds and returns next token
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		StringBuilder sb = new StringBuilder();

		skipWhitespaces();

		if (index >= data.length) {
			this.token = new Token(TokenType.EOF, null);
		} else {

			do {
				sb.append(data[index++]);

				if (data.length <= index) {
					break;
				}

				if (isValidToken(sb.toString()) && !isValidToken(sb.toString() + data[index])) {
					break;
				}
			} while (data.length > index);

			skipWhitespaces();

			this.token = new Token(sb.toString());
		}

		return token;
	}

	/**
	 * Checks if specified string is valid string value of a token.
	 * 
	 * @param str
	 *            possible string value of a token
	 * @return {@code true} if specified string is valid string value of a
	 *         token;{@code false} otherwise
	 */
	private boolean isValidToken(String str) {
		for (TokenType type : TokenType.values()) {
			if (type.matches(str)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Skips whitespaces in char array.
	 */
	private void skipWhitespaces() {
		while (index < data.length && Character.isWhitespace(data[index])) {
			index++;
		}
	}

}
