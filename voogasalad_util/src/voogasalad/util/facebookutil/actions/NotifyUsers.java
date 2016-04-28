package voogasalad.util.facebookutil.actions;

import java.util.List;
import voogasalad.util.facebookutil.user.IUser;


/**
 * This interface extends SocialAction and manages methods specifically for a notify users action
 *
 */
public interface NotifyUsers extends SocialAction {

    /**
     * Build a notification to post
     * 
     * @param users list of recipient users
     * @param message a written message to post
     */
    public void createNotification (List<IUser> users, String message);

}
