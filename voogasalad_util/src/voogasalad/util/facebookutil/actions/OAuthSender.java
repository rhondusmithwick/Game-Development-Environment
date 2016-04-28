package voogasalad.util.facebookutil.actions;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import voogasalad.util.facebookutil.login.LoginObject;

/**
 * Helper class to send requests using login objects and requests
 * @author Tommy
 *
 */
public class OAuthSender {
    
    public static Response sendRequest(LoginObject login, OAuthRequest request) {
        login.getService().signRequest(login.getToken(), request);
        return request.send();
    }

}
