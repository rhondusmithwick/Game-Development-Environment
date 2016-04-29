package voogasalad.util.facebookutil.xstream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import voogasalad.util.facebookutil.user.profiles.SocialProfile;

/**
 * Helper class to write types to xstream files. This was pulled from our
 * own project.
 * @author Tommy
 *
 * @param <Type>
 */
public class XStreamWriter<Type> {
    
    private static final String WRITE_ERROR = "ERROR WRITING TO XSTREAM";
    
    /**
     * Writes a single user to an xml file
     * @param user
     */
    public void writeToFile (Type toWrite, String fileName, String folderPath) {
        File dir = new File(folderPath);
        File file = new File(dir, fileName);
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write(getXML(toWrite));
            fw.close();
            System.out.println("Wrote " + file.getName());
        }
        catch (IOException e) {
            System.out.println(WRITE_ERROR);
        }
    }

    /**
     * transforms user into XML string
     * @param user
     * @return
     */
    private String getXML (Type toWrite) {
        XStream xstream = new XStream(new DomDriver());
        xstream.ignoreUnknownElements();
        xstream.processAnnotations(SocialProfile.class);
        return xstream.toXML(toWrite);
    }

}
