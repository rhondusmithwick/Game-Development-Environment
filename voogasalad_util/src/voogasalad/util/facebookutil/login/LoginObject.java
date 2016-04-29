package voogasalad.util.facebookutil.login;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import voogasalad.util.facebookutil.user.Email;

/**
 * Structure to hold the various items retrieved during a login
 * like the token, service, email, and userID of the user
 * @author Tommy
 *
 */
public class LoginObject {
    
    private OAuth2AccessToken myToken;
    private OAuth20Service myService;
    private Email myEmail;
    private String myUserID;
    
    public OAuth2AccessToken getToken (){
        return myToken;
    }
    
    public OAuth20Service getService () {
        return myService;
    }
    
    public Email getEmail () {
        return myEmail;
    }
    
    public String getUserID () {
        return myUserID;
    }
    
    public void setToken (OAuth2AccessToken token){
        myToken = token;
    }
    
    public void setService (OAuth20Service service) {
        myService = service;
    }
    
    public void setEmail (Email email) {
        myEmail = email;
    }
    
    public void setUserID (String userID) {
        myUserID = userID;
    }

}
