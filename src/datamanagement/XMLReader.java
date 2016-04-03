package datamanagement;

import api.IDataReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class XMLReader<T> implements IDataReader<T> {

    private final List<T> objects = new ArrayList<>();

    private final XStream xstream = new XStream(new StaxDriver());

    public XMLReader() {
        xstream.autodetectAnnotations(true);
    }

    @Override
    public List<T> readFromString(String stringInput) {
        Reader reader = new StringReader(stringInput);
        doRead(reader);
        return objects;
    }

    @Override
    public List<T> readFromFile(String fileName) {
        try {
            Reader reader = new FileReader(fileName);
            doRead(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }

    public T readSingleFromFile(String fileName) {
        return readFromFile(fileName).get(0);
    }

    @SuppressWarnings("unchecked")
    private void doRead(Reader reader) {
        ObjectInputStream in = null;
        try {
            in = xstream.createObjectInputStream(reader);
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
