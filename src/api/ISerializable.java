package api;

import com.google.common.base.Preconditions;
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

    /**
     * Clones this object to the provided class. Must be the same class.
     *
     * @param objectClass the class to clone to (must be same class)
     * @param <T>         the type
     * @return a cloned object
     * @throws IllegalArgumentException if objectClass snot same class
     */
    default <T extends ISerializable> T clone(Class<T> objectClass) {
        boolean sameClass = getClass().isAssignableFrom(objectClass);
        Preconditions.checkArgument(sameClass, "Not the same class so cannot clone.");
        String clonedString = serializeToString();
        Object obj = new XMLReader<T>().readSingleFromString(clonedString);
        return objectClass.cast(obj);
    }

}
