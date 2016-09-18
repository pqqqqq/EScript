package com.pqqqqq.escript.event;

import com.pqqqqq.escript.lang.script.Properties;
import com.pqqqqq.escript.lang.trigger.cause.Causes;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Kevin on 2016-09-02.
 */
public class MainEvents {

    @Listener(order = Order.BEFORE_POST)
    public void blockChange(ChangeBlockEvent event) {
        Optional<Player> player = event.getCause().first(Player.class);

        if (player.isPresent()) {
            if (event instanceof ChangeBlockEvent.Break) {
                for (Transaction<BlockSnapshot> block : event.getTransactions()) {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("Block", block.getOriginal());

                    Causes.MINE.trigger(Properties.builder().event(event).player(player.get()).variables(variables).build()); // TODO more variable stuff!!
                }
            } else if (event instanceof ChangeBlockEvent.Place) {
                for (Transaction<BlockSnapshot> block : event.getTransactions()) {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("Block", block.getFinal());

                    Causes.PLACE.trigger(Properties.builder().event(event).player(player.get()).variables(variables).build()); // TODO more variable stuff!!
                }
            }
        }
    }
}
