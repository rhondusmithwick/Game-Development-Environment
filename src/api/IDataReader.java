package api;

import java.util.List;

import static utility.ReadFile.readFile;

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
    default List<T> readFromFile (String fileName) {
        String readFromFile = readFile(fileName);
        return readFromString(readFromFile);
    }

    /**
     * Return single object of type T read from file. Will be first object read (or only one
     * if only one
     *
     * @param fileName file to be read
     * @return single object of type T read from file
     */
    default T readSingleFromFile (String fileName) {
        return readFromFile(fileName).get(0);
    }

    /**
     * Returns list of objects of type T read from stringInput.
     *
     * @param stringInput input to read objects from
     * @return a list of objects of type T read from stringInput
     */
    List<T> readFromString (String stringInput);

    /**
     * Return single object of type T read from string. Will be first object read (or only one
     * if only one
     *
     * @param stringInput string to be read
     * @return single object of type T read from string
     */
    default T readSingleFromString (String stringInput) {
        return readFromString(stringInput).get(0);
    }

}
