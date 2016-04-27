//package model.component.visual;
//
//import javafx.beans.property.SimpleObjectProperty;
//import utility.SingleProperty;
//import voogasalad.util.spriteanimation.animation.AnimationContainer;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//
///**
// * This component contains the animated sprite
// *
// * @author Melissa Zhang
// */
//public class AnimatedSprite extends Sprite {
//    private final SingleProperty<String> singleProperty = new SingleProperty<>("BundlePath", "");
//    private transient AnimationContainer container;
//
//    /**
//     * Construct with no animation.
//     *
//     * @param imagePath starting value
//     */
//    public AnimatedSprite(String imagePath, String bundlePath) { // TODO: place default in resource file
//        this(imagePath, 0.0, 0.0, bundlePath);
//    }
//
//    /**
//     * Construct with starting values.
//     *
//     * @param imagePath   String path to image
//     * @param imageWidth  width of image
//     * @param imageHeight height of image
//     * @param imagePath   String path to spritesheet
//     */
//    public AnimatedSprite(String imagePath, double imageWidth, double imageHeight, String bundlePath) {
//        super(imagePath, imageWidth, imageHeight);
//        setBundlePath(bundlePath);
//        reInitializeContainer();
//    }
//
//    public SimpleObjectProperty<String> bundlePathProperty() {
//        return singleProperty.property1();
//    }
//
//    public String getBundlePath() {
//        return bundlePathProperty().get();
//    }
//
//    public void setBundlePath(String bundlePath) {
//        bundlePathProperty().set(bundlePath);
//    }
//
//    public AnimationContainer getContainer() {
//        return container;
//    }
//
//    private void reInitializeContainer() {
//        this.container = new AnimationContainer(getImageView(), getBundlePath());
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//        reInitializeContainer();
//    }
//}
