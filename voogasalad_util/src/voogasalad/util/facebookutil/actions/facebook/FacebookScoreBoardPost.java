package voogasalad.util.facebookutil.actions.facebook;

import voogasalad.util.facebookutil.actions.HighScoreBoardPost;
import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.scores.HighScoreMessage;
import voogasalad.util.facebookutil.scores.ScoreOrder;
import voogasalad.util.facebookutil.user.profiles.SocialProfile;

public class FacebookScoreBoardPost extends FacebookCustomPost implements HighScoreBoardPost {

    @Override
    public void createBoardPost (HighScoreBoard board,
                                 String gameName,
                                 ScoreOrder order,
                                 SocialProfile profile) {
        HighScoreMessage message = new HighScoreMessage(board);
        String boardString = message.getHighScoreListString(gameName, order);
        createPost(boardString, profile);
    }

}
