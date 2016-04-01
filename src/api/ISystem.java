package api;

import java.io.File;
import java.io.Serializable;


public interface ISystem extends Serializable {
    File evaluate (File f);

    void update ();

    File serialize ();
}
