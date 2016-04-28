package voogasalad.util.facebookutil;

import java.util.ArrayList;
import java.util.List;
import voogasalad.util.facebookutil.applications.AppMap;
import voogasalad.util.facebookutil.login.LoginObject;
import voogasalad.util.facebookutil.login.LoginUser;
import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.user.Email;
import voogasalad.util.facebookutil.user.IUser;
import voogasalad.util.facebookutil.user.User;
import voogasalad.util.facebookutil.xstream.HighScoreReader;
import voogasalad.util.facebookutil.xstream.HighScoreWriter;
import voogasalad.util.facebookutil.xstream.UserReader;
import voogasalad.util.facebookutil.xstream.UserWriter;

/**
 * Class that implements the main java social interface.
 * Loads previous users into a list of current users and allows people
 * to log in, record scores, and post.
 * @author Tommy
 *
 */
public class JavaSocial implements IJavaSocial {
    
    public static final String RESOURCE_FOLDER = "voogasalad/util/facebookutil/";
    
    private List<IUser> myUsers;
    private HighScoreBoard myHighScores;
    private IUser activeUser;
    private AppMap myApps;
    
    public JavaSocial () {
        myUsers = loadUsers();
        myHighScores = new HighScoreReader().getBoard();
        myApps = new AppMap();
        myApps.loginApps();
    }

    /**
     * Loads users to list from the XML reader
     * @return
     */
    private List<IUser> loadUsers () {
        UserReader reader = new UserReader ();
        return reader.getUsers();
    }

    @Override
    public List<IUser> getUsers () {
        return new ArrayList<>(myUsers);
    }

    @Override
    public IUser getUserByEmail (Email email) {
        for (IUser user: myUsers) {
            if (user.getUserEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public HighScoreBoard getHighScoreBoard () {
        return myHighScores;
    }

    @Override
    public void loginUser (SocialType type, LoginObject login) {
        IUser user = getUserByEmail(login.getEmail());
        if (user == null) {
            user = createNewUser(login.getEmail());
        } else {
            System.out.println("User exists");
        }
        user.login(type, login);
        activeUser = user;
    }
    
    @Override
    public void loginUser (SocialType type) {
        LoginUser login = type.getLogin();
        login.authenticate(this);
    }

    @Override
    public IUser createNewUser (Email email) {
        if (getUserByEmail(email) == null) {
            System.out.println("Creating new User");
            IUser newUser = new User(email);
            myUsers.add(newUser);
            return newUser;
        }
        System.out.println("User already exists");
        return getUserByEmail(email);
    }

    /**
     * Gets the current user of the social environment
     * @return
     */
    public IUser getActiveUser () {
        return activeUser;
    }
    
    /**
     * Gets the applications associated with the social app
     * @return
     */
    public AppMap getApplications () {
        return myApps;
    }

    @Override
    public void saveState () {
        UserWriter writer = new UserWriter();
        writer.write(getUsers());
        HighScoreWriter scoreWriter = new HighScoreWriter();
        scoreWriter.write(myHighScores);
    }


}
