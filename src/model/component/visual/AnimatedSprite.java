package model.component.visual;

import api.ISerializable;
import javafx.animation.Animation;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;
import utility.TwoProperty;
import voogasalad.util.spriteanimation.animation.AnimationContainer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This component contains the animated sprite
 *
 * @author Melissa Zhang
 */
public class AnimatedSprite extends Sprite {

	private static final String DEFAULT_IMAGE = "resources/spriteSheets/ryuBlue.gif";
    private static final String DEFAULT_BUNDLE = "spriteProperties/ryuBlue";
    private final TwoProperty<String, String> twoProperty = new TwoProperty<>("BundlePath", DEFAULT_BUNDLE, "DefaultAnimation", "");
    private CustomAnimationContainer container = new CustomAnimationContainer(DEFAULT_BUNDLE);
    private String currentAnimationName="";
    private SingleProperty<String> defaultAnimationName=new SingleProperty<>("DefaultAnimationName", "");;
    private transient Animation currentAnimation;

    public AnimatedSprite() {
    	this(DEFAULT_IMAGE, DEFAULT_BUNDLE);
    }

    /**
     * Construct with no animation.
     *
     * @param imagePath starting value
     */
    public AnimatedSprite(String imagePath, String bundlePath) { // TODO: place default in resource file
        super(imagePath);
        setBundlePath(bundlePath);
    }

    /**
     * Construct with starting values.
     *
     * @param imagePath   String path to image
     * @param imageWidth  width of image
     * @param imageHeight height of image
     * @param imagePath   String path to spritesheet
     */
    public AnimatedSprite(String imagePath, double imageWidth, double imageHeight, String bundlePath) {
        super(imagePath, imageWidth, imageHeight);
        setBundlePath(bundlePath);
    }
    
    public AnimatedSprite(String imagePath, double imageWidth, double imageHeight, String bundlePath, String defaultAnimation) {
        this(imagePath, imageWidth, imageHeight, bundlePath);
        setDefaultAnimation(defaultAnimation);
    }

    public SimpleObjectProperty<String> bundlePathProperty() {
        return twoProperty.property1();
    }

    public String getBundlePath() {
        return bundlePathProperty().get();
    }

    public void setBundlePath(String bundlePath) {
        bundlePathProperty().set(bundlePath);
        reInitializeContainer();
    }
    
    public SimpleObjectProperty<String> defaultAnimationProperty() {
        return twoProperty.property2();
    }

    public String getDefaultAnimation() {
        return defaultAnimationProperty().get();
    }

    public void setDefaultAnimation(String defaultAnimation) {
    	defaultAnimationProperty().set(defaultAnimation);
    }

    @Override
    public void setImagePath(String imagePath) {
        super.setImagePath(imagePath);
    }

    public AnimationContainer getContainer() {
        return container;
    }

    private void reInitializeContainer() {
        this.container = new CustomAnimationContainer(getBundlePath());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return Arrays.asList(bundlePathProperty(), imagePathProperty(), imageWidthProperty(), imageHeightProperty(), zLevelProperty());

    }

    public Collection<String> getAnimationNames() {
        return getContainer().getAnimationNames();
    }

    public boolean hasAnimation(String animationName) {
        return getContainer().hasAnimation(animationName);
    }

    public Animation createAnimation(String animationName) {
        Animation animation = getContainer().createAnimation(getImageView(), animationName);
        return animation;
    }

    public Animation createAndPlayAnimation(String animationName) {
    	if(!animationName.equals(currentAnimationName)) {
    		if(currentAnimation!=null) {
    			currentAnimation.stop();
    		}
    		currentAnimationName = animationName;
    		currentAnimation = createAnimation(animationName);
    		currentAnimation.setOnFinished(e->createAnimation(getDefaultAnimation()).play());
        	currentAnimation.play();
    	}
    	return currentAnimation;
    }
    
    public static class CustomAnimationContainer extends AnimationContainer implements ISerializable {
        public CustomAnimationContainer(String bundlePath) {
            super(bundlePath);
        }
    }
    
}
