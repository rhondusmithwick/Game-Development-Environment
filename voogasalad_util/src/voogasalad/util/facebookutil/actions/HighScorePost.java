package voogasalad.util.facebookutil.actions;

import voogasalad.util.facebookutil.scores.HighScoreBoard;
import voogasalad.util.facebookutil.scores.ScoreOrder;
import voogasalad.util.facebookutil.user.IUser;


/**
 * This class is for posting about a user's own high score. An example is :
 * "I now have the #3 highest score for the game Plants Vs. Zombies
 *
 */
public interface HighScorePost extends CustomPost {

    /**
     * Builds high score post for a user
     * 
     * @param user game player with the resulting high score
     * @param score the new high score for the user
     */
    public void createPost (HighScoreBoard board, IUser user, String gameName, ScoreOrder order);

}
