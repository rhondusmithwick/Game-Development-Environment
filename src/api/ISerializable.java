package api;

import datamanagement.XMLReader;
import datamanagement.XMLWriter;

import java.io.File;
import java.io.Serializable;

/**
 * Interface for serializable objects.
 * Indicates that an object may be read and written from a file, and allows a few methods to do so.
 *
 * @author Rhondu Smithwick, Tom Wu
 */
public interface ISerializable extends Serializable {

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

    default <T extends ISerializable> T clone(Class<T> objectClass) {
        String clonedString = new XMLWriter<>().writeToString(this);
        Object obj = new XMLReader<>().readSingleFromString(clonedString);
        return objectClass.cast(obj);
    }
    
}
