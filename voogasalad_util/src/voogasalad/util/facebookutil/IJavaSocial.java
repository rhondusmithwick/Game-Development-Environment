package voogasalad.util.facebookutil;

import java.util.List;
import voogasalad.util.facebookutil.login.LoginObject;
import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.user.Email;
import voogasalad.util.facebookutil.user.IUser;

/**
 * Main interface for the social environment. This interface describes
 * how users can login through a social type, get various users by their email
 * and get the current high score board
 * @author Tommy
 *
 */
public interface IJavaSocial {
    
    /**
     * Get the current list of users. List can be filtered
     * by the user
     * @return
     */
    public List<IUser> getUsers ();
    
    /**
     * Get user by his/her email address. Returns
     * null if no user
     * @param email
     * @return
     */
    public IUser getUserByEmail (Email email);
    
    /**
     * Getter for the social environment's high score
     * board
     * @return
     */
    public HighScoreBoard getHighScoreBoard ();
    
    /**
     * Log in a user using a log in object for a given social type.
     * This allows the app to associate a social app to a user
     * @param type
     * @param login
     */
    public void loginUser (SocialType type, LoginObject login);
    
    /**
     * Log in a user with a given social type. This will call to 
     * authorize the user.
     * @param type
     */
    public void loginUser(SocialType type);
    
    /**
     * Creates a new user by email
     * @param email
     * @return
     */
    public IUser createNewUser (Email email);
    
    /**
     * Saves all users and high scores to xml files.
     */
    public void saveState ();

}
