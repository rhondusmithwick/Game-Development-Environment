package datamanagement;

import api.IDataReader;
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
public class XMLReader<T> implements IDataReader<T> {

    @Override
    public List<T> readFromString(String stringInput) {
        return null;
    }

    public List<T> readFromFile(String fileName) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        List<T> objects = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = xstream.createObjectInputStream(fileIn);
            doRead(in, objects);
            in.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }

    public T readSingleFromFile(String fileName) {
        return readFromFile(fileName).get(0);
    }

    @SuppressWarnings("unchecked")
    private void doRead(ObjectInputStream in, List<T> objects) {
        while (true) {
            try {
                T obj = (T) in.readObject();
                objects.add(obj);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                break;
            }
        }
    }
}
