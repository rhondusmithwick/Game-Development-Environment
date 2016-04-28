package voogasalad.util.facebookutil.user;

import voogasalad.util.facebookutil.SocialType;
import voogasalad.util.facebookutil.login.LoginObject;
import voogasalad.util.facebookutil.scores.UserScoreBoard;

/**
 * This interface manages the actions associated with a user 
 *
 */
public interface IUser {
    
    /**
     * Get the Email from a user. All users have to be
     * associated with an email
     * @return
     */
    public Email getUserEmail ();
    
    /**
     * Gets the User's own score board
     * @return
     */
    public UserScoreBoard getScoreBoard ();
    
    /**
     * Logs the user out
     */
    public void logout ();
    
    /**
     * Gets the profiles that this user has
     * @return
     */
    public SocialMap getProfiles ();
    
    /**
     * Logs in the user based on a social type
     * @param type
     * @param login
     */
    public void login (SocialType type, LoginObject login);

}
