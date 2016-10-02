package com.pqqqqq.escript.lang.phrase.analysis.syntax;

import com.pqqqqq.escript.lang.line.Line;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kevin on 2016-09-07.
 * <p>
 * <pre>
 * A syntax component, which must match and remove the head of a given string
 * </pre>
 */
public interface Component {

    /**
     * Checks if the given component matches the string
     *
     * @param s the string
     * @return true if matches
     */
    boolean matches(String s);

    /**
     * Checks if the given component matches the {@link Line line}
     *
     * @param line the line
     * @return true if matches
     */
    default boolean matches(Line line) {
        return matches(line.getLine());
    }

    /**
     * Checks if this component is a {@link ArgumentComponent argument}
     *
     * @return true if an argument
     */
    default boolean isArgument() {
        return this instanceof ArgumentComponent;
    }

    /**
     * Checks if this component is an {@link OptionalComponent optional component}
     *
     * @return true if optional
     */
    default boolean isOptional() {
        return this instanceof OptionalComponent;
    }

    /**
     * Checks if this component is a {@link TextComponent text component}
     *
     * @return true if a text component
     */
    default boolean isText() {
        return this instanceof ArgumentComponent;
    }

    /**
     * Checks if this component is an {@link IfComponent if component}
     *
     * @return true if an if component
     */
    default boolean isIf() {
        return this instanceof IfComponent;
    }

    /**
     * An enumeration of possible {@link Component wrappable components}
     */
    enum Wrap {
        OPTIONAL() {
            @Override
            Component wrap(Component component) {
                return OptionalComponent.from(component);
            }
        },

        IF() {
            @Override
            Component wrap(Component component) {
                return IfComponent.from(component);
            }
        },

        NONE() {
            @Override
            Component wrap(Component component) {
                return component;
            }
        };

        /**
         * Wraps the given {@link Component component}
         *
         * @param component the component
         * @return the new wrapped component
         */
        abstract Component wrap(Component component);
    }

    /**
     * Created by Kevin on 2016-09-08.
     * <p>
     * <pre>
     * An argument, which can have any value (as long as it follows the main syntax of the ES language)
     * </pre>
     */
    class ArgumentComponent implements Component {
        private final String name;
        private final boolean resolve;
        private final boolean sequence;
        private final boolean tentative;

        /**
         * Creates a new builder
         *
         * @return the new builder
         */
        public static Builder builder() {
            return new Builder();
        }

        private ArgumentComponent(String name, boolean resolve, boolean sequence, boolean tentative) {
            this.name = name;
            this.resolve = resolve;
            this.sequence = sequence;
            this.tentative = tentative;
        }

        /**
         * Gets the name of this argument
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Whether to resolve the argument at runtime or not
         *
         * @return true if should resolve
         */
        public boolean doResolve() {
            return resolve;
        }

        /**
         * Whether to sequence the argument at runtime or not
         *
         * @return true if should sequence
         */
        public boolean doSequence() {
            return sequence;
        }

        /**
         * Whether this argument is tentative
         *
         * @return true if tentative
         */
        public boolean isTentative() {
            return tentative;
        }

        @Override
        public boolean matches(String s) {
            return true; // Should always be fine
        }

        @Override
        public String toString() {
            return "ARGUMENT: {" + getName() + "}";
        }

        /**
         * The argument component builder
         */
        public static class Builder {
            private String name = null;
            private boolean resolve = true;
            private boolean sequence = true;
            private boolean tentative = false;

            private Builder() {
            }

            /**
             * Sets the name of the argument component
             *
             * @param name the new name
             * @return this builder, for chaining
             */
            public Builder name(String name) {
                this.name = name;
                return this;
            }

            /**
             * Sets whether this argument should be resolved
             *
             * @param resolve whether to resolve, or not
             * @return this builder, for chaining
             */
            public Builder resolve(boolean resolve) {
                this.resolve = resolve;
                return this;
            }

            /**
             * Sets whether this argument should be sequenced
             *
             * @param sequence whether to sequence, or not
             * @return this builder, for chaining
             */
            public Builder sequence(boolean sequence) {
                this.sequence = sequence;
                return this;
            }

            /**
             * Sets whether this argument should be marked as tentative
             *
             * @param tentative whether to be marked as tentative, or not
             * @return this builder, for chaining
             */
            public Builder tentative(boolean tentative) {
                this.tentative = tentative;
                return this;
            }

            /**
             * Builds the argument component
             *
             * @return the new built component
             */
            public ArgumentComponent build() {
                return new ArgumentComponent(checkNotNull(name, "Name cannot be null"), resolve, sequence, tentative);
            }
        }
    }

    /**
     * Created by Kevin on 2016-09-08.
     * <p>
     * <pre>
     * A {@link Component component} that wraps around another to indicate its optionality
     * </pre>
     */
    class OptionalComponent implements Component {
        private final Component component;

        /**
         * Wraps the given {@link Component component} in the optional component
         *
         * @param component the component
         * @return the new optional component
         */
        public static OptionalComponent from(Component component) {
            return new OptionalComponent(component);
        }

        private OptionalComponent(Component component) {
            this.component = component;
        }

        /**
         * Gets the {@link Component component} that is wrapped
         *
         * @return the component
         */
        public Component getComponent() {
            return component;
        }

        @Override
        public boolean matches(String s) {
            return component.matches(s);
        }

        @Override
        public String toString() {
            return "OPTIONAL: {" + getComponent().toString() + "}";
        }
    }

    /**
     * Created by Kevin on 2016-09-07.
     * <p>
     * <pre>
     * A plain text {@link Component component}
     * </pre>
     */
    class TextComponent implements Component {
        private final String[] texts;

        /**
         * Creates a new text component from the list of texts
         *
         * @param texts the string texts
         * @return the new instance
         */
        public static TextComponent from(String... texts) {
            return new TextComponent(texts);
        }

        private TextComponent(String[] texts) {
            this.texts = texts;
        }

        /**
         * Gets the texts
         *
         * @return the array of texts
         */
        public String[] getTexts() {
            return texts;
        }

        @Override
        public boolean matches(String s) {
            for (String text : getTexts()) {
                if (text.equalsIgnoreCase(s)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public String toString() {
            return "TEXT: {" + Arrays.toString(texts) + "}";
        }
    }

    /**
     * An if {@link Component component}, which is mandatory if the component before it matches, otherwise it is completed neglected (won't even attempt to match)
     */
    class IfComponent implements Component {
        private final Component component;

        /**
         * Wraps the given {@link Component component} in the if component
         *
         * @param component the component
         * @return the new if component
         */
        public static IfComponent from(Component component) {
            return new IfComponent(component);
        }

        private IfComponent(Component component) {
            this.component = component;
        }

        /**
         * Gets the {@link Component component} that is wrapped
         *
         * @return the component
         */
        public Component getComponent() {
            return component;
        }

        @Override
        public boolean matches(String s) {
            return component.matches(s);
        }

        @Override
        public String toString() {
            return "IF: {" + getComponent().toString() + "}";
        }
    }

    /**
     * A hybrid {@link Component component}, made from multiple other components
     */
/*    class HybridComponent implements Component {
        private final Component[] components;

        *//**
     * Creates a new hybrid component from an array of other {@link Component components}
     *
     * @param components the components
     * @return the new hybrid instance
     *//*
        public static HybridComponent from(Component... components) {
            return new HybridComponent(components);
        }

        *//**
     * Creates a new hybrid component from an array of other {@link Component components}
     *
     * @param optional whether this component is optional or not
     * @param components the components
     * @return the new hybrid instance
     *//*
        public static Component from(boolean optional, Component... components) {
            HybridComponent hybridComponent = new HybridComponent(components);
            return optional ? OptionalComponent.from(hybridComponent) : hybridComponent;
        }

        private HybridComponent(Component[] components) {
            this.components = components;
        }

        *//**
     * Gets the array of represented {@link Component components}
     *
     * @return the components
     *//*
        public Component[] getComponents() {
            return components;
        }

        @Override
        public boolean matches(String s) {
            String current = s;

            main:
            for (Component component : getComponents()) {
                if (component instanceof TextComponent) {
                    for (String text : ((TextComponent) component).getTexts()) {
                        Pair<String, Boolean> replace = StringUtils.from(current).replaceFirst(text, "");

                        if (replace.getRight()) {
                            current = replace.getLeft();
                            continue main;
                        }
                    }
                }
            }

            return false;
        }
    }*/
}
