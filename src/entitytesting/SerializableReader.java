package entitytesting;

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
public class SerializableReader<T> {
    private final String myFile;

    public SerializableReader(String myFile) {
        this.myFile = myFile;
    }


    public T readSingle() {
        return read().get(0);
    }

    public List<T> read() {
        List<T> objects = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(myFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            addAll(in, objects);
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return objects;
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
