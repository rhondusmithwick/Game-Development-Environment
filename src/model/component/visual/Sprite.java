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
import java.util.Arrays;
import java.util.List;

/**
 * Component to hold an imagePath.
 *
 * @author Rhondu Smithwick, Melissa Zhang, Bruna Liborio
 */
public class Sprite implements IComponent {

	/**
	 * The singleProperty.
	 */
	private final SingleProperty<String> imagePathProperty;
	private final TwoProperty<Double, Double> imageSizeProperty;
	private transient ImageView imageView;
	private int zLevel = 0;

	public Sprite() {
		this("resources/testing/RhonduSmithwick.JPG");
	}

	/**
	 * Construct with no animation.
	 *
	 * @param imagePath
	 *            starting value
	 */
	public Sprite(String imagePath) { // TODO: place default in resource file
		this(imagePath, 0.0, 0.0);
	}

	/**
	 * Construct with starting values.
	 *
	 * @param imagePath
	 *            String path to image
	 * @param imageWidth
	 *            width of image
	 * @param imageHeight
	 *            height of image
	 * @param imagePath
	 *            String path to spritesheet
	 */
	public Sprite(String imagePath, double imageWidth, double imageHeight){
		this.imagePathProperty = new SingleProperty<>("ImagePath", imagePath);
		this.imageSizeProperty = new TwoProperty<>("ImageWidth", imageWidth, "ImageHeight", imageHeight);
		this.imageView = this.createImageView(imagePath);


	}

	private ImageView createImageView(String imagePath) {
		File resource = new File(imagePath);
		Image image = new Image(resource.toURI().toString());
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(this.getImageWidth());
		imageView.setFitHeight(this.getImageHeight());
		return imageView;
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
		this.imagePathProperty().set(imagePath);
		File resource = new File(imagePath);
		Image image = new Image(resource.toURI().toString());
		this.imageView.setImage(image);

	}

	public SimpleObjectProperty<Double> imageWidthProperty() {
		return imageSizeProperty.property1();
	}

	public SimpleObjectProperty<Double> imageHeightProperty() {
		return imageSizeProperty.property2();
	}

	public double getImageWidth() {
		return this.imageWidthProperty().get();
	}

	public void setImageWidth(double imageWidth) {
		this.imageWidthProperty().set(imageWidth);
		imageView.setFitWidth(imageWidthProperty().get());
	}

	public double getImageHeight() {
		return this.imageHeightProperty().get();
	}

	public void setImageHeight(double imageHeight) {
		this.imageHeightProperty().set(imageHeight);
		imageView.setFitHeight(imageHeightProperty().get());
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		return Arrays.asList(imagePathProperty(), imageWidthProperty(), imageHeightProperty());
	}


	public ImageView getImageView() { 
		return imageView;
	}


	/**
	 * Sets the z-layer order.
	 *
	 * @param z
	 *            the z-layer order (1=>send to back, 1=>send to front)
	 */
	public void setZLevel(int z) {
		this.zLevel = z;
	}

	/**
	 * Gets the z-layer order.
	 *
	 * @return the z-layer order (1=>send to back, 1=>send to front)
	 */
	public int getZLevel() {
		return this.zLevel;
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.imageView = this.createImageView(getImagePath());
	}


}