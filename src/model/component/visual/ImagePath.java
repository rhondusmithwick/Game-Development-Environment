package model.component.visual;

import java.util.Collections;
import java.util.List;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

/**
 * Component to hold an imagePath.
 *
 * @author Rhondu Smithwick
 */
public class ImagePath implements IComponent {

	/**
	 * The singleProperty.
	 */
	private final SingleProperty<String> imagePathProperty, spritesheetPath;
//	private final int framePointer;
	private double imageWidth, imageHeight, width, height, offsetX, offsetY;
	private final boolean isAnimated;

	public ImagePath() {
		this("resources/RhonduSmithwick.JPG");
	}
	
	/**
	 * Construct with no animation.
	 *
	 * @param imagePath starting value
	 */
	public ImagePath(String imagePath) {
		this(imagePath, 0.0, 0.0,
				"resources/RhonduSmithwick.JPG", 0.0, 0.0, 0.0, 0.0, false);
	}
	
	/**
	 * Construct with starting values.
	 *
	 * @param imagePath String path to image
	 * @param imageWidth width of image
	 * @param imageHeight height of image
	 * @param spritesheetPath String path to spritesheet
	 * @param width width of viewport
	 * @param height height of viewport
	 * @param offsetX offset in x-direction
	 * @param offsetX offset in y-direction
	 */
	public ImagePath(String imagePath, double imageWidth, double imageHeight,
			String spritesheetPath, double width, double height, double offsetX, double offsetY, boolean isAnimated) {
		this.imagePathProperty = new SingleProperty<>("ImagePath", imagePath);
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.spritesheetPath = new SingleProperty<>("SpritesheetPath", spritesheetPath);
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.isAnimated = isAnimated;
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
	
	public double getImageWidth() {
		return this.imageWidth;
	}
	
	public void setImageWidth(double imageWidth) {
		this.imageWidth = imageWidth;
	}

	public double getImageHeight() {
		return this.imageHeight;
	}
	
	public void setImageHeight(double imageHeight) {
		this.imageHeight = imageHeight;
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		return Collections.singletonList(imagePathProperty());
	}
}
