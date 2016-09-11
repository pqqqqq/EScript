package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.container.expression.ConditionalExpressionContainer;
import com.pqqqqq.escript.lang.line.Context;

import java.util.List;

/**
 * Created by Kevin on 2015-06-17.
 * A {@link DatumContainer container} that contains a two dimensional {@link ConditionalExpressionContainer condition expression container} array of or and and expressions
 */
public class ConditionContainer implements DatumContainer {
    private final ConditionalExpressionContainer[][] conditionExpressions;

    /**
     * Creates a new {@link ConditionContainer} with the two-dimensional {@link ConditionalExpressionContainer} array
     *
     * @param conditionExpressions the 2D array
     */
    public ConditionContainer(ConditionalExpressionContainer[]... conditionExpressions) {
        this.conditionExpressions = conditionExpressions;
    }

    /**
     * Creates a new {@link ConditionContainer} with a double-decker condition expression {@link List}
     *
     * @param collection the double collection
     */
    public ConditionContainer(List<? extends List<? extends ConditionalExpressionContainer>> collection) {
        ConditionalExpressionContainer[][] conditionExpressions = new ConditionalExpressionContainer[collection.size()][];
        for (int i = 0; i < collection.size(); i++) {
            List<? extends ConditionalExpressionContainer> row = collection.get(i);
            conditionExpressions[i] = row.toArray(new ConditionalExpressionContainer[row.size()]);
        }

        this.conditionExpressions = conditionExpressions;
    }

    /**
     * <p>Gets the two dimensional {@link ConditionalExpressionContainer} array</p>
     * <p>The first dimension represents OR expressions</p>
     * <p>The second dimension represents AND expressions</p>
     *
     * @return the array
     */
    public ConditionalExpressionContainer[][] getConditionExpressions() {
        return conditionExpressions;
    }

    @Override
    public Literal resolve(Context ctx) {
        andArray:
        for (ConditionalExpressionContainer[] andArray : getConditionExpressions()) {
            if (andArray.length == 0) {
                continue; // Empty arrays don't make it true, they make it false
            }

            for (ConditionalExpressionContainer expression : andArray) {
                if (!expression.resolve(ctx).asBoolean()) {
                    continue andArray; // Expression was unsuccessful, move on to the next array
                }
            }

            return Literal.TRUE; // The only time it should ever reach here is if all expressions are true!
        }

        return Literal.FALSE;
    }
}
