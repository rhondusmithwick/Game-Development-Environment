package serialization;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class XMLWriter<T> {

    public void writeToFile(String fileName, T... objs) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        try {
            ObjectOutputStream out = xstream.createObjectOutputStream(new FileOutputStream(fileName));
            for (T obj : objs) {
                out.writeObject(obj);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
