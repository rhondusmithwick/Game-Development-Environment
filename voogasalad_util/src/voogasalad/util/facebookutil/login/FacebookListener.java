package voogasalad.util.facebookutil.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.github.scribejava.core.model.OAuth2AccessToken;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import voogasalad.util.facebookutil.JavaSocial;
import voogasalad.util.facebookutil.SocialType;
import voogasalad.util.facebookutil.actions.GetProfile;
import voogasalad.util.facebookutil.actions.facebook.FacebookGetProfile;
import voogasalad.util.facebookutil.user.Email;

/**
 * Change listener for the browser login method for a facebook
 * login. Waits until the browser goes to the correct location
 * and returns a code, then logs in the user. Necessary because
 * facebook only logs in users from the web.
 * @author Tommy
 *
 */
public class FacebookListener implements ChangeListener<State> {
    private static final String CODE_REGEX = "code=([^&]+)";
    
    private WebEngine myEngine;
    private JavaSocial mySocial;
    private LoginObject myLoginObject;

    public FacebookListener (WebEngine engine, JavaSocial social, LoginObject login) {
        myEngine = engine;
        mySocial = social;
        myLoginObject = login;
    }

    /**
     * Method to overwrite to listen to changes in the web browser
     */
    @Override
    public void changed (ObservableValue<? extends State> ov, State oldState, State newState) {
        if (newState == Worker.State.SUCCEEDED) {
            String newURL = myEngine.getLocation();
            checkForLogin(newURL);
            
        }
    }

    /**
     * Checks if url matches the code regex, then logs in if successful
     * @param newURL
     */
    private void checkForLogin (String newURL) {
        Matcher m = Pattern.compile(CODE_REGEX).matcher(newURL);
        if (m.find()) {
            OAuth2AccessToken token = myLoginObject.getService().getAccessToken(m.group(1));
            myLoginObject.setToken(token);
            findProfile();
            login ();
        }
    }

    /**
     * Logs in user to facebook
     */
    private void login () {
        mySocial.loginUser(SocialType.FACEBOOK, myLoginObject);
    }
    
    /**
     * Finds the profile with another request
     */
    private void findProfile () {
        GetProfile getter = new FacebookGetProfile ();
        getter.createGet(myLoginObject);
        myLoginObject.setEmail(new Email(getter.getEmail()));
        myLoginObject.setUserID(getter.getUserID());
    }
};
