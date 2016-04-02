package serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SerializableWriter {
    private final String myFile;

    public SerializableWriter(String myFile) {
        this.myFile = myFile;
    }

    public void write(Serializable... serializables) {
        try {
            FileOutputStream fileOut = new FileOutputStream(myFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Serializable ser : serializables) {
                out.writeObject(ser);
            }
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
