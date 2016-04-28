package voogasalad.util.facebookutil.applications;

import java.util.List;
import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.user.IUser;
import voogasalad.util.facebookutil.user.profiles.SocialProfile;

public interface AppActor {
    
    void notifyUsers(List<IUser> users, String message);
    
    void HighScoreNotify (HighScoreBoard board, SocialProfile profile);

}
