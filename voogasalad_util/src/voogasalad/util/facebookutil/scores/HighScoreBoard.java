package voogasalad.util.facebookutil.scores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import voogasalad.util.facebookutil.user.IUser;

/**
 * Class to keep of global high scores across all games.
 * User can use this class to return the scores in any order.
 * @author Tommy
 *
 */
public class HighScoreBoard {
    
    Map<String, List<Score>> myScores;
    
    public HighScoreBoard () {
        myScores = new HashMap<>();
    }
    /**
     * Record a new score for a game. Also logs the user score
     * @param gameName - name of the game
     * @param user - user that achieved this score
     * @param newScore - score user achieved (does not necessarily need to be the highest)
     */
    public void addNewScore (String gameName, IUser user, int newScore) {
        recordUserScore (gameName, user, newScore);
        List<Score> myGame = getOrCreateGame(gameName);
        Score score = getScore(myGame, user);
        if (score != null) {
            recordScore(score, newScore);
        } else {
            myGame.add(new Score(user, newScore));
        }
    }

    /**
     * Helper to record the score for the user
     * @param gameName
     * @param user
     * @param newScore
     */
    private void recordUserScore (String gameName, IUser user, int newScore) {
        user.getScoreBoard().logScore(gameName, newScore);
    }
    
    /**
     * Helper to get the user's score in the game's score list
     * @param myGame
     * @param user
     * @return
     */
    private Score getScore (List<Score> myGame, IUser user) {
        for (Score score : myGame) {
            if (score.getUser().equals(user)) {
                return score;
            }
        }
        return null;
    }

    /**
     * Helper to record a score. Only changes the score
     * if it's better
     * @param score
     * @param newScore
     */
    private void recordScore (Score score, int newScore) {
        if (score.getPoints() < newScore) {
            score.setPoints(newScore);
        }
    }

    /**
     * Helper to get the game by name or create it if it doesn't exist
     * @param gameName
     * @return
     */
    private List<Score> getOrCreateGame (String gameName) {
        if (!myScores.containsKey(gameName)) {
            myScores.put(gameName, new ArrayList<Score>());
        }
        return myScores.get(gameName);
    }

    /**
     * Public API to get the score board as is
     * @param gameName
     * @return
     */
    public List<Score> getScoreBoard (String gameName) {
        return myScores.get(gameName);
    }
    
    /**
     * Public API to get the score board sorted by score, user email,
     * or date that the score was achieved
     * @param gameName
     * @param order - SCORE/ALPHABETICAL/DATE
     * @param reverse - true if you want reverse score, reverse alphabetical, reverse date
     * @return
     */
    public List<Score> getScoreBoardSorted (String gameName, ScoreOrder order) {
        List<Score> game = getScoreBoard(gameName);
        if (game == null){
            return null;
        }
        game.sort(order.getComparator());
        return game;
    }

}
