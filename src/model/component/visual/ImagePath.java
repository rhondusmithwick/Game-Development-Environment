package model.component.visual;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.google.common.reflect.Reflection;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.component.movement.Position;
import utility.SingleProperty;
import utility.TwoProperty;

/**
 * Component to hold an imagePath.
 *
 * @author Rhondu Smithwick
 */
public class ImagePath implements IComponent {

	/**
	 * The singleProperty.
	 */
	private final SingleProperty<String> imagePathProperty, spriteSheetPath;
	private final TwoProperty<Double, Double> imageSizeProperty;
	private int framePointer;
	//private final Rectangle2D viewport;
	private final boolean isAnimated;
	private final double frameDuration, totalDuration;
	private static final String PROPERTIES_DIR = "templates.";
	private final String spriteName;
	private ImageView imageView;

	public ImagePath() {
		this("resources/RhonduSmithwick.JPG");
	}

	/**
	 * Construct with no animation.
	 *
	 * @param imagePath
	 *            starting value
	 */
	public ImagePath(String imagePath) {
		this(imagePath, 0.0, 0.0, "resources/RhonduSmithwick.JPG", false, 0.0, 0.0, "Rhondu");
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
	 * @param spriteSheetPath
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
	public ImagePath(String imagePath, double imageWidth, double imageHeight, String spriteSheetPath,
			boolean isAnimated, double frameDuration, double totalDuration, String spriteName) {
		this.imagePathProperty = new SingleProperty<>("ImagePath", imagePath);
		this.imageView = createImage(imagePath);
		this.imageSizeProperty = new TwoProperty<>("ImageWidth", imageWidth, "ImageHeight", imageHeight);
		this.spriteSheetPath = new SingleProperty<>("SpriteSheetPath", spriteSheetPath);
		this.isAnimated = isAnimated;
		this.frameDuration = frameDuration;
		this.totalDuration = totalDuration;
		this.spriteName = spriteName;
	}

	/**
	 * Get the imagePath property.
	 *
	 * @return impagePath string property
	 */
	public SimpleObjectProperty<String> imagePathProperty() {
		return imagePathProperty.property1();
	}
	
	public SimpleObjectProperty<String> spriteSheetProperty() {
		return spriteSheetPath.property1();
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

	public double getFrameDuration() {
		return this.frameDuration;
	}

	public double getTotalDuration() {
		return this.totalDuration;
	}

	public String getSpriteSheet(){
		return spriteSheetProperty().get();
	}

	public String getSpriteProperties() {
		return PROPERTIES_DIR + this.spriteName;
	}

	private ImageView createImage(String path) {
		Image image = new Image(new File(path).toURI().toString());
		ImageView imageView = new ImageView(image);
		return imageView;
	}
	
	public ImageView getImageView() {
		return this.imageView;
	}

}
