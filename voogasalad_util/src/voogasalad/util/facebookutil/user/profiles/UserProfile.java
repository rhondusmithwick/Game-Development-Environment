package voogasalad.util.facebookutil.user.profiles;

import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.scores.ScoreOrder;
import voogasalad.util.facebookutil.user.IUser;

/**
 * Interface that describes the actions a user can do, like
 * challenge and post
 * @author Tommy
 *
 */
public abstract class UserProfile extends SocialProfile {

    public UserProfile (String userID) {
        super(userID);
    }

    public abstract void challenge (IUser source, IUser target, String message);

    public abstract void customPost (String message);

    public abstract void highScorePost (HighScoreBoard board, String gameName, IUser user, ScoreOrder order);

    public abstract void highScoreBoardPost (HighScoreBoard board,
                                             String gameName,
                                             ScoreOrder order);

}
