package voogasalad.util.facebookutil.actions;

import voogasalad.util.facebookutil.login.LoginObject;

/**
 * Gets the profile for a user. Helpful for logging
 * in a user for the first time
 * @author Tommy
 *
 */
public interface GetProfile extends SocialAction {
    
    public void createGet (LoginObject login);
    
    public String getEmail ();
    
    public String getUserID ();

}
