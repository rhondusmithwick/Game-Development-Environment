package voogasalad.util.facebookutil.test;

import java.awt.Dimension;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;


/**
 * Runs an example browser that can be incorporated into a game to integrate the Social utility
 *
 */
public class BrowserView {

    public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
    private static final String PROTECTED_RESOURCE_URL =
            "https://graph.facebook.com/v2.5/10204226196654701";

//    private static final String TWITTER_PROTECTED_RESOURCE_URL =
//            "https://api.twitter.com/1.1/account/verify_credentials.json";
    
    private static final String CALLBACK_URL =
            "https://duke.edu/";

    private OAuth20Service service;
    private OAuth10aService twitterService;
    private Scene myScene;
    private WebView myPage;
    private ResourceBundle mySecrets;

    public BrowserView () {
        mySecrets = ResourceBundle.getBundle("facebookutil/secret");
        BorderPane root = new BorderPane();
        root.setCenter(makePageDisplay());
        facebookExample();
        //twitterExample();

        myScene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height);

    }

    public Scene getScene () {
        return myScene;
    }

    private Node makePageDisplay () {
        myPage = new WebView();
        myPage.getEngine().getLoadWorker().stateProperty().addListener(new FacebookLinkListener());
        return myPage;
    }

    private class FacebookLinkListener implements ChangeListener<State> {

        @Override
        public void changed (ObservableValue<? extends State> ov, State oldState, State newState) {
            if (newState == Worker.State.SUCCEEDED) {
                String newURL = myPage.getEngine().getLocation();
                System.out.println("new URL" + newURL);
                Pattern pattern = Pattern.compile("code=([^&]+)");
                Matcher m = pattern.matcher(newURL);
                if (m.find()) {

                    System.out.println(m.group(1));
                    OAuth2AccessToken accessToken = service.getAccessToken(m.group(1));
                    System.out.println("Got the Access Token!");
                    System.out.println("(if your curious it looks like this: " + accessToken +
                                       ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
                    System.out.println(accessToken.getAccessToken());

                    System.out.println(accessToken.getAccessToken());

                    // Now let's go and ask for a protected resource!
                    System.out.println("Now we're going to access a protected resource...");
                    final OAuthRequest request =
                            new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
                    service.signRequest(accessToken, request);
                    final Response response = request.send();
                    System.out.println(request.getBodyContents());
                    System.out.println("Got it! Lets see what we found...");
                    System.out.println();
                    System.out.println(response.getCode());
                    System.out.println(response.getBody());
                    m = Pattern.compile("\"email\":\"([^&]+)\"").matcher(response.getBody());
                    if (m.find()) {
                        System.out.println(m.group(1));
                    }
                  //SEND A NOTIFICATION
//                      OAuthRequest nextRequest =
//                              new OAuthRequest(Verb.POST,
//                                               "https://graph.facebook.com/10204226196654701/apprequests",
//                                               service);
//                      String message = "Let's make tower defense!";
//                      nextRequest.addBodyParameter("access_token", accessToken.getAccessToken());
//                      nextRequest.addBodyParameter("message", message);
//                      nextRequest.addBodyParameter("to", "tommy.romanburg");
//                      service.signRequest(accessToken, nextRequest);
//    
//                      Response nextResponse = nextRequest.send();
//                      System.out.println("here");
//                      System.out.println(nextRequest.getBodyContents());
//                      System.out.println(nextResponse.getCode());
//                      String responseBody = nextResponse.getBody();
//                      System.out.println(responseBody);

                    // SEND A NOTIFICATION
                    OAuthRequest nextRequest =
                            new OAuthRequest(Verb.POST,
                                             "https://graph.facebook.com/10204226196654701/apprequests",
                                             service);
                    String message = "Let's make tower defense!";
                    nextRequest.addBodyParameter("message", message);
                    nextRequest.addBodyParameter("ids", "10204226196654701");
                    service.signRequest(accessToken, nextRequest);

                    Response nextResponse = nextRequest.send();
                    System.out.println("here");
                    System.out.println(nextRequest.getBodyContents());
                    System.out.println(nextResponse.getCode());
                    String responseBody = nextResponse.getBody();
                    System.out.println(responseBody);
                    //
                    //
                    // // ADD A HIGH SCORE
                    // nextRequest =
                    // new OAuthRequest(Verb.POST,
                    // "https://graph.facebook.com/10204226196654701/scores",
                    // service);
                    // message = "Let's make tower defense!";
                    // nextRequest.addBodyParameter("access_token", accessToken.getAccessToken());
                    // nextRequest.addBodyParameter("score", "100");
                    // // service.signRequest(accessToken, nextRequest);
                    //
                    // nextResponse = nextRequest.send();
                    // System.out.println("here");
                    // System.out.println(nextRequest.getBodyContents());
                    // System.out.println(nextResponse.getCode());
                    // responseBody = nextResponse.getBody();
                    // System.out.println(responseBody);

                }
                else {

                }
            }
        }
    }
    
    
    public class TwitterLinkListener implements ChangeListener<State> {

        @Override
        public void changed (ObservableValue<? extends State> ov, State oldState, State newState) {
            if (newState == Worker.State.SUCCEEDED && myPage.getEngine().getLocation().equals("https://api.twitter.com/oauth/authorize")) {
                String newURL = myPage.getEngine().getLocation();
                System.out.println("here");
                System.out.println(newURL);
                
                
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Twitter Access");
                dialog.setHeaderText("Tower Defense Social");
                dialog.setContentText("Permission Number:");

                // Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(code -> System.out.println("Permission Code: " + code));
                
                final String oauthVerifier = result.toString();
                System.out.println(oauthVerifier.toString());
                
                // Trade the Request Token and Verifier for the Access Token
                System.out.println("Trading the Request Token for an Access Token...");
                final OAuth1RequestToken requestToken = twitterService.getRequestToken();
                System.out.println("request: " + requestToken.toString());
                final OAuth1AccessToken accessToken = twitterService.getAccessToken(requestToken, oauthVerifier);
                System.out.println("Got the Access Token!");
                System.out.println("(if your curious it looks like this: " + accessToken +
                                   ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
                System.out.println();

                // Now let's go and ask for a protected resource!
                System.out.println("Now we're going to access a protected resource...");
                final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
                twitterService.signRequest(accessToken, request);
                final Response response = request.send();
                System.out.println("Got it! Lets see what we found...");
                System.out.println();
                System.out.println(response.getBody());

                System.out.println();
                System.out.println("That's it man! Go and build something awesome with ScribeJava! :)");

                }
                else {

                }
            }
        }
    
    
    
    
    

    public void facebookExample () {
        // Replace these with your client id and secret
        final String clientId = mySecrets.getString("facebookId");
        final String clientSecret = mySecrets.getString("facebookSecret");
        service = new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .callback(CALLBACK_URL)
                //.grantType("client_credentials")
                .scope("publish_actions,email,public_profile")
                // .scope("publish_actions")
                .build(FacebookApi.instance());

        System.out.println();

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        final String authorizationUrl = service.getAuthorizationUrl();
        myPage.getEngine().load(authorizationUrl);
    }

    public void twitterExample () {
        final String clientId = mySecrets.getString("twitterId");
        final String clientSecret = mySecrets.getString("twitterSecret");

        final OAuth10aService service = new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .grantType("client_credentials")
                .build(TwitterApi.instance());

        System.out.println("=== Twitter's OAuth Workflow ===");
        System.out.println();

        final OAuth1RequestToken requestToken = service.getRequestToken();
        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        System.out.println("request: " + requestToken.toString());
        final String authorizationUrl = service.getAuthorizationUrl(requestToken);
        myPage.getEngine().load(authorizationUrl);
    }
}
