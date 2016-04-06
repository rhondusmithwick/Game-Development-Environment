package api;

import java.io.File;

/**
 * The class to write serializables to a file.
 *
 * @author Rhondu Smithwick
 */
public interface IDataWriter<T> {

    /**
     * Write the objects to a file.
     *
     * @param fileName the file to be written to
     * @param objects  the objects to be written
     * @return the File written
     */
    File writeToFile(String fileName, T... objects);

    /**
     * Write the specified objects to a string.
     *
     * @param objects to be written
     * @return a string of the objects in a data format
     */
    @SuppressWarnings("unchecked")
    String writeToString(T... objects);
}
