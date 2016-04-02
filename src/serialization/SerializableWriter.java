package serialization;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SerializableWriter {


    public File writeToFile(String fileName, Serializable... serializables) {
        try {
            File file = new File(fileName);
            FileOutputStream fileOut = new FileOutputStream(file);
            doWrite(fileOut, serializables);
            fileOut.close();
            return file;
        } catch (IOException i) {
            i.printStackTrace();
        }
        return null;
    }

    public String writeToString(Serializable... serializables) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            doWrite(bos, serializables);
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doWrite(OutputStream writeTo, Serializable... serializables) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(writeTo);
        for (Serializable ser : serializables) {
            out.writeObject(ser);
        }
        out.close();
    }
}
