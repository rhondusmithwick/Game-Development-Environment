package voogasalad.util.facebookutil.login;

import java.util.ResourceBundle;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import voogasalad.util.facebookutil.JavaSocial;


/**
 * Login class for Facebook. Creates a Scribe oauth service and then
 * authenticates the user in the browser.
 * @author Tommy
 *
 */
public class FacebookLogin implements LoginUser {
    private static final String SCOPE = "publish_actions,email";

    private ResourceBundle mySecrets;
    private LoginObject myLoginObject;

    public FacebookLogin () {
        mySecrets = ResourceBundle.getBundle(JavaSocial.RESOURCE_FOLDER + "secret");
        myLoginObject = new LoginObject();
    }

    @Override
    public void authenticate (JavaSocial social) {
        OAuth20Service service = createService(mySecrets.getString("facebookId"),
                                  mySecrets.getString("facebookSecret"));
        myLoginObject.setService(service);
        createToken(social);
    }

    /**
     * Creates facebook service given client id and secret
     * @param clientId
     * @param clientSecret
     * @return
     */
    private OAuth20Service createService (String clientId, String clientSecret) {
        return new ServiceBuilder().apiKey(clientId)
                .apiSecret(clientSecret)
                .callback(mySecrets.getString("callback"))
                .scope(SCOPE)
                .build(FacebookApi.instance());
    }
    
    /**
     * Gets oauth token using a web browser
     * @param social
     */
    private void createToken (JavaSocial social) {
        LoginView view = new LoginView(myLoginObject.getService().getAuthorizationUrl());
        view.attachListener(new FacebookListener(view.getEngine(), social, myLoginObject));
    }

}
