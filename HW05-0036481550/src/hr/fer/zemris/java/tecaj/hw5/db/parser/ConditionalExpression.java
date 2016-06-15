package hr.fer.zemris.java.tecaj.hw5.db.parser;

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
 * {@code ConditionalExpression} is a class representation of conditions that
 * may appear in query.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class ConditionalExpression {

	/** Field value getter */
	private IFieldValueGetter fieldGetter;

	/** String which we use to compare with field values */
	private String stringLiteral;

	/** Operator which we use to compare string with field values */
	private IComparisonOperator operator;

	/**
	 * Constructs a new {@code ConditionalExpression} object with given
	 * {@link IFieldValueGetter} object, {@link String} object and
	 * {@link IComparisonOperator} object.
	 * 
	 * @param fieldGetter
	 *            field value getter
	 * @param stringLiteral
	 *            string which we use to compare with field values
	 * @param operator
	 *            operator which we use to compare string with field values
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator operator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.operator = operator;
	}

	/**
	 * Returns field value getter
	 * 
	 * @return field value getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns string literal
	 * 
	 * @return string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Return operator
	 * 
	 * @return operator
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getFieldString() + " ");
		sb.append(getOperatorString() + " ");
		sb.append(stringLiteral);

		return sb.toString();
	}

	/**
	 * Returns string representation of field name
	 * 
	 * @return string representation of field name
	 */
	private String getFieldString() {
		if (fieldGetter instanceof JmbagFieldGetter) {
			return "jmbag";
		} else if (fieldGetter instanceof LastNameFieldGetter) {
			return "lastName";
		} else if (fieldGetter instanceof FirstNameFieldGetter) {
			return "firstName";
		} else {
			throw new ParserException("");
		}
	}

	/**
	 * Returns string representation of operator
	 * 
	 * @return string representation of operator
	 */
	private String getOperatorString() {
		if (operator instanceof EqualsOperator) {
			return "=";
		} else if (operator instanceof NotEqualsOperator) {
			return "!=";
		} else if (operator instanceof MoreOperator) {
			return ">";
		} else if (operator instanceof LessOperator) {
			return "<";
		} else if (operator instanceof MoreOrEqualsOperator) {
			return ">=";
		} else if (operator instanceof LessOrEqualsOperator) {
			return "<=";
		} else if (operator instanceof LikeOperator) {
			return "LIKE";
		} else {
			throw new IllegalArgumentException();
		}
	}
}
