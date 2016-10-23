package com.pqqqqq.escript.lang.util;

/**
 * Represents a class that can be copied. This is offered as an alternative to {@link Cloneable cloneable objects}.
 * Copyable types <strong>should copy any fields that are also Copyable types.</strong>
 *
 * @param <T> the copied classes's type
 */
public interface Copyable<T> {

    /**
     * Copies the class and its fields
     *
     * @return the copied class
     */
    T copy();
}
