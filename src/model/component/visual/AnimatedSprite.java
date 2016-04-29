package model.component.visual;

import api.ISerializable;
import javafx.animation.Animation;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;
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
    private final SingleProperty<String> singleProperty = new SingleProperty<>("BundlePath", DEFAULT_BUNDLE);
    private CustomAnimationContainer container = new CustomAnimationContainer(DEFAULT_BUNDLE);

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

    public SimpleObjectProperty<String> bundlePathProperty() {
        return singleProperty.property1();
    }

    public String getBundlePath() {
        return bundlePathProperty().get();
    }

    public void setBundlePath(String bundlePath) {
        bundlePathProperty().set(bundlePath);
        reInitializeContainer();
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

    public Animation getAnimation(String animationName) {
        return getContainer().createAnimation(getImageView(), animationName);
    }

    public static class CustomAnimationContainer extends AnimationContainer implements ISerializable {
        public CustomAnimationContainer(String bundlePath) {
            super(bundlePath);
        }
    }
}
