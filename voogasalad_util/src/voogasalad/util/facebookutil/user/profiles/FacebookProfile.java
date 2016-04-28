package voogasalad.util.facebookutil.user.profiles;

import voogasalad.util.facebookutil.SocialType;
import voogasalad.util.facebookutil.actions.facebook.FacebookChallenge;
import voogasalad.util.facebookutil.actions.facebook.FacebookCustomPost;
import voogasalad.util.facebookutil.actions.facebook.FacebookHighScorePost;
import voogasalad.util.facebookutil.actions.facebook.FacebookScoreBoardPost;
import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.scores.ScoreOrder;
import voogasalad.util.facebookutil.user.IUser;


/**
 * Profile for Facebook users
 * @author Tommy
 *
 */
public class FacebookProfile extends UserProfile {

    public FacebookProfile (String userID) {
        super(userID);
    }

    @Override
    public void challenge (IUser source, IUser target, String message) {
        FacebookChallenge challenge = new FacebookChallenge();
        challenge.createChallenge(source.getProfiles().getProfileByType(SocialType.FACEBOOK),
                                  source.getProfiles().getProfileByType(SocialType.FACEBOOK), message);
        challenge.send(getLogin());

    }

    @Override
    public void customPost (String message) {
        FacebookCustomPost post = new FacebookCustomPost();
        post.createPost(message, this);
        post.send(getLogin());
    }

    @Override
    public void highScorePost (HighScoreBoard board,
                               String gameName,
                               IUser user,
                               ScoreOrder order) {
        FacebookHighScorePost post = new FacebookHighScorePost();
        post.createPost(board, user, gameName, order);
        post.send(getLogin());
    }

    @Override
    public void highScoreBoardPost (HighScoreBoard board,
                                    String gameName,
                                    ScoreOrder order) {
        FacebookScoreBoardPost post = new FacebookScoreBoardPost();
        post.createBoardPost(board, gameName, order, this);
        post.send(getLogin());
    }

}
