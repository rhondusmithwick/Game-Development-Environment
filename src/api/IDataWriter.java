package api;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The class to write serializables to a file.
 *
 * @author Rhondu Smithwick
 */
public interface IDataWriter<T> {

    /**
     * Write the objects (list) to a file.
     *
     * @param fileName the file to be written to
     * @param objects  the objects to be written
     * @return the File written
     * @see #writeToString(List)
     */
    default File writeToFile(String fileName, List<T> objects) {
        String objectsString = writeToString(objects);
        File file = new File(fileName);
        try {
            Files.write(objectsString,file, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Write the objects (array/varargs) to a file.
     *
     * @param fileName the file to be written to
     * @param objects  the objects to be written
     * @return the File written
     * @see #writeToFile(String, List)
     */
    @SuppressWarnings("unchecked")
    default File writeToFile(String fileName, T... objects) {
        return writeToFile(fileName, Arrays.asList(objects));
    }

    /**
     * Write the specified objects (list) to a string.
     *
     * @param objects to be written
     * @return a string of the objects in a data format
     */
    String writeToString(List<T> objects);

    /**
     * Write the specified objects (array/ varargs) to a string.
     *
     * @param objects to be written
     * @return a string of the objects in a data format
     * @see #writeToString(List)
     */
    @SuppressWarnings("unchecked")
    default String writeToString(T... objects) {
        return writeToString(Arrays.asList(objects));
    }

}
