package api;

import datamanagement.XMLReader;
import datamanagement.XMLWriter;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Interface for serializable objects
 *
 * @author Tom Wu
 */
public interface ISerializable extends Serializable {

    /**
     * Evaluates the File f
     *
     * @param f File to evaluate
     */
    static List<Object> evaluate(File file) {
        return new XMLReader<>().readFromFile(file.getPath());
    }

    /**
     * Evaluates the String s
     *
     * @param s String to evaluate
     */
    static List<Object> evaluateString(String stringToReadFrom) {
        return new XMLReader<>().readFromString(stringToReadFrom);
    }

    /**
     * Serializes this object to a File
     *
     * @return File representing the serialized form of this object
     */
    default File serialize(String fileName) {
        return new XMLWriter<>().writeToFile(fileName, this);
    }

    /**
     * Serializes this object to a String
     *
     * @return String representing the serialized form of this object
     */
    default String serializeToString() {
        return new XMLWriter<>().writeToString(this);
    }
}
