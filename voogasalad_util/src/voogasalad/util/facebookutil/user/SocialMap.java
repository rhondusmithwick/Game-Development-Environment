package voogasalad.util.facebookutil.user;

import java.util.HashMap;
import java.util.Map;
import voogasalad.util.facebookutil.SocialType;
import voogasalad.util.facebookutil.user.profiles.UserProfile;

public class SocialMap {
    
    private Map<SocialType, UserProfile> myProfiles;
    private UserProfile myActiveProfile;
    
    public SocialMap () {
        myProfiles = new HashMap<SocialType, UserProfile>();
    }
    
    public UserProfile getProfileByType (SocialType type){
        return myProfiles.get(type);
    }
    
    public boolean setActive (SocialType type) {
        if(!myProfiles.containsKey(type)){
            return false;
        }
        myActiveProfile = myProfiles.get(type);
        return true;
    }
    
    public void createNewProfile (SocialType type, String userID) {
        myProfiles.put(type, type.getProfile(userID));
    }

    public UserProfile getActiveProfile () {
        return myActiveProfile;
    }

}
