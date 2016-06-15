package hr.fer.zemris.java.tecaj.hw5.db.parser;

/**
 * {@code Token} is class which is holding value and it's type. It is used in
 * {@link Tokenizer} to split string into {@code Token} objects. objects.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class Token {

	/** Type of a token. */
	private TokenType type;

	/** Value that token holds. */
	private String value;

	/**
	 * Constructs a {@code Token} object from specified type and value.
	 * 
	 * @param type
	 *            type of a token
	 * @param value
	 *            value that token holds
	 */
	public Token(TokenType type, String value) {
		this.type = type;

		if (type.equals(TokenType.EOF)) {
			this.value = null;
		} else if (!type.equals(TokenType.STRING)) {
			this.value = value.toLowerCase();
		} else {
			this.value = value;
		}
	}

	/**
	 * Constructs a {@code Token} object from specified value. Type is evaluated
	 * automatically.
	 * 
	 * @param value
	 *            value that token holds
	 */
	public Token(String value) {
		this.value = value;

		for (TokenType type : TokenType.values()) {
			if (type.matches(value)) {
				this.type = type;
			}
		}

		if (this.type == null) {
			throw new IllegalArgumentException("There is no token type for value provided!");
		}

		if (!type.equals(TokenType.STRING)) {
			this.value = value.toLowerCase();
		}
	}

	/**
	 * Returns type of this token.
	 * 
	 * @return type of this token
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Returns value of this token.
	 * 
	 * @return value of this token
	 */
	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Token)) {
			return false;
		}

		return this.type.equals(((Token) obj).getType()) && this.value.equals(((Token) obj).getValue());
	}

	/**
	 * Indicates whether this {@code Token} is "equal to" token constructed from
	 * specified type and value.
	 * 
	 * @param type
	 *            type of a token
	 * @param value
	 *            value that token holds
	 * @return {@code true} if tokens are equal; {@code false} otherwise
	 */
	public boolean equals(TokenType type, String value) {
		return this.equals(new Token(type, value));
	}

	@Override
	public String toString() {
		return "(" + this.type + ", " + this.value + ")";
	}
}
