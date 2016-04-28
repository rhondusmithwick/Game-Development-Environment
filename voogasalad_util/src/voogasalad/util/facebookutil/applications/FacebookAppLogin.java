package voogasalad.util.facebookutil.applications;

import java.util.ResourceBundle;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import voogasalad.util.facebookutil.JavaSocial;
import voogasalad.util.facebookutil.ParseHelper;
import voogasalad.util.facebookutil.login.LoginObject;


/**
 * Handles logging in a facebook app. This request is a little different
 * from the others, since it launches the browser view.
 * @author Tommy
 *
 */
public class FacebookAppLogin {
    private static final String APP_URL = "https://graph.facebook.com/oauth/access_token?" +
                                          "&grant_type=client_credentials&client_secret=%s&client_id=%s";
    private static final String TOKEN_REGEX = "access_token=([^&]+)";

    private LoginObject myLoginObject;
    private ResourceBundle secrets;

    public FacebookAppLogin () {
        secrets = ResourceBundle.getBundle(JavaSocial.RESOURCE_FOLDER + "secret");
        getLogin();
    }

    private void getLogin () {
        final OAuth20Service service = createService(secrets);
        final OAuthRequest request = createRequest(service, secrets);
        Response response = request.send();
        processResponse(response, secrets, service);
    }

    private void processResponse (Response response,
                                  ResourceBundle secrets,
                                  OAuth20Service service) {
        LoginObject login = new LoginObject();
        login.setUserID(secrets.getString("facebookId"));
        login.setToken(new OAuth2AccessToken(ParseHelper.getFirstGroup(TOKEN_REGEX,
                                                                       response.getBody())));
        login.setService(service);
        myLoginObject = login;
    }

    private OAuthRequest createRequest (OAuth20Service service, ResourceBundle secrets) {
        String url = String.format(APP_URL, secrets.getString("facebookSecret"),
                                   secrets.getString("facebookId"));
        return new OAuthRequest(Verb.GET, url, service);
    }

    private OAuth20Service createService (ResourceBundle secrets) {
        return new ServiceBuilder()
                .apiKey(secrets.getString("facebookId"))
                .apiSecret(secrets.getString("facebookSecret"))
                .callback(secrets.getString("callback"))
                .grantType("client_credentials")
                .build(FacebookApi.instance());
    }

    public LoginObject getLoginObject () {
        return myLoginObject;
    }

}
