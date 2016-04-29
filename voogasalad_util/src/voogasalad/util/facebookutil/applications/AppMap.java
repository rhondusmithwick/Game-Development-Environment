package voogasalad.util.facebookutil.applications;

import java.util.HashMap;
import java.util.Map;
import voogasalad.util.facebookutil.SocialType;

/**
 * Creates and logs in all of the apps that are available
 * for the social environment
 * @author Tommy
 *
 */
public class AppMap {
    
    private Map<SocialType, App> myMap;
    
    public AppMap () {
        myMap = new HashMap<>();
    }
    
    /**
     * Logs in all apps in the social type enum
     */
    public void loginApps () {
        for(SocialType type: SocialType.values()) {
            App app = type.getApp();
            app.login();
            myMap.put(type, app);
        }
    }
    
    /**
     * Gets an app by its social type
     * @param type
     * @return
     */
    public App getAppByType (SocialType type) {
        return myMap.get(type);
    }

}
