package entitytesting;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class XMLReader {
    private XMLDecoder decoder = null;

    public XMLReader(String myFile) {
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(myFile)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: While Opening the File " + myFile);
        }
    }

    public <T> List<T> readAll(Class<T> theClass) {
        List<T> objects = new ArrayList<>();
        while (true) {
            try {
                objects.add(read(theClass));
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        decoder.close();
        return objects;
    }

    public <T> T read(Class<T> theClass) {
        Object obj = decoder.readObject();
        return theClass.cast(obj);
    }
}
