package model.component.visual;

import java.io.File;


import java.util.Arrays;

import java.util.List;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Rectangle2D;
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

	private static final String PROPERTIES_DIR = "spriteProperties.";
	/**
	 * The singleProperty.
	 */
	private SingleProperty<String> imagePath;
	private SimpleObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();	
	private String spriteName;
	

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
		ImageView imageView = new ImageView(new Image(new File(imagePath).toURI().toString()));
        imageViewProperty.set(imageView);
		this.imagePath = new SingleProperty<>("ImagePath", imagePath);
		this.imageViewProperty.get().setPreserveRatio(true);
	}

	/**
	 * Construct with starting values.
	 *@param spriteName
	 *			String name of sprite corresponding to properties file
	 * @param spritesheetPath
	 *            String path to spritesheet
	 */

	public ImagePath(String spritesheetPath, String spriteName) {
		this.imagePath = new SingleProperty<>("SpritesheetPath", spritesheetPath);
		this.spriteName = spriteName;
		File resource = new File(spritesheetPath);
		Image image = new Image(resource.toURI().toString());
		this.imageViewProperty.set(new ImageView(image));
		this.imageViewProperty.get().setPreserveRatio(true);
	}

	/**
	 * Get the imagePath property.
	 *
	 * @return impagePath string property
	 */

	public double getImageWidth() {
		return imageViewProperty.get().getBoundsInParent().getWidth();
	}

	public void setImageWidth(double imageWidth) {
		this.imageViewProperty.get().setFitWidth(imageWidth);
		System.out.println("Image height set to: " + this.getImageHeight());
	}

	public double getImageHeight() {
		return imageViewProperty.get().getBoundsInParent().getHeight();
	}

	public void setImageHeight(double imageHeight) {
		this.imageViewProperty.get().setFitHeight(imageHeight);
		System.out.println("Image height set to: " + this.getImageWidth());
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		return Arrays.asList(imageViewProperty);
	}


	public ImageView getImageView() {	
		return imageViewProperty.get();
	}

	public String getSpriteSheet(){
		return this.imagePath.property1().get();
	}
	
	public String getSpriteName(){
		return this.spriteName;
	}

	public String getSpriteProperties() {
		return PROPERTIES_DIR + this.spriteName;

	}

	public String getImagePath() {
		return imagePath.property1().get();
	}

}