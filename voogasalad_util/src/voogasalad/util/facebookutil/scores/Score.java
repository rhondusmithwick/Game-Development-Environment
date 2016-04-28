package voogasalad.util.facebookutil.scores;

import java.util.Date;
import voogasalad.util.facebookutil.user.IUser;

/**
 * Simple wrapper for a score that stores its
 * user, points, and date so that we can sort the scores
 * in varying orders.
 * @author Tommy
 *
 */
public class Score {
    
    public IUser myUser;
    public int myPoints;
    public Date myDate;
    
    public Score (IUser user, int score) {
        myUser = user;
        myPoints = score;
        recordDate();
    }
    
    private void recordDate () {
        myDate = new Date();
    }

    public IUser getUser () {
        return myUser;
    }
    
    public int getPoints () {
        return myPoints;
    }
    
    public Date getDate () {
        return myDate;
    }

    public void setPoints (int newPoints) {
        myPoints = newPoints;
        recordDate();
    }
}
