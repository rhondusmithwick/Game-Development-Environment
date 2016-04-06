package datamanagement;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SerializableWriter<T> implements IDataWriter<T> {

    @Override
    @SafeVarargs
    public final File writeToFile(String fileName, T... objects) {
        File file = new File(fileName);
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            doWrite(fileOut, objects);
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return file;
    }

    @Override
    @SafeVarargs
    public final String writeToString(T... objects) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            doWrite(bos, objects);
            bos.close();
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void doWrite(OutputStream writeTo, Object... objects) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(writeTo);
        for (Object object : objects) {
            out.writeObject(object);
        }
        out.close();
    }
}
