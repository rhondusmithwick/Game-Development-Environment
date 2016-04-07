package datamanagement;

import api.IDataReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SerializableReader<T> implements IDataReader<T> {

    @Override
    public List<T> readFromFile(String fileName) {
        List<T> objects = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            addAll(in, objects);
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return objects;
    }

    @Override
    public List<T> readFromString(String stringInput) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private void addAll(ObjectInputStream in, List<T> objects) {
        while (true) {
            try {
                T obj = (T) in.readObject();
                objects.add(obj);
            } catch (ClassNotFoundException | IOException e) {
                break;
            }
        }
    }
}
