package model.entity;

import java.util.Map;

/**
 * Created by rhondusmithwick on 4/6/16.
 *
 * @author Rhondu Smithwick
 */
public interface SpecLoader<T> {

    Map<T, Integer> loadSpecs(String fileName);
}
