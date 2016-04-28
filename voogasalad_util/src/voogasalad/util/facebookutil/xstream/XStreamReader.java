package voogasalad.util.facebookutil.xstream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Helper class to read objects from xstream xml files. This
 * came from our project.
 * @author Tommy
 *
 */
public class XStreamReader {
    
    /**
     * Reads in an XML file object
     */
    public Object getObject (File xmlFile) {
        XStream xstream = new XStream(new DomDriver());
        try {
            String xml = fileToXMLString(xmlFile);
            Object newUser = xstream.fromXML(xml);
            System.out.println("Read " + xmlFile.getName());
            return newUser;
        }
        catch (IOException e) {
            System.out.println("No File Exists");
            return null;
        }
    }

    /**
     * Transforms the file from a file to a string
     * @param file
     * @return
     * @throws IOException
     */
    private String fileToXMLString (File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader buffReader = getBufferedReader(file);
        String line;
        while ((line = buffReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    /**
     * helper to create a buffered reader of the file
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    private BufferedReader getBufferedReader (File file) throws FileNotFoundException {
        FileReader reader = new FileReader(file);
        return new BufferedReader(reader);
    }

}
