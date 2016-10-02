package com.pqqqqq.escript.lang.data.serializer;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.exception.SerializationException;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;

/**
 * Created by Kevin on 2016-09-28.
 * The {@link ItemStack item stack} {@link Serializer serializer}
 */
public class ItemStackSerializer implements Serializer<ItemStack> {
    private static final ItemStackSerializer INSTANCE = new ItemStackSerializer();

    /**
     * Gets the main instance
     *
     * @return the main instance
     */
    public static ItemStackSerializer instance() {
        return INSTANCE;
    }

    private ItemStackSerializer() {
    }

    @Override
    public Literal serialize(ItemStack value) {
        // Serialize into array
        // TODO: Add all other item stack data, plus use maps

        Object[] array = new Object[2];
        array[0] = value.getItem();
        array[1] = value.getQuantity();
        return Literal.fromObject(array);
    }

    @Override
    public ItemStack deserialize(Literal value) {
        // ItemStacks can be deserialized by lists or strings
        if (value.isString()) {
            return deserialize(Literal.fromObject(value.asString().split(","))); // Strings will just separate them by commas
        }

        List<Literal> list = value.asList();
        if (list.size() < 2) { // We'll allow lenience with having more
            throw new SerializationException("Value \"%s\" has incorrect number of arguments for a ItemStack (<2)", value.asString());
        }

        ItemType itemType = Serializers.ITEM_TYPE.deserialize(list.get(0));
        int quantity = list.get(1).asNumber().intValue();

        return ItemStack.of(itemType, quantity);
    }

    @Override
    public Class<ItemStack> getCorrespondingClass() {
        return ItemStack.class;
    }
}
