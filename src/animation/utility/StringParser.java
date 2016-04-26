package animation.utility;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A class to parse strings.
 *
 * @author Melissa Zhang, Rhondu Smithwick
 */
public final class StringParser {

    private StringParser() {
    }

    /**
     * Convert a string representation of a list into a list of strings.
     * <p>
     * String in form of [x, y, z, ., ., .]
     * </p>
     *
     * @param string the string representation
     * @return the list of strings represented by this string
     */
    public static List<String> converStringToStringList(String string) {
        String noBrackets = string.replace("[", "");
        noBrackets = noBrackets.replace("]", "");
        return Arrays.asList(noBrackets.split("\\s*,\\s*"));
    }

    /**
     * Converts a string to a list of generics using the provided function.
     * <p>
     * String must be in form of [x, y, z, ., ., .]
     * </p>
     *
     * @param string    the string to convert
     * @param converter the converter function
     * @param <T>       the class to convert to
     * @return the list represented by this string
     */
    public static <T> List<T> convertStringToList(String string, Function<String, T> converter) {
        return converStringToStringList(string).stream().map(converter).collect(Collectors.toList());
    }

    /**
     * Convert a string representing a list into a list of doubles.
     *
     * @param string the string representation
     * @return the list of doubles represented by this string
     * @see #convertStringToList(String, Function)
     */
    public static List<Double> convertStringToDoubleList(String string) {
        return convertStringToList(string, Double::parseDouble);
    }

}
