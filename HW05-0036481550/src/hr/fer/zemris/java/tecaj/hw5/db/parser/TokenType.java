package hr.fer.zemris.java.tecaj.hw5.db.parser;

import java.util.regex.Pattern;

/**
 * Possible states of a <code>Token</code>:
 * <p>- ATTRIBUTE</p>
 * <p>- OPERATOR</p>
 * <p>- STRING</p>
 * <p>- EOF</p>
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public enum TokenType {

	/** Keyoword token type */
	KEYWORD("^(query|indexquery)$")
	
	/** Attribute token type */
	, ATTRIBUTE("^(jmbag|lastname|firstname|finalgrade)$")
	
	/** Operator token type */
	, OPERATOR("^(>|<|>=|<=|=|!=|like|and)$")
	
	/** String token type */
	, STRING("^\\\".*\\\"$")
	
	/** End of file token type */
	, EOF("^EOF$");
	
	/** Pattern of a token type */
	private Pattern pattern;

	/**
	 * Constructs a new {@code TokenType} enum from string
	 * 
	 * @param regEx regular expression
	 */
	private TokenType(String regEx) {
		this.pattern = Pattern.compile(regEx);
	}
	
	/**
	 * Attempts to match the entire region against the pattern. 	
	 * 
	 * @param str string which we compare
	 * @return {@code true} if the entire region sequence matches this matcher's pattern
	 */
	public boolean matches(String str) {
		return this.pattern.matcher(str.toLowerCase()).matches();
	}
}
