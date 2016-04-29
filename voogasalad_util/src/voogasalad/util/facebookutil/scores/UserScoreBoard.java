package voogasalad.util.facebookutil.scores;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple class to manage the score board for a user.
 * Only the high score board should actually log a score
 * @author Tommy
 *
 */
public class UserScoreBoard {
    
    private Map<String, Integer> myBoard;
    
    public UserScoreBoard () {
        myBoard = new HashMap<>();
    }
    
    /**
     * Logs a score for a certain game. If the game doesn't
     * exist, or the score is lower, does nothing.
     * @param gameName
     * @param score
     */
    public void logScore (String gameName, int score) {
        if (!myBoard.containsKey(gameName) || myBoard.get(gameName) < score) {
            myBoard.put(gameName, score);
        }
    }

    /**
     * Gets the highest score for a game, or -1 if it hasn't
     * been played.
     * @param gameName
     * @return
     */
    public int getHighScore (String gameName) {
        if (!myBoard.containsKey(gameName)) {
            return -1;
        }
        return myBoard.get(gameName);
    }

}
