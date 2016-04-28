package voogasalad.util.facebookutil.xstream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import voogasalad.util.facebookutil.JavaSocial;
import voogasalad.util.facebookutil.user.IUser;

/**
 * XStream reader for users. Likely will be refactored so that
 * we can save the high score board as well
 * @author Tommy
 *
 */
public class UserReader {
    
    private ResourceBundle mySecrets;
    private XStreamReader myReader;
    
    public UserReader () {
        mySecrets = ResourceBundle.getBundle(JavaSocial.RESOURCE_FOLDER + "secret");
        myReader = new XStreamReader();
    }

    /**
     * Gets the list of users from files
     * @return
     */
    public List<IUser> getUsers () {
        List<IUser> users = new ArrayList<IUser>();
        File dir = new File(mySecrets.getString("userfolder"));
        File[] list = dir.listFiles();
        for (File f: list) {
            addUser(f, users);
        }
        return users;
    }

    private void addUser (File f, List<IUser> users) {
        IUser user = (IUser) myReader.getObject(f);
        users.add(user);
    }
    


}
