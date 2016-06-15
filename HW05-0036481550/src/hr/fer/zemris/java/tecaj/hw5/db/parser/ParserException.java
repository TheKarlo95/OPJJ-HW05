package hr.fer.zemris.java.tecaj.hw5.db.parser;

/**
 * {@code ParserException} is thrown to indicate that a parser have encountered
 * an error.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see RuntimeException
 */
public class ParserException extends RuntimeException {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -222654971327150426L;

	/**
	 * Constructs an {@code ParserException} with the specified detail message.
	 * 	
	 * @param string the detail message
	 */
	public ParserException(String string) {
		super(string);
	}

}
