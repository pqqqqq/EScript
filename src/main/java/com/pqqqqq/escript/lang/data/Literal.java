package com.pqqqqq.escript.lang.data;

import com.pqqqqq.escript.lang.data.mutable.MutableValue;
import com.pqqqqq.escript.lang.data.mutable.SimpleMutableValue;
import com.pqqqqq.escript.lang.data.serializer.Serializers;
import com.pqqqqq.escript.lang.data.store.LiteralStore;
import com.pqqqqq.escript.lang.data.store.map.EntryReplicate;
import com.pqqqqq.escript.lang.exception.FormatException;
import com.pqqqqq.escript.lang.exception.InvalidTypeException;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.util.string.StringUtilities;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.*;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * An immutable literal type - a type that does not need to be resolved
 */
public class Literal implements Datum {
    private final Optional<?> value;

    /**
     * An empty literal, where the value is null
     */
    public static final Literal EMPTY = new Literal();

    /**
     * A literal with an empty string - ""
     */
    public static final Literal EMPTY_STRING = new Literal("");

    /**
     * A literal with the boolean value true
     */
    public static final Literal TRUE = new Literal(true);

    /**
     * A literal with the boolean value false
     */
    public static final Literal FALSE = new Literal(false);

    /**
     * A literal with the number 0
     */
    public static final Literal ZERO = new Literal(0D);

    /**
     * A literal with the number 1
     */
    public static final Literal ONE = new Literal(1D);

    /**
     * <pre>
     * Creates a new literal that represents the given object
     * The main purpose of this method's body is to reduce instances of Literals by using common ones
     * This method will make use of these public, final literals, plus any {@link Keyword keywords}:
     *
     *      {@link #EMPTY}
     *      {@link #EMPTY_STRING}
     *      {@link #TRUE}
     *      {@link #FALSE}
     *      {@link #ZERO}
     *      {@link #ONE}
     *      </pre>
     *
     * @param value the value
     * @return the created (or referenced) literal instance
     */
    public static Literal fromObject(Object value) {
        if (value == null) {
            return EMPTY;
        } else if (value instanceof Optional) {
            return fromObject(((Optional<?>) value).orElse(null));
        } else if (value instanceof Literal) {
            return (Literal) value; // No need to change anything
        } else if (value instanceof String) {
            String string = (String) value;

            if (string.isEmpty()) {
                return EMPTY_STRING;
            } else {
                return new Literal(StringUtilities.from(string).formatColour());
            }
        } else if (value instanceof Boolean) {
            return (Boolean) value ? TRUE : FALSE;
        } else if (value instanceof Integer || value instanceof Long || value instanceof Float) { // Number formatting
            return fromObject(Double.parseDouble(value.toString())); // Recursion, now as a double
        } else if (value instanceof Double) {
            Double number = (Double) value;

            if (number == 0D) {
                return ZERO;
            } else if (number == 1D) {
                return ONE;
            }

            return new Literal(number);
        } else if (value.getClass().isArray()) { // Check if the value is an array
            return fromObject(Arrays.asList((Object[]) value));
        } else if (value instanceof Collection) { // Array formatting
            Collection<?> collection = (Collection<?>) value;
            List<MutableValue<Literal>> list = new ArrayList<>();

            collection.stream().map(Literal::fromObject).map(SimpleMutableValue::from).forEach(list::add);
            return new Literal(LiteralStore.builder().listMV(list).build()); // Literal store
        } else if (value instanceof Map) { // Map formatting
            Map<?, ?> map = (Map<?, ?>) value;
            Map<String, MutableValue<Literal>> newMap = new HashMap<>();

            map.entrySet().forEach(entry -> newMap.put(entry.getKey().toString(), SimpleMutableValue.from(Literal.fromObject(entry.getValue()))));
            return new Literal(LiteralStore.builder().mapMV(newMap).build());
        } else if (value instanceof Keyword) {
            return ((Keyword) value).getLiteral();
        } else if (value instanceof LiteralStore) {
            return new Literal(value); // Just letting it through
        } else if (value instanceof EntryReplicate) {
            return new Literal(value); // Just letting it through
        } else if (value instanceof MutableValue) {
            return fromObject(((MutableValue<?>) value).getValue()); // Get the actual value
        }

        // If we reach the end, try serializing it
        Optional<Literal> serialized = Serializers.instance().serialize(value);
        if (serialized.isPresent()) {
            return serialized.get(); // Woo!
        }

        // TODO Allow unknown objects as literals?
        return new Literal(value); // Otherwise, just make it a plain value
    }

    protected static Optional<Literal> fromSequence(String literal) { // Only Sequencer should use this
        if (literal == null || literal.isEmpty() || literal.equals("null")) { // Null or empty values return an empty parse
            return Optional.of(EMPTY);
        }

        // If there's quotes, it's a string
        if (literal.startsWith("\"") && literal.endsWith("\"")) {
            return Optional.of(Literal.fromObject(StringUtilities.from(StringEscapeUtils.unescapeJava(literal.substring(1, literal.length() - 1))).formatColour()));
        }

        // Literal booleans are only true or false
        if (literal.equals("true")) {
            return Optional.of(TRUE);
        }

        if (literal.equals("false")) {
            return Optional.of(FALSE);
        }

        // Check for keyword
        if (literal.startsWith("~")) {
            Optional<Keyword> keyword = Keyword.fromString(literal.substring(1));
            if (keyword.isPresent()) {
                return Optional.of(keyword.get().getLiteral());
            }
        }

        // All numbers are doubles, just make them all doubles
        Double doubleVal = StringUtilities.from(literal).asDouble();
        if (doubleVal != null) {
            return Optional.of(Literal.fromObject(doubleVal));
        }

        return Optional.empty();
    }

    protected Literal() { // DO NOT USE (unless you are fromObject or fromSequence)
        this(null);
    }

    protected Literal(Object value) { // DO NOT USE (unless you are fromObject or fromSequence)
        this.value = Optional.ofNullable(value);
    }

    /**
     * Gets the literal's value as a plain {@link Optional} object
     *
     * @return the object
     */
    public Optional<?> getValue() {
        return value;
    }

    /**
     * Checks if the value of the literal is empty (null)
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return !getValue().isPresent();
    }

    /**
     * Checks if this value of the literal is a {@link Keyword keyword}
     *
     * @return true if a keyword
     */
    public boolean isKeyword() {
        return getValue().isPresent() && getValue().get() instanceof Keyword;
    }

    /**
     * Checks if this literal is a String type
     *
     * @return true if a string
     */
    public boolean isString() {
        return getValue().isPresent() && getValue().get() instanceof String;
    }

    /**
     * Checks if this literal is a number type
     *
     * @return true if a number
     */
    public boolean isNumber() {
        return getValue().isPresent() && getValue().get() instanceof Double;
    }

    /**
     * Checks if this literal is a boolean type
     *
     * @return true if a boolean
     */
    public boolean isBoolean() {
        return getValue().isPresent() && getValue().get() instanceof Boolean;
    }

    /**
     * Checks if this literal is a store type
     *
     * @return true if a store
     */
    public boolean isStore() {
        return getValue().isPresent() && getValue().get() instanceof LiteralStore;
    }

    /**
     * Checks if this literal is castable to the given type.
     * This is useful preceding a {@link #rawAs(Class) raw cast}
     *
     * @param typeClass the type's class
     * @return true if castable
     */
    public boolean isType(Class<?> typeClass) {
        return getValue().isPresent() && typeClass.isInstance(getValue().get());
    }

    /**
     * <pre>
     * Attempts to get this literal as a {@link Keyword keyword} value
     * This may throw a {@link InvalidTypeException}
     * </pre>
     *
     * @return the keyword
     */
    public Keyword asKeyword() {
        return isKeyword() ? (Keyword) getValue().get() : parseKeyword().asKeyword();
    }

    /**
     * <pre>
     * Gets this literal as a string value.
     * This should always be successful, as strings are limitless.
     * </pre>
     *
     * @return the string
     */
    public String asString() {
        return isString() ? (String) getValue().get() : parseString().asString();
    }

    /**
     * <pre>
     * Attempts to get this literal as a number value (Double)
     * Unlike {@link #asString()}, this is not guaranteed to work, and may throw a {@link NumberFormatException}
     * </pre>
     *
     * @return the number
     */
    public Double asNumber() {
        return isNumber() ? (Double) getValue().get() : parseNumber().asNumber();
    }

    /**
     * <pre>
     * Attempts to get this literal as a boolean value
     * This may throw a {@link FormatException}
     * </pre>
     *
     * @return the boolean
     */
    public Boolean asBoolean() {
        return isBoolean() ? (Boolean) getValue().get() : parseBoolean().asBoolean();
    }

    /**
     * <pre>
     * Attempts to get this literal as a list value
     * In general, this will just create a singleton list, or get the list as is
     * </pre>
     *
     * @return the list
     */
    public LiteralStore asStore() {
        return isStore() ? (LiteralStore) getValue().get() : parseStore().asStore();
    }

    /**
     * A raw cast to the given type
     *
     * @param clazz the class of the type
     * @param <T> the generic type
     * @return the value, or null
     */
    public <T> T rawAs(Class<T> clazz) {
        return (T) getValue().orElse(null);
    }

    /**
     * <pre>
     * Gets the literal at the given index
     * If the literal is a list, it gets the literal at the given index
     * Otherwise, the literal is treated as a string, and retrieves the character at the given index
     *
     * The index SHOULD NOT be corrected for base-1, as it is done in this method
     * </pre>
     *
     * @param indexLiteral the index
     * @return the literal
     */
    public Literal fromIndex(Literal indexLiteral) {
        if (isStore()) {
            return asStore().get(indexLiteral).getValue();
        } else {
            return Literal.fromObject(asString().indexOf(indexLiteral.asNumber().intValue() - 1));
        }
    }

    private Literal parseKeyword() { // This method's only "saving grace" is an empty literal
        if (isEmpty()) {
            return EMPTY;
        }

        throw new InvalidTypeException("Keywords cannot be cast to"); // Throw exception
    }

    private Literal parseString() { // Parses the value (no matter the value) into a literal with a string value
        if (isEmpty()) {
            return EMPTY_STRING; // Empty = empty string (default value)
        }

        if (isNumber()) {
            // Make integers not have the .0
            if (asNumber() % 1 == 0) {
                return fromObject(Integer.toString(asNumber().intValue()));
            } else {
                return fromObject(Double.toString(asNumber()));
            }
        }

        return fromObject(getValue().get().toString());
    }

    private Literal parseNumber() { // Attempts to parse the value into a literal with a number value
        if (isEmpty()) {
            return ZERO; // Empty = zero (default value)
        }

        return fromObject(Double.parseDouble(asString()));
    }

    private Literal parseBoolean() { // Attempts to parse the value into a literal with a boolean value
        if (isEmpty()) {
            return FALSE; // Empty = false (default value)
        }

        switch (asString().trim().toLowerCase()) { // Lower case to ignore cases, trimmed because we're parsing a boolean, whitespace shouldn't matter
            case "true":
            case "yes":
                return TRUE;
            case "false":
            case "no":
                return FALSE;
        }

        throw new FormatException(asString() + " cannot be formatted into a boolean.");
    }

    private Literal parseStore() { // Attempts to parse the value into a literal with a store value
        if (isEmpty()) {
            return LiteralStore.emptyLiteral().get();
        }

        return Literal.fromObject(Collections.singletonList(this)); // Create a singleton
    }

    // Arithmetic

    /**
     * Adds a {@link Literal} to this one, either by string (concatenation) or numerically
     *
     * @param other the other literal
     * @return the sum literal
     */
    public Literal add(Literal other) {
        if (isNumber() && other.isNumber()) {
            return Literal.fromObject(asNumber() + other.asNumber());
        }

        if (isStore()) {
            LiteralStore store = asStore().copy();

            if (other.isStore()) { // If the other is also a store, combine the stores
                store.addAll(other.asStore());
            } else { // Otherwise, just append the other to this store
                if (!other.isEmpty()) {
                    store.add(other);
                }
            }
            return Literal.fromObject(store);
        } else if (other.isString()) {
            return other.add(this); // Reverse this, so we don't need to rewrite code
        }

        return Literal.fromObject(asString() + other.asString()); // Everything can be a string
    }

    /**
     * Subtracts a {@link Literal} from this one numerically
     *
     * @param other the other literal
     * @return the difference literal
     */
    public Literal sub(Literal other) {
        return Literal.fromObject(asNumber() - other.asNumber());
    }

    /**
     * Multiplies a {@link Literal} by this one numerically
     *
     * @param other the other literal
     * @return the product literal
     */
    public Literal mult(Literal other) {
        return Literal.fromObject(asNumber() * other.asNumber());
    }

    /**
     * Divides a {@link Literal} by this one numerically
     *
     * @param other the other literal
     * @return the quotient literal
     */
    public Literal div(Literal other) {
        return Literal.fromObject(asNumber() / other.asNumber());
    }

    /**
     * Raises a {@link Literal} to the power of this one numerically
     *
     * @param other the other literal
     * @return the resultant literal
     */
    public Literal pow(Literal other) {
        return Literal.fromObject(Math.pow(asNumber(), other.asNumber()));
    }

    /**
     * Takes the nth root as per a {@link Literal} of this one numerically
     *
     * @param other the other literal
     * @return the resultant literal
     */
    public Literal root(Literal other) {
        return Literal.fromObject(Math.pow(asNumber(), (1D / other.asNumber())));
    }

    /**
     * Takes the modulus of the other literal, its remainder in division
     *
     * @param other the other literal
     * @return the resultant literal
     */
    public Literal mod(Literal other) {
        return Literal.fromObject(asNumber() % other.asNumber());
    }

    @Override
    public Literal resolve(Context ctx) {
        return this; // Resolves itself
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Literal && ((Literal) obj).getValue().equals(getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
