package api;

import java.io.File;
import java.io.Serializable;


public interface ISystem extends Serializable {
    void update ();

    File serialize ();
}
