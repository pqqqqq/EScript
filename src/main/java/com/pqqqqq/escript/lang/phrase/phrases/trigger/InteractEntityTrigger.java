package com.pqqqqq.escript.lang.phrase.phrases.trigger;

import com.pqqqqq.escript.lang.data.mutable.property.PropertyType;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.analysis.syntax.Syntax;
import com.pqqqqq.escript.lang.script.Script;
import com.pqqqqq.escript.lang.trigger.Trigger;
import com.pqqqqq.escript.lang.trigger.cause.Causes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kevin on 2016-08-31.
 * <p>
 * <pre>
 * The interact entity trigger phrase, which fires when a player interacts with an entity.
 * Some ways of doing this:
 *
 *     <code>on left click of "minecraft:player":
 *     when an entity is right clicked:</code>
 * </pre>
 */
public class InteractEntityTrigger implements Phrase {
    private static final InteractEntityTrigger INSTANCE = new InteractEntityTrigger();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("on $^Click click of a|an? ${entity, player}Entity:"),
            Syntax.compile("when|if a|an? ${entity, player}Entity is|are? $^Click click|clicked:"),
    };

    /**
     * Gets the main interact entity trigger instance
     *
     * @return the instance
     */
    public static InteractEntityTrigger instance() {
        return INSTANCE;
    }

    private InteractEntityTrigger() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Script.State getRunState() {
        return Script.State.COMPILE_TIME; // Run at compile time
    }

    @Override
    public Result execute(Context ctx) {
        LiteralStore interactTypes = ctx.getLiteral("Entity", (Object) null).asStore();
        String click = ctx.getStrarg("Click");

        List<EntityType> entityTypes = new ArrayList<>();
        interactTypes.getListModule().literalStream().map(Serializers.ENTITY_TYPE::deserialize).forEach(entityTypes::add);

        Trigger.builder().script(ctx.getLine().getRawScript()).causes(Causes.INTERACT_ENTITY).predicate((properties) -> {
            Optional<String> interaction = properties.getValue(PropertyType.INTERACTION, String.class);
            if (!interaction.isPresent() || !interaction.get().equalsIgnoreCase(click)) {
                return false;
            }

            if (entityTypes.isEmpty()) { // Empty = any entity
                return true;
            } else {
                Optional<Entity> entity = properties.getValue(PropertyType.ENTITY, Entity.class);
                if (!entity.isPresent()) {
                    return false;
                }

                return entityTypes.contains(entity.get().getType());
            }
        }).build();
        return Result.success();
    }
}
