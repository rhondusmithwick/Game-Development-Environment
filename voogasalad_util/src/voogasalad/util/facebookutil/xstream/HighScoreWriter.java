package voogasalad.util.facebookutil.xstream;

import java.util.ResourceBundle;
import voogasalad.util.facebookutil.JavaSocial;
import voogasalad.util.facebookutil.scores.HighScoreBoard;

/**
 * Writes the high scoreboard to a file to get
 * later.
 * @author Tommy
 *
 */
public class HighScoreWriter {
    
    private ResourceBundle mySecrets;
    private XStreamWriter<HighScoreBoard> myWriter;
    
    public HighScoreWriter () {
        mySecrets = ResourceBundle.getBundle(JavaSocial.RESOURCE_FOLDER + "secret");
        myWriter = new XStreamWriter<>();
    }
    
    public void write (HighScoreBoard board) {
        String dirPath = mySecrets.getString("highscorefolder");
        String file = mySecrets.getString("scoreboardfile");
        myWriter.writeToFile(board, file, dirPath);
    }
}
