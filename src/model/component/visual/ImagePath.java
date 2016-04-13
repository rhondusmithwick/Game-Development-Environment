package model.component.visual;

import java.io.File;
import java.util.List;

import api.IComponent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.SingleProperty;

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
	private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();	private final String spriteName;
	

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
		this("resources/RhonduSmithwick.JPG", "Rhodu");
        ImageView imageView = new ImageView(new Image(new File(imagePath).toURI().toString()));
        imageViewProperty.set(imageView);
		this.imagePath = new SingleProperty<>("ImagePath", imagePath);
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
	public ImagePath(String spritesheetPath, String spriteName) {
		this.imagePath = new SingleProperty<>("SpritesheetPath", spritesheetPath);
		this.spriteName = spriteName;
		File resource = new File(spritesheetPath);
		Image image = new Image(resource.toURI().toString());
		this.imageViewProperty.set(new ImageView(image));

	}

	/**
	 * Get the imagePath property.
	 *
	 * @return impagePath string property
	 */



//	public SimpleObjectProperty<Double> imageWidthProperty() {
//		return imageSizeProperty.property1();
//	}
//
//	public SimpleObjectProperty<Double> imageHeightProperty() {
//		return imageSizeProperty.property2();
//	}

	public double getImageWidth() {
		return imageViewProperty.get().getBoundsInParent().getWidth();
	}

	public void setImageWidth(double imageWidth) {
		this.imageViewProperty.get().setFitWidth(imageWidth);
	}

	public double getImageHeight() {
		return imageViewProperty.get().getBoundsInParent().getHeight();
	}

	public void setImageHeight(double imageHeight) {
		this.imageViewProperty.get().setFitHeight(imageHeight);
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		return null;
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