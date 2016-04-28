package voogasalad.util.facebookutil.test;

import java.util.ResourceBundle;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class TestAppLogin {
    
    
    public static void main(String[] args) {
        ResourceBundle secrets = ResourceBundle.getBundle("facebookutil/secret");
        final OAuth20Service service = new ServiceBuilder()
                .apiKey(secrets.getString("clientId"))
                .apiSecret(secrets.getString("clientSecret"))
                .callback("https://duke.edu/")
                .grantType("client_credentials")
                .build(FacebookApi.instance());
        String url = "https://graph.facebook.com/oauth/access_token?";
        url = url + "&client_id" + "=" + secrets.getString("clientId");
        url = url + "&client_secret" + "=" + secrets.getString("clientSecret");
        url = url + "&grant_type" + "=" + "client_credentials";
        final OAuthRequest request =
                new OAuthRequest(Verb.GET, url, service);
        service.signRequest(new OAuth2AccessToken(""), request);
        System.out.println(request.getBodyContents());
        System.out.println(request.getUrl());
        Response response = request.send();
        System.out.println(response.getBody());
    }

}
