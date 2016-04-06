package model.entity;

import java.util.Map;

/**
 * Created by rhondusmithwick on 4/6/16.
 * <p>
 * Load the specs from some resource (XML, properties file etc).
 *
 * @author Rhondu Smithwick
 */
public interface SpecLoader<T> {

    /**
     * Load specs from the file.
     *
     * @param fileName the name of the file
     * @return the specs from the file
     */
    Map<T, Integer> loadSpecs(String fileName);
}
