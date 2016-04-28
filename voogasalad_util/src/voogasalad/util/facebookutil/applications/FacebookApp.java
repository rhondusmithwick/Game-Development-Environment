package voogasalad.util.facebookutil.applications;

import java.util.List;
import voogasalad.util.facebookutil.actions.facebook.FacebookNotifyUsers;
import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.user.IUser;
import voogasalad.util.facebookutil.user.profiles.SocialProfile;

/**
 * Implementation of an app for Facebook.
 * Can login, notify, and (not yet) post to facebook. Still
 * working on that one...
 * @author Tommy
 *
 */
public class FacebookApp extends App{

    @Override
    public void login () {
        FacebookAppLogin appLogin = new FacebookAppLogin();
        login(appLogin.getLoginObject());
    }
    
    @Override
    public void notifyUsers (List<IUser> users, String message) {
        FacebookNotifyUsers notify = new FacebookNotifyUsers();
        notify.createNotification(users, message);
        notify.send(getLogin());
    }

    @Override
    public void customPost (String message, SocialProfile profile) {
        
    }

    @Override
    public void HighScoreBoardPost (HighScoreBoard board, SocialProfile profile) {
        // TODO Auto-generated method stub
        
    }

}
