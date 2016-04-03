package datamanagement;


import api.IDataWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class XMLWriter<T> implements IDataWriter<T> {

    @SafeVarargs
    @Override
    public final File writeToFile(String fileName, T... objs) {
        File file = new File(fileName);
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = xstream.createObjectOutputStream(fileOut);
            for (T obj : objs) {
                out.writeObject(obj);
            }
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @SafeVarargs
    @Override
    public final String writeToString(T... objects) {
        return null;
    }
}
