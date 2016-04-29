package voogasalad.util.spriteanimation.animation;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import static java.util.ResourceBundle.getBundle;
import static voogasalad.util.spriteanimation.animation.AnimationFactory.createAnimationFromMap;
import static voogasalad.util.spriteanimation.utility.StringParser.converStringToStringList;

/**
 * Allows playing of different animations from a properties file.
 *
 * @author Rhondu Smithwick
 */
public class AnimationContainer implements Serializable {
    private final Map<String, String> properties;
    private final Set<String> animationNames;

    /**
     * Sole constructor.
     *
     * @param bundlePath the bundle of the animations
     */
    public AnimationContainer(String bundlePath) {
        properties = getPropertiesMap(bundlePath);
        String animationsString = properties.get("animationNames");
        this.animationNames = new HashSet<>(converStringToStringList(animationsString));
    }

    /**
     * Get all the animations names.
     *
     * @return the collection of animations
     */
    public Collection<String> getAnimationNames() {
        return animationNames;
    }

    /**
     * Check if it has an animation.
     *
     * @param animationName the animation
     * @return true if it has the animation
     */
    public boolean hasAnimation(String animationName) {
        return animationNames.contains(animationName);
    }

    /**
     * Get an animation from the animation name
     *
     * @param animationName the animation name
     * @param imageView     the imageView to act on
     * @return the animation with this name
     */
    public Animation createAnimation(ImageView imageView, String animationName) {
        if (!hasAnimation(animationName)) {
            throw new IllegalArgumentException("No animation with name " + animationName);
        }
        return createAnimationFromMap(imageView, animationName, properties);
    }

    private Map<String, String> getPropertiesMap(String bundlePath) {
        Map<String, String> map = new HashMap<>();
        ResourceBundle bundle = getBundle(bundlePath);
        Enumeration<String> iter = bundle.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            map.put(key, bundle.getString(key));
        }
        return map;
    }

}
