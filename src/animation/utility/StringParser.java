package animation.utility;

import java.util.Arrays;
import java.util.List;
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
     * List in form of [x, y, z, ., ., .]
     * </p>
     *
     * @param string the string representation
     * @return the list of strings represented by this string
     */
    public static List<String> convertStringFromList(String string) {
        String noBrackets = string.replace("[", "");
        noBrackets = noBrackets.replace("]", "");
        return Arrays.asList(noBrackets.split("\\s*,\\s*"));
    }

    /**
     * Convert a string representing a list into a list of doubles.
     *
     * @param string the string representation
     * @return the list of doubles represented by this string
     * @see #convertStringFromList(String)
     */
    public static List<Double> convertStringToDoubleList(String string) {
        return convertStringFromList(string).stream().map(Double::parseDouble).collect(Collectors.toList());
    }

}
