package api;

import java.io.File;
import java.io.Serializable;


public interface ISerializable extends Serializable {
    default void evaluate (File f) {
        // TODO
    }

    default File serialize () {
        return null; // TODO
    }
}
