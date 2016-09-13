package com.pqqqqq.escript.lang.data.container.expression;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.container.ConditionContainer;
import com.pqqqqq.escript.lang.data.container.DatumContainer;
import com.pqqqqq.escript.lang.line.Context;

/**
 * Created by Kevin on 2015-06-17.
 * <p>A {@link ExpressionContainer} that compares many separate {@link DatumContainer}s</p>
 * <p>This is slightly different than {@link ConditionContainer} in that it does not accept or/and values</p>
 * <p>For external use, usually using the condition container is preferred.</p>
 */
public class ConditionalExpressionContainer extends ExpressionContainer {

    /**
     * Creates a new {@link ConditionalExpressionContainer} with the given {@link DatumContainer} terms and the operator
     *
     * @param firstTerm  the first term in the expression (left)
     * @param secondTerm the second term in the expression (right)
     * @param operator   the operator for the expression
     */
    public ConditionalExpressionContainer(DatumContainer firstTerm, DatumContainer secondTerm, ComparativeOperator operator) {
        super(firstTerm, secondTerm, operator);
    }

    /**
     * Creates a new {@link ConditionalExpressionContainer} with the given {@link DatumContainer} terms and the functional operator string
     *
     * @param firstTerm  the first term in the expression (left)
     * @param secondTerm the second term in the expression (right)
     * @param operator   the string operator for the expression
     */
    public ConditionalExpressionContainer(DatumContainer firstTerm, DatumContainer secondTerm, String operator) {
        this(firstTerm, secondTerm, ComparativeOperator.fromOperator(operator));
    }

    @Override
    public ComparativeOperator getOperator() {
        return (ComparativeOperator) super.getOperator();
    }

    @Override
    public Literal resolve(Context ctx) {
        Literal firstTerm;
        Literal secondTerm = getSecondTerm().resolve(ctx);
        ComparativeOperator comparator = getOperator();

        // Check to see if the first container is also a condition. StringParser#parseNextSequence parses the last sequence, so
        // 2 < variable < 10 will be parsed as (2 < variable) < 10
        if (getFirstTerm() instanceof ConditionalExpressionContainer) {
            // If so, parse that as well, and use the second expression in that container as the first in this one
            ConditionalExpressionContainer firstTermContainer = (ConditionalExpressionContainer) getFirstTerm();
            if (!firstTermContainer.resolve(ctx).asBoolean()) { // If this is false, the entire thing is false
                return Literal.FALSE;
            } else { // Otherwise, set the firstTerm to the second one
                firstTerm = firstTermContainer.getSecondTerm().resolve(ctx);
            }
        } else { // If not, just resolve the first term
            firstTerm = getFirstTerm().resolve(ctx);
        }

        if (firstTerm.isEmpty() && secondTerm.isEmpty()) {
            return comparator.isNegative() ? Literal.FALSE : Literal.TRUE;
        } else if (firstTerm.isEmpty() && !secondTerm.isEmpty() || secondTerm.isEmpty() && !firstTerm.isEmpty()) {
            return comparator.isNegative() ? Literal.TRUE : Literal.FALSE;
        }

        switch (comparator) {
            case EQUALS:
                return Literal.fromObject(firstTerm.getValue().equals(secondTerm.getValue()));
            case NOT_EQUALS:
                return Literal.fromObject(!firstTerm.getValue().equals(secondTerm.getValue()));
            case SIMILAR:
                return Literal.fromObject(firstTerm.asString().trim().equalsIgnoreCase(secondTerm.asString().trim()));
            case DISSIMILAR:
                return Literal.fromObject(!firstTerm.asString().trim().equalsIgnoreCase(secondTerm.asString().trim()));
            case LESS_THAN:
                return Literal.fromObject(firstTerm.asNumber() < secondTerm.asNumber());
            case MORE_THAN:
                return Literal.fromObject(firstTerm.asNumber() > secondTerm.asNumber());
            case LESS_THAN_EQUAL_TO:
                return Literal.fromObject(firstTerm.asNumber() <= secondTerm.asNumber());
            case MORE_THAN_EQUAL_TO:
                return Literal.fromObject(firstTerm.asNumber() >= secondTerm.asNumber());
            default:
                throw new IllegalStateException("Unknown comparative operator: " + comparator);
        }
    }

    /**
     * An enumeration of all possible comparative {@link ExpressionOperator}s
     */
    public enum ComparativeOperator implements ExpressionOperator {
        /**
         * Represents a blank operator
         */
        NONE(null),

        /**
         * Represents the equals operator (==)
         */
        EQUALS("is"),

        /**
         * Represents the not equals operator (!=)
         */
        NOT_EQUALS("is not", true),

        /**
         * Represents the similar operator (~)
         */
        SIMILAR("similar"),

        /**
         * Represents the dissimilar operator (!~)
         */
        DISSIMILAR("dissimilar", true),

        /**
         * Represents the less than operator (<)
         */
        LESS_THAN("less than"),

        /**
         * Represents the more than operator (>)
         */
        MORE_THAN("greater than"),

        /**
         * Represents the less than of equal to operator (<=)
         */
        LESS_THAN_EQUAL_TO("less than or equal to"),

        /**
         * Represents the more than equal to operator (>=)
         */
        MORE_THAN_EQUAL_TO("greater than or equal to");

        private final String operator;
        private final boolean negative;

        ComparativeOperator(String operator) {
            this(operator, false);
        }

        ComparativeOperator(String operator, boolean negative) {
            this.operator = operator;
            this.negative = negative;
        }

        /**
         * Gets the {@link ComparativeOperator} corresponding to the operator string
         *
         * @param operator the functional operator string
         * @return the comparative operator, or {@link #NONE} if none match
         */
        public static ComparativeOperator fromOperator(String operator) {
            for (ComparativeOperator comparativeOperator : values()) {
                if (comparativeOperator.test(operator)) {
                    return comparativeOperator;
                }
            }

            return NONE;
        }

        /**
         * Gets the functional operator string (eg == for EQUALS)
         *
         * @return the operator string
         */
        public String getOperator() {
            return operator;
        }

        /**
         * Gets if this operator is negative (unequal, dissimilar, etc)
         *
         * @return true if negative
         */
        public boolean isNegative() {
            return negative;
        }

        @Override
        public boolean test(String input) {
            return getOperator() != null && getOperator().equals(input.trim());
        }
    }
}
