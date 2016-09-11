package com.pqqqqq.escript.lang.util.string;

import org.apache.commons.lang3.tuple.Triple;

/**
 * Represents an immutable split sequence, a {@link Triple} String of the before(L) and after(R) segments, as well as the delimiter(M)
 */
public class SplitSequence extends Triple<String, String, String> {
    private final String beforeSegment;
    private final String delimiter;
    private final String afterSegment;

    /**
     * Creates a new split sequence
     *
     * @param beforeSegment the before segment
     * @param delimiter     the delimiter
     * @param afterSegment  the after segment
     */
    public SplitSequence(String beforeSegment, String delimiter, String afterSegment) {
        this.beforeSegment = beforeSegment;
        this.delimiter = delimiter;
        this.afterSegment = afterSegment;
    }

    @Override
    public String getLeft() {
        return beforeSegment;
    }

    @Override
    public String getMiddle() {
        return delimiter;
    }

    @Override
    public String getRight() {
        return afterSegment;
    }

    /**
     * Gets the before segment. This is analogous to {@link #getLeft()}
     *
     * @return the before segment string
     */
    public String getBeforeSegment() {
        return beforeSegment;
    }

    /**
     * Gets the split. This is analogous to {@link #getMiddle()}
     *
     * @return the split
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * Gets the after segment. This is analogous to {@link #getRight()}
     *
     * @return the after segment string
     */
    public String getAfterSegment() {
        return afterSegment;
    }
}