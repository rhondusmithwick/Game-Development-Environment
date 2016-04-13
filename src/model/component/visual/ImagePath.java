package model.component.visual;

import java.io.File;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

import java.util.List;
import api.IComponent;
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
	private ImageView imageView;
	//private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();	
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
		this("resources/RhonduSmithwick.JPG", "Rhodu");
        imageView = new ImageView(new Image(new File(imagePath).toURI().toString()));
        imageView.setPreserveRatio(true);
       // imageViewProperty.set(imageView);
		this.imagePath = new SingleProperty<>("ImagePath", imagePath);
		//this.imageViewProperty.get().setPreserveRatio(true);
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
		this.imageView = (new ImageView(image));
		imageView.setPreserveRatio(true);
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
		return imageView.getBoundsInParent().getWidth();
	}

	public void setImageWidth(double imageWidth) {
		imageView.setFitWidth(imageWidth);
		System.out.println("Image height set to: " + this.getImageHeight());
	}

	public double getImageHeight() {
		return imageView.getBoundsInParent().getHeight();
	}

	public void setImageHeight(double imageHeight) {
		imageView.setFitHeight(imageHeight);
		System.out.println("Image height set to: " + this.getImageWidth());
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		return imagePath.getProperties();
	}


	public ImageView getImageView() {	
		return imageView;
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


	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		// reconstruct imageView
		File resource = new File(this.imagePathProperty().get());
		Image image = new Image(resource.toURI().toString());
		this.imageView = new ImageView(image);
	}

}