package voogasalad.util.facebookutil.actions.facebook;

import voogasalad.util.facebookutil.SocialType;
import voogasalad.util.facebookutil.actions.HighScorePost;
import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.scores.HighScoreMessage;
import voogasalad.util.facebookutil.scores.ScoreOrder;
import voogasalad.util.facebookutil.user.IUser;

public class FacebookHighScorePost extends FacebookCustomPost implements HighScorePost {

    @Override
    public void createPost (HighScoreBoard board, IUser user, String gameName, ScoreOrder order) {
        HighScoreMessage message = new HighScoreMessage(board);
        String boardString = message.getUserScoreString(user, gameName, order);
        createPost(boardString, user.getProfiles().getProfileByType(SocialType.FACEBOOK));
    }

}
