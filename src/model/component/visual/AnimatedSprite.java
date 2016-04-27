package model.component.visual;

import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;
import voogasalad.util.spriteanimation.animation.AnimationContainer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * This component contains the animated sprite
 *
 * @author Melissa Zhang
 */
public class AnimatedSprite extends Sprite {
    private static final String DEFAULT_BUNDLE = "";
    private final SingleProperty<String> singleProperty = new SingleProperty<>("BundlePath", DEFAULT_BUNDLE);
    private transient AnimationContainer container;

    public AnimatedSprite() {
    }

    /**
     * Construct with no animation.
     *
     * @param imagePath starting value
     */
    public AnimatedSprite(String imagePath, String bundlePath) { // TODO: place default in resource file
        this(imagePath, 0.0, 0.0, bundlePath);
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
        reInitializeContainer();
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

    @Override
    public void setImageWidth(double imageWidth) {
        super.setImageWidth(imageWidth);
        reInitializeContainer();
    }

    @Override
    public void setImagePath(String imagePath) {
        super.setImagePath(imagePath);
        reInitializeContainer();
    }

    @Override
    public void setImageHeight(double imageHeight) {
        super.setImageHeight(imageHeight);
        reInitializeContainer();
    }

    public AnimationContainer getContainer() {
        return container;
    }

    private void reInitializeContainer() {
        this.container = new AnimationContainer(getImageView(), getBundlePath());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        reInitializeContainer();
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        List<SimpleObjectProperty<?>> superProps = super.getProperties();
        superProps.add(bundlePathProperty());
        return superProps;
    }


}
