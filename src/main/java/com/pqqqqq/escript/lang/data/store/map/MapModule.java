package com.pqqqqq.escript.lang.data.store.map;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.store.Module;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A map {@link Module module} interface.
 * The map module extends upon the module with map-specific actions
 */
public interface MapModule extends Module<String>, Iterable<Map.Entry<String, MutableValue<Literal>>> {

    /**
     * Performs an action for each entry of String and {@link MutableValue mutable value} in this map module.
     *
     * @param biConsumer the {@link BiConsumer bi consumer} action performer
     */
    default void forEach(BiConsumer<String, MutableValue<Literal>> biConsumer) {
        for (Map.Entry<String, MutableValue<Literal>> entry : this) {
            biConsumer.accept(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Performs an action for each entry of String and {@link Literal literal} in this map module.
     *
     * @param biConsumer the {@link BiConsumer bi consumer} action performer
     */
    default void forEachLiteral(BiConsumer<String, Literal> biConsumer) {
        for (Map.Entry<String, MutableValue<Literal>> entry : this) {
            biConsumer.accept(entry.getKey(), entry.getValue().getValue());
        }
    }
}
