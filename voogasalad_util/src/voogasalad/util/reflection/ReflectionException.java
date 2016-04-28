package voogasalad.util.reflection;


/**
 * A general exception that represents all possible Java Reflection exceptions.
 *
 * @author Robert C. Duvall
 */
public final class ReflectionException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;

    /**
     * Create an exception based on an issue in our code.
     */
    public ReflectionException (String message, Object ... args) {
        super(format(message, args));
    }

    /**
     * Create an exception based on a caught exception.
     */
    public ReflectionException (Throwable exception) {
        super(exception);
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public ReflectionException (Throwable cause, String message, Object ... args) {
        super(format(message, args), cause);
    }

    // remove duplicate code, also placeholder for future improvements (like logging)
    private static String format (String message, Object ... args) {
        return String.format(message, args);
    }
}
