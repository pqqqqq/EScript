package com.pqqqqq.escript.lang.data;

import com.pqqqqq.escript.lang.data.container.expression.ArithmeticContainer;
import com.pqqqqq.escript.lang.exception.FormatException;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.util.string.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Optional;

/**
 * Created by Kevin on 2016-09-02.
 *
 * An immutable literal type - a type that does not need to be resolved
 */
public class Literal implements Datum {
    private final Optional<Object> value;

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
    public static final Literal ZERO = new Literal(0);

    /**
     * A literal with the number 1
     */
    public static final Literal ONE = new Literal(1);

    /**
     * <pre>
     * Creates a new literal that represents the given object
     * The main purpose of this method's body is to reduce instances of Literals by using common ones
     * This method will make use of these public, final literals:
     *
     *      EMPTY
     *      EMPTY_STRING
     *      TRUE
     *      FALSE
     *      ONE
     *      TWO</pre>
     *
     * @param value the value
     * @return the created (or referenced) literal instance
     */
    public static Literal fromObject(Object value) {
        if (value == null) {
            return EMPTY;
        }

        if (value instanceof String) {
            String string = (String) value;

            if (string.isEmpty()) {
                return EMPTY_STRING;
            } else {
                return new Literal(StringUtils.from(string).formatColour());
            }
        }

        if (value instanceof Boolean) {
            return (Boolean) value ? TRUE : FALSE;
        }

        if (value instanceof Integer || value instanceof Long || value instanceof Float) {
            double number = Double.parseDouble(value.toString());

            if (number == 0D) {
                return ZERO;
            } else if (number == 1D) {
                return ONE;
            } else {
                return new Literal(number);
            }
        }

        return new Literal(value);
    }

    protected static Optional<Literal> fromSequence(String literal) { // Only Sequencer should use this
        if (literal == null || literal.isEmpty() || literal.equals("null")) { // Null or empty values return an empty parse
            return Optional.of(EMPTY);
        }

        // TODO unquoted strings?!
        // If there's quotes, it's a string
        if (literal.startsWith("\"") && literal.endsWith("\"")) {
            return Optional.of(Literal.fromObject(StringUtils.from(StringEscapeUtils.unescapeJava(literal.substring(1, literal.length() - 1))).formatColour()));
        }

        // Literal booleans are only true or false
        if (literal.equals("true")) {
            return Optional.of(TRUE);
        }

        if (literal.equals("false")) {
            return Optional.of(FALSE);
        }

        // All numbers are doubles, just make them all doubles
        Double doubleVal = StringUtils.from(literal).asDouble();
        if (doubleVal != null) {
            return Optional.of(Literal.fromObject(doubleVal));
        }

        return Optional.empty();
    }

    private Literal() { // DO NOT USE (unless you are fromObject or fromSequence)
        this(null);
    }

    private Literal(Object value) { // DO NOT USE (unless you are fromObject or fromSequence)
        this.value = Optional.ofNullable(value);
    }

    /**
     * Gets the literal's value as a plain {@link Optional} object
     *
     * @return the object
     */
    public Optional<Object> getValue() {
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
     * This will not error, however anything other than "true" and "yes" will be false
     * </pre>
     *
     * @return the boolean
     */
    public Boolean asBoolean() {
        return isBoolean() ? (Boolean) getValue().get() : parseBoolean().asBoolean();
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

    /**
     * Performs the given arithmetic on the two literals
     *
     * @param other    the other literal
     * @param operator the operation to perform
     * @return the resultant literal
     */
    public Literal arithmetic(Literal other, ArithmeticContainer.ArithmeticOperator operator) {
        switch (operator) {
            case ADDITION:
                return add(other);
            case SUBTRACTION:
                return sub(other);
            case MULTIPLICATION:
                return mult(other);
            case DIVISION:
                return div(other);
            case EXPONENTIAL:
                return pow(other);
            case ROOT:
                return root(other);
            case MODULUS:
                return mod(other);
            default:
                throw new IllegalStateException("Unknown arithmetic operator: " + operator);
        }
    }

    @Override
    public Literal resolve(Context ctx) {
        return this; // Resolves itself
    }

    @Override
    public String toString() {
        return asString();
    }
}
