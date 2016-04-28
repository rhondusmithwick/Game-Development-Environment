package voogasalad.util.facebookutil;

import java.lang.reflect.InvocationTargetException;
import voogasalad.util.facebookutil.applications.App;
import voogasalad.util.facebookutil.applications.FacebookApp;
import voogasalad.util.facebookutil.applications.LocalApp;
import voogasalad.util.facebookutil.applications.TwitterApp;
import voogasalad.util.facebookutil.login.FacebookLogin;
import voogasalad.util.facebookutil.login.LocalLogin;
import voogasalad.util.facebookutil.login.LoginUser;
import voogasalad.util.facebookutil.login.TwitterLogin;
import voogasalad.util.facebookutil.user.profiles.FacebookProfile;
import voogasalad.util.facebookutil.user.profiles.LocalProfile;
import voogasalad.util.facebookutil.user.profiles.TwitterProfile;
import voogasalad.util.facebookutil.user.profiles.UserProfile;

/**
 * Enum to deal with the various social types that may be supported by our
 * utility. For now, this is just kept as an enum but will probably be
 * refactored into property files given enough time.
 * @author Tommy
 *
 */
public enum SocialType {
    FACEBOOK (FacebookLogin.class, FacebookProfile.class, FacebookApp.class),
    TWITTER (TwitterLogin.class, TwitterProfile.class, TwitterApp.class),
    LOCAL (LocalLogin.class, LocalProfile.class, LocalApp.class);
    
    private Class<? extends LoginUser> myLogin;
    private Class<? extends UserProfile> myProfile;
    private Class<? extends App> myApp;
    
    SocialType (Class<? extends LoginUser> loginClass, Class<? extends UserProfile> profileClass,
                Class<? extends App> appClass) {
        myLogin = loginClass;
        myProfile = profileClass;
        myApp = appClass;
    }
    
    /**
     * Returns login class
     * @return
     */
    public LoginUser getLogin () {
        try {
            return myLogin.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
    
    /**
     * Returns profile class
     * @param userID
     * @return
     */
    public UserProfile getProfile (String userID) {
        try {
            return myProfile.getDeclaredConstructor(String.class).newInstance(userID);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }
    
    /**
     * Returns app class
     * @return
     */
    public App getApp () {
        try {
            return myApp.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

}
