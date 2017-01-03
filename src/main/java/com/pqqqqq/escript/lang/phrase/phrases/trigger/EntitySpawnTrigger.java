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
 * The entity spawn trigger phrase, which fires when an entity is spawned into the world.
 * Some ways of doing this:
 *
 *     <code>on spawn of entity:
 *     of spawn of "minecraft:zombie":</code>
 * </pre>
 */
public class EntitySpawnTrigger implements Phrase {
    private static final EntitySpawnTrigger INSTANCE = new EntitySpawnTrigger();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("on spawn|create|creation of $Entities:")
    };

    /**
     * Gets the main interact block trigger instance
     *
     * @return the instance
     */
    public static EntitySpawnTrigger instance() {
        return INSTANCE;
    }

    private EntitySpawnTrigger() {
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
        LiteralStore entityStore = ctx.getLiteral("Entities", (Object) null).asStore();

        List<EntityType> entityTypes = new ArrayList<>();
        entityStore.getListModule().literalStream().map(Serializers.ENTITY_TYPE::deserialize).forEach(entityTypes::add);

        Trigger.builder().script(ctx.getLine().getRawScript()).causes(Causes.SPAWN_ENTITY).predicate((properties) -> {
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
