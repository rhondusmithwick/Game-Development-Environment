package datamanagement;


import api.IDataWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class XMLWriter<T> implements IDataWriter<T> {
    private final XStream xstream = new XStream(new StaxDriver());

    public XMLWriter() {
        xstream.autodetectAnnotations(true);
    }

    @SafeVarargs
    @Override
    public final File writeToFile(String fileName, T... objects) {
        File file = new File(fileName);
        try {
            Writer writer = new FileWriter(file);
            doWrite(writer, objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @SafeVarargs
    @Override
    public final String writeToString(T... objects) {
        Writer writer = new StringWriter();
        doWrite(writer, objects);
        return writer.toString();
    }

    private void doWrite(Writer writer, T... objects) {
        try {
            ObjectOutputStream out = xstream.createObjectOutputStream(writer);
            for (T obj : objects) {
                out.writeObject(obj);
            }
            out.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
