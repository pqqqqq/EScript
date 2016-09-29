package com.pqqqqq.escript.lang.data.container;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;

/**
 * Created by Kevin on 2016-09-27.
 * Represents a {@link DatumContainer datum container} for ranges
 */
public class RangeContainer implements DatumContainer {
    private final DatumContainer lowContainer;
    private final DatumContainer highContainer;

    /**
     * Creates a new range container from the low and high containers
     *
     * @param lowContainer  the lower boundary
     * @param highContainer the upper boundary
     */
    public RangeContainer(DatumContainer lowContainer, DatumContainer highContainer) {
        this.lowContainer = lowContainer;
        this.highContainer = highContainer;
    }

    @Override
    public Literal resolve(Context ctx) {
        int low = lowContainer.resolve(ctx).asNumber().intValue();
        int high = highContainer.resolve(ctx).asNumber().intValue();

        int realLow = Math.min(low, high);
        int realHigh = Math.max(low, high);

        Integer[] numbers = new Integer[realHigh - realLow + 1];
        for (int i = realLow; i <= realHigh; i++) {
            numbers[i - realLow] = i;
        }

        return Literal.fromObject(numbers);
    }
}
