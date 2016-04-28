package voogasalad.util.facebookutil.actions.facebook;

import java.util.ArrayList;
import java.util.List;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import voogasalad.util.facebookutil.SocialType;
import voogasalad.util.facebookutil.actions.NotifyUsers;
import voogasalad.util.facebookutil.actions.OAuthAction;
import voogasalad.util.facebookutil.login.LoginObject;
import voogasalad.util.facebookutil.user.IUser;
import voogasalad.util.facebookutil.user.profiles.SocialProfile;

public class FacebookNotifyUsers extends OAuthAction implements NotifyUsers {
    private static final String REQUEST_URL = "https://graph.facebook.com/%s/notifications";
    
    private List<String> myRequests = new ArrayList<>();
    private String myMessage;
    
    @Override
    public void send (LoginObject login) {
        myRequests.stream()
                  .forEach(s -> {
                      setRequest(createRequest(login, s));
                      super.send(login);
                  });
    }

    @Override
    public void createNotification (List<IUser> users, String message) {
        myMessage = message;
        users.stream().forEach(u -> createRequestURL(u, message) );
    }
    
    private OAuthRequest createRequest (LoginObject login, String url) {
        OAuthRequest request =
                new OAuthRequest(Verb.POST, url, login.getService());
        request.addBodyParameter("template", myMessage);
        return request;
    }

    private void createRequestURL (IUser user, String message) {
        SocialProfile profile = user.getProfiles().getProfileByType(SocialType.FACEBOOK);
        if (profile == null) {
            return;
        }
        addURL(profile.getID(), message);
        
    }

    private void addURL (String id, String message) {
        myRequests.add(String.format(REQUEST_URL, id));
    }

}
