package datamanagement;

import api.IDataReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The XML implementation of a DataReader.
 *
 * @author Rhondu Smithwick
 */
public class XMLReader<T> implements IDataReader<T> {

    private final List<T> objects = new ArrayList<>();

    private final XStream xstream = new XStream(new StaxDriver());

    public XMLReader () {
        xstream.autodetectAnnotations(true);
    }

    @Override
    public List<T> readFromString (String stringInput) {
        Reader reader = new StringReader(stringInput);
        doRead(reader);
        return objects;
    }

    private void doRead (Reader reader) {
        try {
            ObjectInputStream in = xstream.createObjectInputStream(reader);
            continueReading(in);
            reader.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    private void continueReading (ObjectInputStream in) throws ClassNotFoundException {
        while (true) {
            try {
                T obj = (T) in.readObject();
                objects.add(obj);
            } catch (IOException e) {
                break;
            } catch (ClassCastException c) {
                throw new ClassCastException("Not all objects in this file of type T.");
            }
        }
    }
}
