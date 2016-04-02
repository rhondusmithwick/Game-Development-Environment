package api;

import java.util.List;

/**
 * Created by rhondusmithwick on 3/31/16.
 * <p>
 * Class to wread in serializables of type T.
 *
 * @author Rhondu Smithwick
 */
public interface ISerializableReader<T> {

    /**
     * Read in the serializables from a file.
     *
     * @return list of serializables of type T.
     */
    List<T> read();
}
