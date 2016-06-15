package hr.fer.zemris.java.tecaj.hw5.db.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.fieldgetter.FirstNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetter.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetter.JmbagFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldgetter.LastNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operators.EqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessOrEqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LikeOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.MoreOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.MoreOrEqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.NotEqualsOperator;

/**
 * {@code Parser} takes {@link Token} objects from {@link Tokenizer} and makes
 * {@link ConditionalExpression} out of it.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class Parser {

	/** Tokenizer which will provide tokens for this {@code Parser} */
	private Tokenizer tokenizer;

	/**
	 * Constructs a new {@code Parser} object from given text. Text is fed to
	 * tokenizer so tokenizer can make new tokens.
	 * 
	 * @param text
	 *            text to be tokenized and converted to conditional expressions
	 */
	public Parser(String text) {
		if (text == null) {
			throw new IllegalArgumentException("You can't provide null reference to parser.");
		}
		this.tokenizer = new Tokenizer(text);
	}

	/**
	 * Parses previously specified string
	 * 
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression> parse() {
		List<ConditionalExpression> list = new ArrayList<>(4);

		// must be query or indexquery
		Token typeOfQueryToken = tokenizer.nextToken();

		do {
			ConditionalExpression expr;

			Token fieldToken = tokenizer.nextToken();
			Token operatorToken = tokenizer.nextToken();
			Token stringToken = tokenizer.nextToken();

			if (isIndexQuery(typeOfQueryToken, fieldToken, operatorToken, stringToken)) {
				expr = new ConditionalExpression(
						new JmbagFieldGetter(),
						tokenizer.getToken().getValue(),
						new EqualsOperator());
			} else if (isQuery(typeOfQueryToken, fieldToken, operatorToken, stringToken)) {
				expr = new ConditionalExpression(
						getFieldValueGetter(fieldToken.getValue()),
						stringToken.getValue(),
						getOperator(operatorToken.getValue()));
			} else {
				throw new ParserException("Invalid query");
			}

			list.add(expr);

			if (tokenizer.nextToken().equals(TokenType.OPERATOR, "and")) {
				if (typeOfQueryToken.getValue().equals("indexquery")) {
					throw new ParserException(
							"You can only have one condition on indexed query.");
				}
				continue;
			}
		} while (!tokenizer.getToken().getType().equals(TokenType.EOF));

		return list;
	}

	/**
	 * Checks if tokens corresponds to indexquery argument layout
	 * 
	 * @param token1
	 *            First token; should contain value "indexquery"
	 * @param token2
	 *            Second token; should contain value "jmbag"
	 * @param token3
	 *            Third token; should contain value "="
	 * @param token4
	 *            Fourth token; should contain value corresponding to string
	 *            literal
	 * @return {@code true} if tokens corresponds to indexquery argument layout;
	 *         {@code false} otherwise
	 */
	private boolean isIndexQuery(Token token1, Token token2, Token token3, Token token4) {
		if (token1 == null || token2 == null || token3 == null || token4 == null) {
			return false;
		}

		return token1.equals(TokenType.KEYWORD, "indexquery")
				&& token2.equals(TokenType.ATTRIBUTE, "jmbag")
				&& token3.equals(TokenType.OPERATOR, "=")
				&& token4.getType().equals(TokenType.STRING);
	}

	/**
	 * Checks if tokens corresponds to query argument layout
	 * 
	 * @param token1
	 *            First token; should contain value "query"
	 * @param token2
	 *            Second token; should contain value of name of one of the
	 *            fields
	 * @param token3
	 *            Third token; should contain operator value
	 * @param token4
	 *            Fourth token; should contain value corresponding to string
	 *            literal
	 * @return {@code true} if tokens corresponds to query argument layout;
	 *         {@code false} otherwise
	 */
	private boolean isQuery(Token token1, Token token2, Token token3, Token token4) {
		if (token1 == null || token2 == null || token3 == null || token4 == null) {
			return false;
		}

		return token1.equals(TokenType.KEYWORD, "query")
				&& token2.getType().equals(TokenType.ATTRIBUTE)
				&& token3.getType().equals(TokenType.OPERATOR)
				&& token4.getType().equals(TokenType.STRING);
	}

	/**
	 * Returns {@link IFieldValueGetter} representation of a field.
	 * 
	 * @param str
	 *            name of a field
	 * @return {@link IFieldValueGetter} representation of a field
	 */
	private IFieldValueGetter getFieldValueGetter(String str) {
		switch (str.toLowerCase()) {
		case "lastname":
			return new LastNameFieldGetter();
		case "firstname":
			return new FirstNameFieldGetter();
		case "jmbag":
			return new JmbagFieldGetter();
		default:
			throw new IllegalArgumentException("Unsupported field getter!");
		}
	}

	/**
	 * Returns {@link IComparisonOperator} representation of a operator.
	 * 
	 * @param str
	 *            operator
	 * @return {@link IFieldValueGetter} representation of an operator
	 */
	private IComparisonOperator getOperator(String str) {
		switch (str.toLowerCase()) {
		case "=":
			return new EqualsOperator();
		case "!=":
			return new NotEqualsOperator();
		case ">":
			return new MoreOperator();
		case "<":
			return new LessOperator();
		case ">=":
			return new MoreOrEqualsOperator();
		case "<=":
			return new LessOrEqualsOperator();
		case "like":
			return new LikeOperator();
		default:
			throw new IllegalArgumentException("Unsupported field getter!");
		}
	}

}
