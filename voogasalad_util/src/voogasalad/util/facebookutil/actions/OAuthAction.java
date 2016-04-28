package voogasalad.util.facebookutil.actions;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import voogasalad.util.facebookutil.login.LoginObject;

public abstract class OAuthAction implements SocialAction {
    
    private static final String ERROR_STRING = "Please create request first";
    
    private OAuthRequest myRequest;
    
    @Override
    public void send (LoginObject login) {
        if (getRequest() == null) {
            System.out.println(ERROR_STRING);
            return;
        }
        Response response = OAuthSender.sendRequest(login, getRequest());
        System.out.println(response.getBody());
    }
    
    public OAuthRequest getRequest () {
        return myRequest;
    }
    
    public void setRequest (OAuthRequest request) {
        myRequest = request;
    }
}
