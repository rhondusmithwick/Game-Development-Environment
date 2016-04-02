package api;

import java.io.File;
import java.io.Serializable;


public interface ISerializable extends Serializable {
    void evaluate (File f);

    File serialize ();
}
