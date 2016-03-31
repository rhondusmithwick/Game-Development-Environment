package entitytesting;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class XMLWriter {
    private final String myFile;
    private XMLEncoder encoder = null;

    public XMLWriter(String myFile) {
        this.myFile = myFile;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(myFile)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: While Opening the File " + myFile);
        }
    }

    public void  writeAll(Serializable... serializables) {
        for (Serializable ser: serializables) {
            encoder.writeObject(ser);
        }
        encoder.close();
    }

}
