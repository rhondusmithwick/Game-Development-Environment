package api;

import java.io.File;


public interface ISystem {
    File evaluate (File f);

    void update ();

    File serialize ();
}
