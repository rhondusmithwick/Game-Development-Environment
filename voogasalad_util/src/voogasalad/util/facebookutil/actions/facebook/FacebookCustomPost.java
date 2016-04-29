package voogasalad.util.facebookutil.actions.facebook;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import voogasalad.util.facebookutil.actions.CustomPost;
import voogasalad.util.facebookutil.actions.OAuthAction;
import voogasalad.util.facebookutil.user.profiles.SocialProfile;

public class FacebookCustomPost extends OAuthAction implements CustomPost{
    
    private static final String POST_URL = "https://graph.facebook.com/%s/feed";

    @Override
    public void createPost (String message, SocialProfile profile) {
        OAuthRequest myRequest = new OAuthRequest(Verb.POST,
                                     String.format(POST_URL, profile.getID()),
                                     profile.getLogin().getService());
        myRequest.addBodyParameter("message", message);
        setRequest(myRequest);
    }

}
