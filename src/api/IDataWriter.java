package api;

import java.io.File;

/**
 * Created by rhondusmithwick on 3/31/16.
 * <p>
 * The class to write serializables to a file.
 *
 * @author Rhondu Smithwick
 */
public interface IDataWriter<T> {

    /**
     * Writes all these serializables to a file.
     *
     * @param serializables the serializables to be written
     */
    File writeToFile(String fileName, T... objects);

    /**
     * Write the specified objects to a string.
     *
     * @param objects to be written
     * @return
     */
    String writeToString(T... objects);
}
