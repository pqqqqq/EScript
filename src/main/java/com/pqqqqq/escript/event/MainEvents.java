package com.pqqqqq.escript.event;

import com.pqqqqq.escript.lang.script.Properties;
import com.pqqqqq.escript.lang.trigger.cause.Causes;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

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
                    Causes.MINE.trigger(Properties.builder().event(event).player(player.get())
                            .variable("Block", block.getOriginal()) // TODO more variable stuff!!
                            .build());
                }
            } else if (event instanceof ChangeBlockEvent.Place) {
                for (Transaction<BlockSnapshot> block : event.getTransactions()) {
                    Causes.PLACE.trigger(Properties.builder().event(event).player(player.get())
                            .variable("Block", block.getFinal()) // TODO more variable stuff!!
                            .build());
                }
            }
        }
    }

    @Listener(order = Order.BEFORE_POST)
    public void command(SendCommandEvent event, @First CommandSource source) {
        String command = event.getCommand(); // Won't have '/'
        String arguments = event.getArguments();

        if (source instanceof Player) {
            Causes.COMMAND.trigger(Properties.builder().event(event).player((Player) source)
                    .variable("Command", command)
                    .variable("Arguments", arguments)
                    .build());
        }
    }

    @Listener(order = Order.BEFORE_POST)
    public void interactBlock(InteractBlockEvent event, @First Player player) {
        Causes.INTERACT_BLOCK.trigger(Properties.builder().event(event).player(player)
                .variable("Block", event.getTargetBlock())
                .variable("Interaction", event instanceof InteractBlockEvent.Primary ? "Left" : "Right")
                .build());
    }

    @Listener(order = Order.BEFORE_POST)
    public void interactEntity(InteractEntityEvent event, @First Player player) {
        Causes.INTERACT_ENTITY.trigger(Properties.builder().event(event).player(player)
                .variable("Target", event.getTargetEntity())
                .variable("Interaction", event instanceof InteractEntityEvent.Primary ? "Left" : "Right")
                .build());
    }
}
