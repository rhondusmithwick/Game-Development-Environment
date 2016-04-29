package voogasalad.util.facebookutil.xstream;

import java.util.List;
import java.util.ResourceBundle;
import voogasalad.util.facebookutil.JavaSocial;
import voogasalad.util.facebookutil.user.Email;
import voogasalad.util.facebookutil.user.IUser;

/**
 * XStream writer for users. Writes a list of users all
 * to files in the same directory
 * @author Tommy
 *
 */
public class UserWriter extends XStreamWriter<IUser>{
    
    private static final String FILE_FORMAT = "%s%s.xml";

    private ResourceBundle mySecrets;
    private XStreamWriter<IUser> myWriter;
    
    public UserWriter () {
        mySecrets = ResourceBundle.getBundle(JavaSocial.RESOURCE_FOLDER + "secret");
        myWriter = new XStreamWriter<>();
    }
    
    /**
     * Writes each user to a file
     * @param users
     */
    public void write (List<IUser> users) {
        String dirPath = mySecrets.getString("userfolder");
        users.stream()
             .forEach(u -> {
                 myWriter.writeToFile(u, createFileName(u), dirPath);
             });
    }

    private String createFileName (IUser user) {
        Email email = user.getUserEmail();
        return String.format(FILE_FORMAT, email.getName(), email.getDomain());
    }

}
