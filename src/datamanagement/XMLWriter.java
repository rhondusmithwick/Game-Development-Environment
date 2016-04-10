package datamanagement;


import api.IDataWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * The XML implementation of a DataWriter.
 *
 * @author Rhondu Smithwick
 */
public class XMLWriter<T> implements IDataWriter<T> {
    private final XStream xstream = new XStream(new StaxDriver());

    public XMLWriter() {
        xstream.autodetectAnnotations(true);
    }

    @Override
    public final String writeToString(List<T> objects) {
        Writer writer = new StringWriter();
        doWrite(writer, objects);
        return writer.toString();
    }

    private void doWrite(Writer writer, List<T> objects) {
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
