package com.pqqqqq.escript.lang.data.serializer.item;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.serializer.Serializer;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.data.store.map.MapModule;
import com.pqqqqq.escript.lang.exception.SerializationException;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

/**
 * Created by Kevin on 2016-09-28.
 * The {@link ItemStack item stack} {@link Serializer serializer}
 */
public class ItemStackSerializer implements Serializer<ItemStack> {
    private static final ItemStackSerializer INSTANCE = new ItemStackSerializer();
    private static final String[] MAPPER = {"type", "amount"};

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
        // TODO: Add all other item stack data

        LiteralStore store = LiteralStore.empty();
        MapModule mapModule = store.getMapModule();

        mapModule.add("type", Literal.fromObject(value.getItem()));
        mapModule.add("amount", Literal.fromObject(value.getQuantity()));
        return Literal.fromObject(store);
    }

    @Override
    public ItemStack deserialize(Literal value) {
        LiteralStore store = value.asStore().collapseMap(MAPPER);
        MapModule mapModule = store.getMapModule();

        if (mapModule.size() < 2) { // We'll allow lenience with having more
            throw new SerializationException("Value \"%s\" has incorrect number of arguments for a ItemStack (<2)", value.asString());
        }

        ItemType itemType = Serializers.ITEM_TYPE.deserialize(mapModule.getLiteral("type"));
        int quantity = mapModule.getLiteral("amount").asNumber().intValue();

        return ItemStack.of(itemType, quantity);
    }

    @Override
    public Class<ItemStack> getCorrespondingClass() {
        return ItemStack.class;
    }
}
