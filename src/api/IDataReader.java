package api;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class to read in serializables of type T.
 *
 * @author Rhondu Smithwick
 */
public interface IDataReader<T> {

    /**
     * Read in all the data from a file.
     *
     * @return list of objects of type T read from file
     */
    default List<T> readFromFile(String fileName) {
        File file = new File(fileName);
        String readFromFile = null;
        try {
            readFromFile = Files.toString(file, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readFromString(readFromFile);
    }

    /**
     * Return single object of type T read from file. Will be first object read (or only one
     * if only one
     *
     * @param fileName file to be read
     * @return single object of type T read from file
     */
    default T readSingleFromFile(String fileName) {
        return readFromFile(fileName).get(0);
    }

    /**
     * Returns list of objects of type T read from stringInput.
     *
     * @param stringInput input to read objects from
     * @return a list of objects of type T read from stringInput
     */
    List<T> readFromString(String stringInput);

    /**
     * Return single object of type T read from string. Will be first object read (or only one
     * if only one
     *
     * @param stringInput string to be read
     * @return single object of type T read from string
     */
    default T readSingleFromString(String stringInput) {
        return readFromString(stringInput).get(0);
    }

}
