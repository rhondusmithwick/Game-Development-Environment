package api;

import java.io.Serializable;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * The class to write serializables to a file.
 * @author Rhondu Smithwick
 */
public interface ISerializableWriter {

    /**
     * Writes all these serializables to a file.
     * @param serializables the serializables to be written
     */
    void write(Serializable... serializables);
}
