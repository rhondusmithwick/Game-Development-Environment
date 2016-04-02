package serialization;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class XMLReader<T> {

    public List<T> readFromFile(String fileName) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        List<T> objs = new ArrayList<>();
        try {
            ObjectInputStream in = xstream.createObjectInputStream(new FileInputStream(fileName));
            doRead(in, objs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objs;
    }

    public T readSingleFromFile(String fileName) {
        return readFromFile(fileName).get(0);
    }

    @SuppressWarnings("unchecked")
    private void doRead(ObjectInputStream in, List<T> objs) {
        while (true) {
            try {
                T obj = (T) in.readObject();
                objs.add(obj);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                break;
            }
        }
    }
}
