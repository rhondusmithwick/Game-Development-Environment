package voogasalad.util.facebookutil.actions.facebook;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import voogasalad.util.facebookutil.actions.Challenge;
import voogasalad.util.facebookutil.actions.OAuthAction;
import voogasalad.util.facebookutil.user.profiles.UserProfile;

public class FacebookChallenge extends OAuthAction implements Challenge{
    private static final String CHALLENGE_URL = "https://graph.facebook.com/%s/apprequests";
    

    @Override
    public void createChallenge (UserProfile source, UserProfile target, String message) {
        OAuthRequest myRequest = new OAuthRequest(Verb.POST,
                                 String.format(CHALLENGE_URL, target.getID()),
                                 source.getLogin().getService());
        myRequest.addBodyParameter("message", message);
        setRequest(myRequest);
    }

}
