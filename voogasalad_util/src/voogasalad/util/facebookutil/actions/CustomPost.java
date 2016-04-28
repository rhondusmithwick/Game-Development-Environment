package voogasalad.util.facebookutil.actions;

import voogasalad.util.facebookutil.user.profiles.SocialProfile;

/**
 * This interface is responsible for handling user generated custom posts
 * 
 *
 */
public interface CustomPost extends Post {

    public void createPost (String message, SocialProfile profile);

}
