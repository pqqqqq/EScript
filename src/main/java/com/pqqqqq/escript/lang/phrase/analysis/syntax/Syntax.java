package com.pqqqqq.escript.lang.phrase.analysis.syntax;

import com.pqqqqq.escript.lang.data.mutable.property.PropertyType;
import com.pqqqqq.escript.lang.exception.SyntaxMatchingException;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.analysis.Analysis;
import com.pqqqqq.escript.lang.phrase.analysis.AnalysisResult;
import com.pqqqqq.escript.lang.util.string.StringTransformer;
import com.pqqqqq.escript.lang.util.string.StringUtilities;
import com.pqqqqq.escript.lang.util.string.TrackerProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Created by Kevin on 2016-09-07.
 * <p>
 * <pre>
 * Defines the necessary syntax a {@link Phrase} must fulfill
 * </pre>
 */
public class Syntax {
    private static final Pattern TYPE_PATTERN = Pattern.compile("\\{(?<Type>.+?)\\}");
    private static final String[] PREFIXES = {"$", "^", "#", "@"};
    private static final String[] SUFFIXES = {"?", "*"};

    private final Component[] components;
    private final boolean colon;

    /**
     * Creates a new syntax instance from the {@link Component components}
     *
     * @param components the components
     * @return the new instance
     */
    public static Syntax from(Component... components) {
        return new Syntax(components, false);
    }

    /**
     * <pre>
     * Compiles a new syntax string, based on the following principles:
     * <li>Each {@link Component component} is separated by a whitespace character</li>
     * <li>If there is a dollar sign '$' at the start, it is treated as a {@link Component.ArgumentComponent argument}
     * <li>If there is a up sign '^' at the start, it is treated as a argument, but one which should not be sequenced
     * <li>If there is a pound sign '#' at the start, it is treated as a argument, but one which should not be resolved
     * <li>If there is a at sign '@' at the start, it is treated as an argument, but one that is tentative (that is, if the component following it
     * matches, it won't take up any strings.
     * <li>Otherwise, it is treated as a {@link Component.TextComponent text component}
     * <li>If there is a question mark '?' at the end, it is wrapped in a {@link Component.OptionalComponent optional component}
     * <li>If there is a asterisk '*' at the end, it is wrapped in an {@link Component.IfComponent if component}
     * </pre>
     *
     * @param string the string
     * @return the new syntax
     */
    public static Syntax compile(String string) {
        boolean colon = string.endsWith(":");
        if (colon) { // Remove colon if present
            string = StringUtils.removeEnd(string, ":");
        }

        List<String> split = StringUtilities.from(string).parseSplit(TrackerProperties.builder().brackets(true).quotes(false).build(), " "); // We care about brackets, but not quotes
        List<Component> components = new ArrayList<>();

        for (String stringComponent : split) {
            StringTransformer transformer = StringTransformer.from(stringComponent);
            Component.Wrap wrap = Component.Wrap.NONE;
            List<PropertyType> propertyTypes = new ArrayList<>();

            // First we have to remove any property types
            String typesRaw = transformer.remove(TYPE_PATTERN, "Type");
            if (typesRaw != null && !typesRaw.isEmpty()) {
                String[] types = typesRaw.split(","); // Split by comma

                for (String type : types) {
                    Optional<PropertyType> propertyType = PropertyType.fromString(type.trim()); // Trim & get property type
                    if (propertyType.isPresent()) { // Doesn't really matter if it isn't present, just make sure it is
                        propertyTypes.add(propertyType.get());
                    }
                }
            }

            // Remove suffixes first (for wrapping and such)
            Set<String> suffixSet = transformer.removeEnd(SUFFIXES);
            if (suffixSet.contains("?")) {
                wrap = Component.Wrap.OPTIONAL;
            } else if (suffixSet.contains("*")) {
                wrap = Component.Wrap.IF;
            }

            // Remove prefixes
            Set<String> prefixSet = transformer.removeStart(PREFIXES);
            if (prefixSet.contains("$")) { // Argument?
                Component.ArgumentComponent.Builder builder = Component.ArgumentComponent.builder().name(transformer.getCurrentResult())
                        .types(propertyTypes) // Set property types
                        .sequence(!prefixSet.contains("^")) // ^ = NO sequence
                        .resolve(!prefixSet.contains("#")) // # = NO resolve
                        .tentative(prefixSet.contains("@")); // @ = tentative

                components.add(wrap.wrap(builder.build()));
            } else { // Nope
                components.add(wrap.wrap(Component.TextComponent.from(transformer.getCurrentResult().split("\\|")))); // Split by pipe (ors)
            }
        }

        return new Syntax(components.toArray(new Component[components.size()]), colon);
    }

    private Syntax(Component[] components, boolean colon) {
        this.components = components;
        this.colon = colon;
    }

    /**
     * Gets the array of {@link Component components}
     *
     * @return the components
     */
    public Component[] getComponents() {
        return components;
    }

    /**
     * Checks if this syntax matches the line
     *
     * @param analysis the current {@link Analysis analysis}
     * @return an {@link AnalysisResult.Builder analysis builder} or {@link Optional#empty()} if there was no match
     */
    public synchronized Optional<AnalysisResult.Builder> matches(Analysis analysis) {
        try {
            // Check colon first
            if (colon != analysis.lineHasColon()) { // Must be in agreeance
                return Optional.empty();
            }

            Map<Component.ArgumentComponent, String> map = new HashMap<>();
            LinkedList<Component> compQueue = new LinkedList<>(Arrays.asList(getComponents())); // Create linked list
            Deque<String> stringQueue = new ArrayDeque<>(analysis.getStringComponents()); // String split deque

            Component component;
            boolean lastMatch = false;

            while ((component = compQueue.poll()) != null) { // Poll, not pop, for no errors
                Optional<Map<Component.ArgumentComponent, String>> match = matchesLoad(component, compQueue, stringQueue, lastMatch);

                if (match == null) { // Null just means the optional didn't match, just track it
                    lastMatch = false;
                } else if (!match.isPresent()) {
                    return Optional.empty();
                } else {
                    map.putAll(match.get());
                    lastMatch = true;
                }
            }

            // If it's empty, we've done our job, otherwise we're missing components, but we must make sure none are optional
            //boolean check = queue.stream().filter((comp) -> !(comp instanceof Component.OptionalComponent)).findAny().isPresent();
            return !stringQueue.isEmpty() ? Optional.empty() : Optional.of(AnalysisResult.builder().strargs(map));
        } catch (Throwable e) {
            throw new SyntaxMatchingException(e, "Error when matching phrase '%s' with syntax: '%s'", analysis.getLine(), Arrays.toString(components));
        }
    }

    private synchronized Optional<Map<Component.ArgumentComponent, String>> matchesLoad(Component component, LinkedList<Component> compQueue, Deque<String> stringQueue, boolean lastMatch) {
        Map<Component.ArgumentComponent, String> map = new HashMap<>(1); // Should only be one entry, or zero

        if (component.isArgument()) { // Arguments should continue until the next component matches
            Component.ArgumentComponent argComp = (Component.ArgumentComponent) component;

            String stringComponent = argComp.isTentative() ? "" : stringQueue.poll(); // Don't poll if tenatative, because we don't want to remove a string if the next component matches
            String nextString;

            main:
            while ((nextString = stringQueue.peek()) != null) {
                int iterations = 0;

                LinkedList<Component> copyCompQueue = new LinkedList<>(compQueue); // Need a copy to avoid CME
                for (Component testComp : copyCompQueue) {
                    if (testComp.matches(nextString) && (iterations == 0 || !testComp.isIf())) { // Next component matches, ends the argument eating. If an if is here, then if its first it's okay, otherwise not
                        IntStream.rangeClosed(1, iterations).forEach(num -> compQueue.poll()); // Poll out ones that have been skipped
                        break main;
                    } else if (!testComp.isOptional() && !testComp.isIf()) { // Not optional, continue argument eating
                        break;
                    }

                    iterations++;
                }

                stringComponent += (stringComponent.isEmpty() ? "" : " ") + stringQueue.poll(); // Poll it out
            }

            if (!stringComponent.isEmpty()) { // Empty means tentative, and no argument
                map.put(argComp, stringComponent);
            }
        } else if (component.isOptional()) {
            String stringComponent = stringQueue.peek(); // Just peek first, remove if okay

            if (stringComponent != null && component.matches(stringComponent)) { // It's okay for stringComponent to be null (just don't throw anything)
                map.putAll(matchesLoad(((Component.OptionalComponent) component).getComponent(), compQueue, stringQueue, lastMatch).get()); // Should be present, since component matched
            } else {
                return null; // The null here is just to track that the optional didn't match, it will be dealt with above
            }
        } else if (component.isIf()) {
            String stringComponent = stringQueue.peek(); // Just peek first, remove if okay

            if (stringComponent != null && component.matches(stringComponent) && lastMatch) { // It's okay for stringComponent to be null (just don't throw anything)
                map.putAll(matchesLoad(((Component.IfComponent) component).getComponent(), compQueue, stringQueue, true).orElseGet(HashMap::new));
            } else {
                return null;
            }
        } else {
            String stringComponent = stringQueue.poll(); // We're okay to poll
            if (!component.matches(stringComponent)) {
                return Optional.empty(); // Empty if it doesn't match
            }
        }

        return Optional.of(map);
    }
}
