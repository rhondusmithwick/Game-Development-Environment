package model.component.visual;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.SingleProperty;
import utility.TwoProperty;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Component to hold an imagePath.
 *
 * @author Rhondu Smithwick, Melissa Zhang, Bruna Liborio
 */
public class Sprite implements IComponent {

    private static final String DEFAULT_IMAGE_PATH = "resources/testing/RhonduSmithwick.JPG";

    private final SingleProperty<String> imagePathProperty = new SingleProperty<>("ImagePath", DEFAULT_IMAGE_PATH);
    private final TwoProperty<Double, Double> imageSizeProperty = new TwoProperty<>("ImageWidth", 0.0, "ImageHeight", 0.0);
    private final SingleProperty<Integer> zLevelProperty = new SingleProperty<>("zLevel", 0);
    private transient ImageView imageView;

    public Sprite() {
    }

    /**
     * Construct with no animation.
     *
     * @param imagePath starting value
     */
    public Sprite(String imagePath) { // TODO: place default in resource file
        setImagePath(imagePath);
        Image image = getImage(imagePath);
        setImageWidth(image.getWidth());
        setImageHeight(image.getHeight());
    }

    /**
     * Construct with starting values.
     *
     * @param imagePath   String path to image
     * @param imageWidth  width of image
     * @param imageHeight height of image
     * @param imagePath   String path to spritesheet
     */
    public Sprite(String imagePath, double imageWidth, double imageHeight) {
        this(imagePath);
        setImageWidth(imageWidth);
        setImageHeight(imageHeight);
    }

    public Sprite(String imagePath, double imageWidth, double imageHeight, int zLevel) {
        this(imagePath, imageWidth, imageHeight);
        setZLevel(zLevel);
    }

    /**
     * Get the imagePath property.
     *
     * @return impagePath string property
     */
    public SimpleObjectProperty<String> imagePathProperty() {
        return imagePathProperty.property1();
    }

    public String getImagePath() {
        return imagePathProperty().get();
    }

    public void setImagePath(String imagePath) {
        imagePathProperty().set(imagePath);
        this.imageView = this.createImageView(getImagePath());
    }

    public SimpleObjectProperty<Double> imageWidthProperty() {
        return imageSizeProperty.property1();
    }

    public double getImageWidth() {
        return this.imageWidthProperty().get();
    }

    public void setImageWidth(double imageWidth) {
        this.imageWidthProperty().set(imageWidth);
    }

    public SimpleObjectProperty<Double> imageHeightProperty() {
        return imageSizeProperty.property2();
    }

    public double getImageHeight() {
        return this.imageHeightProperty().get();
    }

    public void setImageHeight(double imageHeight) {
        this.imageHeightProperty().set(imageHeight);
    }

    public SimpleObjectProperty<Integer> zLevelProperty() {
        return this.zLevelProperty.property1();
    }

    /**
     * Sets the z-layer order.
     *
     * @param z the z-layer order (1=>send to back, 1=>send to front)
     */
    public void setZLevel(int z) {
        zLevelProperty().set(z);
    }

    /**
     * Gets the z-layer order.
     *
     * @return the z-layer order (1=>send to back, 1=>send to front)
     */
    public int getZLevel() {
        return zLevelProperty().get();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        imageHeightProperty().unbind();
        imageWidthProperty().unbind();
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.imageView = this.createImageView(getImagePath());
    }

    private ImageView createImageView(String imagePath) {
        Image image = getImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(imageWidthProperty());
        imageView.fitHeightProperty().bind(imageHeightProperty());
        return imageView;
    }

    private Image getImage(String imagePath) {
        File resource = new File(imagePath);
        return new Image(resource.toURI().toString());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return Arrays.asList(imagePathProperty(), imageWidthProperty(), imageHeightProperty(), zLevelProperty());
    }

    public ImageView getImageView() {
        return imageView;
    }


}