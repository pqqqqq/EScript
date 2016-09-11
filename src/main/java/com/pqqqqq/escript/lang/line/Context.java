package com.pqqqqq.escript.lang.line;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.data.container.DatumContainer;
import com.pqqqqq.escript.lang.exception.MissingGroupException;
import com.pqqqqq.escript.lang.phrase.syntax.Component;
import com.pqqqqq.escript.lang.script.Script;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by Kevin on 2016-09-02.
 *
 * <pre>
 * A context is a bridge that connects a {@link Script script instance} to a given {@link Line line}
 * A context is to a line as a script is to a raw script (that is to say, the version at compile time vs the version at runtime)
 * </pre>
 */
public class Context {
    private final Script script;
    private final Line line;

    private final Map<Component.ArgumentComponent, Literal> resolvedLiterals = new HashMap<>();

    protected Context(Script script, Line line) {
        this.script = script;
        this.line = line;

        // Populate literals
        for (Map.Entry<Component.ArgumentComponent, DatumContainer> entry : line.getContainers().entrySet()) {
            if (entry.getKey().doResolve()) { // Check if should be resolved
                this.resolvedLiterals.put(entry.getKey(), entry.getValue().resolve(this)); // Resolve each
            } else {
                this.resolvedLiterals.put(entry.getKey(), Literal.EMPTY);
            }
        }
    }

    /**
     * The context's referenced {@link Script script instance}
     *
     * @return the script
     */
    public Script getScript() {
        return script;
    }

    /**
     * The context's referenced {@link Line line}
     *
     * @return the line
     */
    public Line getLine() {
        return line;
    }

    /**
     * Gets an {@link Optional optional} {@link Literal literal} (after {@link DatumContainer#resolve(Context) resolved}) for the given group
     *
     * @param group the group
     * @return the literal
     */
    public Optional<Literal> getOptionalLiteral(String group) {
        for (Map.Entry<Component.ArgumentComponent, Literal> entry : this.resolvedLiterals.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(group)) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    /**
     * <pre>
     * Gets the {@link Literal literal} (after {@link DatumContainer#resolve(Context) resolved}) for the given group
     * If no literal is found, a literal with the default value is returned
     * </pre>
     *
     * @param group the group
     * @param def the default {@link Supplier supplier}
     * @return the literal
     */
    public Literal getLiteral(String group, Supplier<?> def) {
        return getOptionalLiteral(group).orElseGet(() -> Literal.fromObject(def.get()));
    }

    /**
     * <pre>
     * Gets the {@link Literal literal} (after {@link DatumContainer#resolve(Context) resolved}) for the given group
     * If no literal is found, a literal with the default value is returned
     * </pre>
     *
     * @param group the group
     * @param def the default value
     * @return the literal
     */
    public Literal getLiteral(String group, Object def) {
        return getOptionalLiteral(group).orElse(Literal.fromObject(def));
    }

    /**
     * <pre>
     * Gets the {@link Literal literal} (after {@link DatumContainer#resolve(Context) resolved}) for the given group
     * If no literal is found in that group, a {@link com.pqqqqq.escript.lang.exception.MissingGroupException missing group exception (MGE)} is thrown
     * </pre>
     *
     * @param group the group
     * @return the literal
     */
    public Literal getLiteral(String group) {
        return getOptionalLiteral(group).orElseThrow(() -> new MissingGroupException("Group could not be found: \"%s\"", group));
    }

    /**
     * Gets the {@link DatumContainer container} for the given group
     *
     * @param group the group
     * @return the container, or {@link Optional#empty()}
     */
    public Optional<DatumContainer> getOptionalContainer(String group) {
        return getLine().getContainer(group);
    }

    /**
     * <pre>
     * Gets the {@link DatumContainer container} for the given group
     * If no container is found in that group, a {@link com.pqqqqq.escript.lang.exception.MissingGroupException missing group exception (MGE)} is thrown
     * </pre>
     *
     * @param group the group
     * @return the container
     */
    public DatumContainer getContainer(String group) {
        return getOptionalContainer(group).orElseThrow(() -> new MissingGroupException("Group could not be found: \"%s\"", group));
    }

    /**
     * Gets the strarg for the given group
     *
     * @param group the group
     * @return the container, or {@link Optional#empty()}
     */
    public Optional<String> getOptionalStrarg(String group) {
        return getLine().getStrarg(group);
    }

    /**
     * <pre>
     * Gets the strarg for the given group
     * If no strarg is found in that group, a {@link com.pqqqqq.escript.lang.exception.MissingGroupException missing group exception (MGE)} is thrown
     * </pre>
     *
     * @param group the group
     * @return the container
     */
    public String getStrarg(String group) {
        return getOptionalStrarg(group).orElseThrow(() -> new MissingGroupException("Group could not be found: \"%s\"", group));
    }

    // CONVENIENCE LITERALS \\

    /**
     * <pre>
     * Attempts to get the {@link Player player} based on the given group
     * If the group is not present, the player who triggered the event is returned
     * If the group is present, however the player is not in the server, {@link Optional#empty()} is returned
     * </pre>
     *
     * @param group the group of the player
     * @return the player (see above description)
     */
    public Optional<Player> getOptionalPlayer(String group) {
        Optional<Literal> playerLiteral = getOptionalLiteral(group);
        if (playerLiteral.isPresent()) {
            String playerString = playerLiteral.get().asString();
            Optional<Player> player = Sponge.getServer().getPlayer(playerString); // Try by name first

            try {
                player = Sponge.getServer().getPlayer(UUID.fromString(playerString)); // Try by uuid otherwise
            } catch (IllegalArgumentException e) { // Ignore this
            }

            return player;
        }

        return Optional.ofNullable(script.getProperties().getPlayer()); // Otherwise, get the triggerer (which may be null)
    }

    /**
     * <pre>
     * Attempts to get the {@link Player player} based on the given group
     * If the group is not present, the player who triggered the event is returned
     * If the group is present, however the player is not in the server, a {@link com.pqqqqq.escript.lang.exception.MissingGroupException missing group exception (MGE)} is thrown
     * </pre>
     *
     * @param group the group of the player
     * @return the player (see above description)
     */
    public Player getPlayer(String group) {
        return getOptionalPlayer(group).orElseThrow(() -> new MissingGroupException("A player could not be found for the given group: %s", group));
    }

    /**
     * Converts this {@link Context context} into a {@link RunVessel run vessel}
     *
     * @return the {@link RunVessel vessel}
     */
    public RunVessel toVessel() {
        RunVessel vessel = new RunVessel(this);
        return vessel;
    }

    public void executeBlock() {
        for (Line block : line.getLineBlock()) {
            if (block.getTabulations() == (line.getTabulations() + 1)) { // We only want lines with one more tabulation
                RunVessel vessel = block.toContext(script).toVessel();
                vessel.run();

                script.getRanVessels().add(vessel);
            }
        }
    }
}
