package voogasalad.util.facebookutil.actions;

import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.scores.ScoreOrder;
import voogasalad.util.facebookutil.user.profiles.SocialProfile;


/**
 * This is for when posting the entire score board for a game. An example is:
 * "The highest scores for the game Plants vs. Zombies are:
 * #1 tomrom95@gmail.com - 5000
 * #2 someone@someone.com - 200
 *
 */
public interface HighScoreBoardPost extends Post {

    public abstract void createBoardPost (HighScoreBoard board,
                                 String gameName,
                                 ScoreOrder order,
                                 SocialProfile profile);

}
