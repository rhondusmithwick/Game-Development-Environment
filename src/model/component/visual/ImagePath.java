package model.component.visual;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.SingleProperty;
import utility.TwoProperty;

/**
 * Component to hold an imagePath.
 *
 * @author Rhondu Smithwick
 */
public class ImagePath implements IComponent {

	private static final String PROPERTIES_DIR = "templates.";
	/**
	 * The singleProperty.
	 */
	private final SingleProperty<String> imagePathProperty, spritesheetPath;
	private final TwoProperty<Double, Double> imageSizeProperty;
	private ImageView imageView;
	private final String spriteName;

	public ImagePath() {
		this("resources/RhonduSmithwick.JPG");

	}

	/**
	 * Construct with no animation.
	 *
	 * @param imagePath
	 *            starting value
	 */

	public ImagePath(String imagePath) { // TODO: place default in resource file
		this(imagePath, 0.0, 0.0, "resources/RhonduSmithwick.JPG", "Rhodu");
        imageView = new ImageView(new Image(new File(imagePath).toURI().toString()));
	}

	// TODO: IMPORTANT NOTE: I forgot to account for columns!
	/**
	 * Construct with starting values.
	 *
	 * @param imagePath
	 *            String path to image
	 * @param imageWidth
	 *            width of image
	 * @param imageHeight
	 *            height of image
	 * @param spritesheetPath
	 *            String path to spritesheet
	 * @param width
	 *            width of viewport
	 * @param height
	 *            height of viewport
	 * @param offsetX
	 *            offset in x-direction
	 * @param offsetX
	 *            offset in y-direction
	 */
	public ImagePath(String imagePath, double imageWidth, double imageHeight, String spritesheetPath, String spriteName) {
		this.imagePathProperty = new SingleProperty<>("ImagePath", imagePath);
		this.imageSizeProperty = new TwoProperty<>("ImageWidth", imageWidth, "ImageHeight", imageHeight);
		this.spritesheetPath = new SingleProperty<>("SpritesheetPath", spritesheetPath);
		this.spriteName = spriteName;
		
		
		File resource = new File(spritesheetPath);
		Image image = new Image(resource.toURI().toString());
		this.imageView = new ImageView(image);
	

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
		System.out.println("Set width to: " + imageWidth);
	}

	public double getImageHeight() {
		return this.imageHeightProperty().get();
	}

	public void setImageHeight(double imageHeight) {
		this.imageHeightProperty().set(imageHeight);
		System.out.println("Set height to: " + imageHeight);
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		return Arrays.asList(imagePathProperty(), imageWidthProperty(), imageHeightProperty());
	}


	public ImageView getImageView() {											
		return imageView;
	}



	public String getSpriteSheet(){
		return this.spritesheetPath.property1().get();
	}
	
	public String getSpriteName(){
		return this.spriteName;
	}

	public String getSpriteProperties() {
		return PROPERTIES_DIR + this.spriteName;
	}



}
