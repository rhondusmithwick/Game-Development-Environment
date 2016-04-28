package voogasalad.util.facebookutil.user.profiles;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import voogasalad.util.facebookutil.login.LoginObject;

/**
 * Describes a profile for any social account. This
 * could also just be the app itself.
 * @author Tommy
 *
 */
public abstract class SocialProfile {
    
    private String myID;
    
    @XStreamOmitField
    private LoginObject myLogin;
    
    public SocialProfile () {
        
    }
    
    public SocialProfile (String userID) {
        myID = userID;
    }
    
    public void login (LoginObject newLogin) {
        myLogin = newLogin;
        myID = newLogin.getUserID();
    }
    
    public String getID () {
        return myID;
    }
    
    public LoginObject getLogin () {
        return myLogin;
    }

}
