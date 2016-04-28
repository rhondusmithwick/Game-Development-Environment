package voogasalad.util.facebookutil.user;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import voogasalad.util.facebookutil.SocialType;
import voogasalad.util.facebookutil.login.LoginObject;
import voogasalad.util.facebookutil.scores.UserScoreBoard;

public class User implements IUser{
    
    private Email myEmail;
    private UserScoreBoard myScoreBoard;
    @XStreamOmitField
    private SocialMap myProfiles;
    
    public User (Email email) {
        myEmail = email;
        myScoreBoard = new UserScoreBoard();
        myProfiles = new SocialMap ();
    }

    @Override
    public Email getUserEmail () {
        return myEmail;
    }

    @Override
    public UserScoreBoard getScoreBoard () {
        return myScoreBoard;
    }

    @Override
    public void logout () {
        // TODO setup with XStream
        
    }
    
    @Override
    public void login (SocialType type, LoginObject login) {
        if (myProfiles.getProfileByType(type) == null){
            createNewProfile(type, login);
        } else {
            loginExisting(type, login);
        }
    }
    
    private void loginExisting (SocialType type, LoginObject login) {
        myProfiles.getProfileByType(type).login(login);
        myProfiles.setActive(type);
    }

    private void createNewProfile (SocialType type, LoginObject login) {
        myProfiles.createNewProfile(type, login.getUserID());
        loginExisting(type, login);
    }

    @Override
    public SocialMap getProfiles () {
        return myProfiles;
    }
    
    @Override
    public boolean equals (Object obj) {
        if (! (obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        return getUserEmail().equals(other.getUserEmail());
    }

}
