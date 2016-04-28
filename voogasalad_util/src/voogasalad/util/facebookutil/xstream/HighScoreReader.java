package voogasalad.util.facebookutil.xstream;

import java.io.File;
import java.util.ResourceBundle;
import voogasalad.util.facebookutil.JavaSocial;
import voogasalad.util.facebookutil.scores.HighScoreBoard;

/**
 * Reads the high scores from a file
 * @author Tommy
 *
 */
public class HighScoreReader {
    
    private ResourceBundle mySecrets;
    private XStreamReader myReader;
    
    public HighScoreReader () {
        mySecrets = ResourceBundle.getBundle(JavaSocial.RESOURCE_FOLDER + "secret");
        myReader = new XStreamReader();
    }

    /**
     * Gets the list of users from files
     * @return
     */
    public HighScoreBoard getBoard () {
        String fileName = mySecrets.getString("highscorefolder") + mySecrets.getString("scoreboardfile");
        File file = new File(fileName);
        HighScoreBoard board = (HighScoreBoard) myReader.getObject(file);
        if (board == null) {
            return new HighScoreBoard();
        }
        return board;
    }


}
