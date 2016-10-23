package com.pqqqqq.escript.lang.data.store.list;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.mutable.SimpleMutableValue;
import com.pqqqqq.escript.lang.data.store.Module;

import java.util.List;

/**
 * A list {@link Module module} interface.
 * The list module extends upon the module with list-specific actions, as well as provides {@link Iterable iteration}.
 * List modules also incorporate some stack methods, including pop, popLast, peek, and peekLast.
 */
public interface ListModule extends Module<Integer>, Iterable<MutableValue<Literal>> {

    /**
     * Converts this literal module to a {@link List list} of immutable {@link Literal literals}
     *
     * @return the list
     */
    List<Literal> toImmutable();

    /**
     * <pre>
     * Adds a {@link MutableValue mutable literal value} to the back of the list
     * </pre>
     *
     * @param value the value
     * @return the value that was added
     */
    MutableValue<Literal> add(MutableValue<Literal> value);

    /**
     * <pre>
     * Adds a new mutable value, according to {@link #add(MutableValue)}
     * </pre>
     *
     * @param value the literal value
     * @return the value that was added
     */
    default MutableValue<Literal> add(Literal value) {
        return add(SimpleMutableValue.from(value));
    }

    /**
     * Attempts to pop the list module.
     * Popping is removing, and returning the head of the list (the first element; or the element at 1 for a base-1 list)
     *
     * @return the popped element, or null if the list is empty
     */
    default MutableValue<Literal> pop() {
        if (isEmpty()) {
            return null;
        }

        return remove(1);
    }

    /**
     * Attempts to pop the last entry of the list module.
     * Popping is removing, and returning the tail of the list (the last element; or the element at the list size for a base-1 list)
     *
     * @return the popped element, or null if the list is empty
     */
    default MutableValue<Literal> popLast() {
        if (isEmpty()) {
            return null;
        }

        return remove(size());
    }

    /**
     * A peek is similar to a {@link #pop()}, however it does not remove the entry, it only retrieves it.
     * The head of the list (first element) is retrieved
     *
     * @return the first element, or null if the list is empty
     */
    default MutableValue<Literal> peek() {
        return get(1);
    }

    /**
     * A peek is similar to a {@link #peekLast()}, however it does not remove the entry, it only retrieves it.
     * The tail of the list (last element) is retrieved
     *
     * @return the last element, or null if the list is empty
     */
    default MutableValue<Literal> peekLast() {
        return get(size());
    }
}
