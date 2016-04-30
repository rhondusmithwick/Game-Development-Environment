package model.component.visual;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private static final double DEFAULT_WIDTH = 638.0, DEFAULT_HEIGHT = 518.0;

    private final SingleProperty<String> imagePathProperty = new SingleProperty<>("ImagePath", DEFAULT_IMAGE_PATH);
    private final TwoProperty<Double, Double> imageSizeProperty = new TwoProperty<>("ImageWidth", 0.0, "ImageHeight", 0.0);
    private final SingleProperty<Integer> zLevelProperty = new SingleProperty<>("zLevel", 0);
    private transient ImageView imageView;
    private transient ChangeListener<String> imagePathListener;

    public Sprite () {
        this(DEFAULT_IMAGE_PATH);
    }

    /**
     * Construct with no animation.
     *
     * @param imagePath starting value
     */
    public Sprite (String imagePath) {
        setImagePath(imagePath);
        //        Image image = getImage(imagePath);
        //        setImageWidth(image.getWidth());
        //        setImageHeight(image.getHeight());
        addImagePathListener();
        imageView = createImageView(imagePath);

    }

    private void addImagePathListener () {
        imagePathListener = (new ChangeListener<String>() {
            public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setImagePath(newValue);
            }
        });
        imagePathProperty().addListener(imagePathListener);
    }

    /**
     * Construct with starting values.
     *
     * @param imagePath   String path to image
     * @param imageWidth  width of image
     * @param imageHeight height of image
     * @param imagePath   String path to spritesheet
     */
    public Sprite (String imagePath, double imageWidth, double imageHeight) {
        this(imagePath);
        setImageWidth(imageWidth);
        setImageHeight(imageHeight);
    }

    @Deprecated
    public Sprite (String imagePath, double imageWidth, double imageHeight, int zLevel) {
        this(imagePath, imageWidth, imageHeight);
        setZLevel(zLevel);
    }

    /**
     * Get the imagePath property.
     *
     * @return impagePath string property
     */
    public SimpleObjectProperty<String> imagePathProperty () {
        return imagePathProperty.property1();
    }

    public String getImagePath () {
        return imagePathProperty().get();
    }

    public void setImagePath (String imagePath) {
        imagePathProperty().set(imagePath);
        this.imageView = this.createImageView(getImagePath());
        setImageHeight(getImageHeight());
        setImageWidth(getImageWidth());
    }

    public SimpleObjectProperty<Double> imageWidthProperty () {
        return imageSizeProperty.property1();
    }

    public double getImageWidth () {
        return this.imageWidthProperty().get();
    }

    public void setImageWidth (double imageWidth) {
        this.imageWidthProperty().set(imageWidth);
        if (imageView != null) {
            imageView.setFitWidth(getImageWidth());
        }
    }

    public SimpleObjectProperty<Double> imageHeightProperty () {
        return imageSizeProperty.property2();
    }

    public double getImageHeight () {
        return this.imageHeightProperty().get();
    }

    public void setImageHeight (double imageHeight) {
        this.imageHeightProperty().set(imageHeight);
        if (imageView != null) {
            imageView.setFitHeight(getImageHeight());
        }
    }

    @Deprecated
    public SimpleObjectProperty<Integer> zLevelProperty () {
        return this.zLevelProperty.property1();
    }

    @Deprecated
    /**
     * Sets the z-layer order.
     *
     * @param z the z-layer order (1=>send to back, 1=>send to front)
     */
    public void setZLevel (int z) {
        zLevelProperty().set(z);
    }

    @Deprecated
    /**
     * Gets the z-layer order.
     *
     * @return the z-layer order (1=>send to back, 1=>send to front)
     */
    public int getZLevel () {
        return zLevelProperty().get();
    }


    private void writeObject(ObjectOutputStream out) throws IOException {
        imagePathProperty().removeListener(imagePathListener);
        out.defaultWriteObject();
    }

    private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        addImagePathListener();
        this.imageView = this.createImageView(getImagePath());
    }

    private ImageView createImageView (String imagePath) {
        Image image = getImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        //        imageView.set
        return imageView;
    }

    private Image getImage (String imagePath) {
        File resource = new File(imagePath);
        return new Image(resource.toURI().toString());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return Arrays.asList(imagePathProperty(), imageWidthProperty(), imageHeightProperty(), zLevelProperty());
    }

    public ImageView getImageView () {
        return imageView;
    }

    @Override
    public void update() {
        setImagePath(getImagePath());
        setImageHeight(getImageHeight());
        setImageWidth(getImageWidth());
    }
}
