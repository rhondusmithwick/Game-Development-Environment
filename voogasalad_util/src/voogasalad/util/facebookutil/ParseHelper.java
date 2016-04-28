package voogasalad.util.facebookutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for parsing HTTP responses. There's no real good way
 * to deal with JSON in Java and instead of reinventing the wheel for use
 * in a small amount of situations, it's better to just have some helper methods
 * here to get items from a response
 * @author Tommy
 *
 */
public class ParseHelper {
    
    private static final String JSON_REGEX = "\"%s\":\"([^&]+?)\"";
    
    /**
     * Gets the first group of a regex pattern (assumes a group is used)
     * This is helpful when the response might be "token:2989273489723"
     * @param pattern
     * @param body
     * @return match or null if no match
     */
    public static String getFirstGroup (String pattern, String body) {
        Matcher m = Pattern.compile(pattern).matcher(body);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
    
    /**
     * Parses a JSON body for a specific id. Simply formats it with a regex
     * and looks for the regex group.
     * @param id
     * @param body
     * @return match or null if no match
     */
    public static String JSONParse (String id, String body) {
        String regex = String.format(JSON_REGEX, id);
        return getFirstGroup(regex, body);
    }

}
