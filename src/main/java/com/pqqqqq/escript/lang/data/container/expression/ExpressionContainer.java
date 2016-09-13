package com.pqqqqq.escript.lang.data.container.expression;

import com.pqqqqq.escript.lang.data.container.DatumContainer;

import java.util.function.Predicate;

/**
 * Created by Kevin on 2015-06-17.
 * <p>
 * An abstract {@link DatumContainer} which does something to two separate data containers based on the operator they are separated by
 */
public abstract class ExpressionContainer implements DatumContainer {
    private final DatumContainer firstTerm;
    private final DatumContainer secondTerm;
    private final ExpressionOperator operator;

    /**
     * Creates a new {@link ExpressionContainer} with the given {@link DatumContainer} terms and the operator
     *
     * @param firstTerm  the first term in the expression (left)
     * @param secondTerm the second term in the expression (right)
     * @param operator   the operator for the expression
     */
    public ExpressionContainer(DatumContainer firstTerm, DatumContainer secondTerm, ExpressionOperator operator) {
        this.firstTerm = firstTerm;
        this.secondTerm = secondTerm;
        this.operator = operator;
    }

    /**
     * Gets the first {@link DatumContainer} terms in this expression
     *
     * @return the first data containers
     */
    public DatumContainer getFirstTerm() {
        return firstTerm;
    }

    /**
     * Gets the second {@link DatumContainer} terms in this expression
     *
     * @return the second data containers
     */
    public DatumContainer getSecondTerm() {
        return secondTerm;
    }

    /**
     * Gets the {@link ExpressionOperator} for this expression
     *
     * @return the operator
     */
    public ExpressionOperator getOperator() {
        return operator;
    }

    /**
     * Represents an operator in an expression that inherits from a String {@link Predicate}
     */
    public interface ExpressionOperator extends Predicate<String> {
    }
}
