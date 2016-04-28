package voogasalad.util.facebookutil.scores;

import java.util.List;
import java.util.ResourceBundle;
import voogasalad.util.facebookutil.JavaSocial;
import voogasalad.util.facebookutil.user.IUser;

/**
 * Helper class to create String messages out of the high score board
 * for posting
 * @author Tommy
 *
 */
public class HighScoreMessage {

    private static final String LANGUAGE_FILE = JavaSocial.RESOURCE_FOLDER + "englishposts";
    private static final String SCORE_FORMAT = "#%d %s - %d points\n";

    private ResourceBundle myProperties;
    private HighScoreBoard myBoard;

    public HighScoreMessage (HighScoreBoard board) {
        myProperties = ResourceBundle.getBundle(LANGUAGE_FILE);
        myBoard = board;
    }

    public String getHighScoreListString (String gameName, ScoreOrder order) {
        List<Score> scores = myBoard.getScoreBoardSorted(gameName, order);
        StringBuffer message = new StringBuffer();
        addInitialMessage(message, gameName, order);
        addScores(message, scores);
        return message.toString();
    }
    
    public String getUserScoreString (IUser user, String gameName, ScoreOrder order) {
        int place = getOrder(myBoard.getScoreBoardSorted(gameName, order), user);
        return createUserScoreString(place, order, gameName);
    }
    
    public String getNotifyString (IUser user, String gameName, ScoreOrder order) {
        int place = getOrder(myBoard.getScoreBoardSorted(gameName, order), user);
        return createNotifyString(place, order, gameName);
    }

    private void addScores (StringBuffer message, List<Score> scores) {
        for (int i = 0; i < scores.size(); i++) {
            message.append(scoreString(scores.get(i), i + 1));
        }

    }

    private String scoreString (Score score, int place) {
        return String.format(SCORE_FORMAT, place, score.getUser().getUserEmail().toString(),
                             score.getPoints());
    }

    private void addInitialMessage (StringBuffer message, String gameName, ScoreOrder order) {
        message.append(String.format(getTemplate("boardpost"), getAdjective(order), gameName));
    }

    private String createUserScoreString (int place, ScoreOrder order, String gameName) {
        return String.format(getTemplate("scorepost"), place, getAdjective(order), gameName);
    }
    
    private String createNotifyString (int place, ScoreOrder order, String gameName) {
        return String.format(getTemplate("notifyscorepost"), place, getAdjective(order), gameName);
    }

    private int getOrder (List<Score> scores, IUser user) {
        for (int i = 0; i < scores.size(); i ++) {
            if (scores.get(i).getUser().equals(user)) {
                return i + 1;
            }
        }
        return -1;
    }
    
    private String getTemplate (String name) {
        return myProperties.getString(name);
    }
    
    private String getAdjective (ScoreOrder order) {
        return myProperties.getString(order.name());
    }
    
}
