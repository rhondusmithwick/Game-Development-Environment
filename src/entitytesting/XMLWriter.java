package entitytesting;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class XMLWriter {
    private XMLEncoder encoder = null;

    public XMLWriter(String myFile) {
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(myFile)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: While Opening the File " + myFile);
        }
    }

    public void writeAll(Serializable... serializables) {
        for (Serializable ser : serializables) {
            encoder.writeObject(ser);
        }
        encoder.close();
    }

}
