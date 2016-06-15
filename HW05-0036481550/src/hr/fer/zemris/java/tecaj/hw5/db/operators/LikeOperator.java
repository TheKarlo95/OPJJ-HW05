package hr.fer.zemris.java.tecaj.hw5.db.operators;

import java.util.regex.Pattern;

/**
 * {@code EqualsOperator} is a implementation of {@link IComparisonOperator} for
 * like operator.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see IComparisonOperator
 */
public class LikeOperator implements IComparisonOperator {

    @Override
    public boolean satisfied(String value1, String value2) {
        int numOfWildcards = value2.length() - value2.replace("*", "").length();

        if (numOfWildcards == 1) {
            return Pattern.matches(value2.replace("*", ".*"), value1);
        } else if (numOfWildcards == 0) {
            return value1.equals(value2);
        } else {
            throw new IllegalArgumentException(
                    "More than 1 '*' sign found in value1!");
        }
    }

}
