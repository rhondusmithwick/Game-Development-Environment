package api;

import java.util.Map;

/**
 * Load the specs from some resource (XML, properties file etc).
 *
 * @author Rhondu Smithwick
 */
public interface ITemplateLoader<T> {

    /**
     * Load specs from the file.
     *
     * @param fileName the name of the file
     * @return the specs from the file
     */
    Map<T, Integer> loadSpecs (String fileName);

}
