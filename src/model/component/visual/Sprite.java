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
    private transient ImageView imageView;

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

        imageView = createImageView(imagePath);

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
        System.out.println("image path " + imagePath);
        imageView = this.createImageView(imagePath);
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

    private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.imageView = this.createImageView(getImagePath());
    }

    private ImageView createImageView (String imagePath) {
        Image image = getImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private Image getImage (String imagePath) {
        File resource = new File(imagePath);
        return new Image(resource.toURI().toString());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return Arrays.asList(imagePathProperty(), imageWidthProperty(), imageHeightProperty());
    }

    public ImageView getImageView () {
    		//return createImageView(this.getImagePath());
        return imageView;
    }

    @Override
    public void update() {
        setImagePath(getImagePath());
        setImageHeight(getImageHeight());
        setImageWidth(getImageWidth());
    }
}
