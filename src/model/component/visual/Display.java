package model.component.visual;

import java.util.Collections;
import java.util.List;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

@Deprecated
public class Display implements IComponent {
	private final SingleProperty<String> imagePath, spritesheetPath;
	private int framePointer;
	private double width, height;
	private double offsetX, offsetY;
	private boolean isAnimated;

	public Display() {
		// TODO: change defaults
		this("resources/testing/RhonduSmithwick.JPG");
	}

	public Display(String imagePath) {
		this(imagePath, "resources/testing/RhonduSmithwick.JPG", 0.0, 0.0);
	}

	public Display(String imagePath, String spritesheetPath, double width, double height) {
		this(imagePath, spritesheetPath, width, height, 0.0, 0.0);
	}

	public Display(String imagePath, String spritesheetPath, double width, double height, double offsetX,
			double offsetY) {
		this.imagePath = new SingleProperty<>("ImagePath", imagePath);
		this.spritesheetPath = new SingleProperty<>("SpritesheetPath", spritesheetPath);
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	/**
	 * Get the imagePath property.
	 *
	 * @return imagePath string property
	 */
	public SimpleObjectProperty<String> imagePathProperty() {
		return imagePath.property1();
	}

	/**
	 * Get the imagePath String.
	 *
	 * @return imagePath String
	 */
	public String getImagePath() {
		return imagePathProperty().get();
	}

	/**
	 * Set the imagePath String.
	 *
	 * @param imagePath
	 *            String
	 */
	public void setImagePath(String imagePath) {
		this.imagePathProperty().set(imagePath);
	}

	/**
	 * Get the spritesheetPath Property.
	 *
	 * @return spritesheetPath Property
	 */
	public SimpleObjectProperty<String> spritesheetPathProperty() {
		return spritesheetPath.property1();
	}

	/**
	 * Get the spritesheetPath String.
	 *
	 * @return spritesheetPath String
	 */
	public String getSpritesheetPath() {
		return spritesheetPathProperty().get();
	}

	/**
	 * Set the spritesheetPath String.
	 *
	 * @param spritesheetPath
	 *            String
	 */
	public void setSpritesheetPath(String spritesheetPath) {
		this.spritesheetPathProperty().set(spritesheetPath);
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		return Collections.singletonList(imagePathProperty());
	}

}
