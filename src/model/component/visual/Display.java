package model.component.visual;

import api.IComponent;
import javafx.scene.image.Image;
import utility.SingleProperty;

public class Display implements IComponent {
	private final SingleProperty<String> imagePath;
	private int framePointer;
	private double width, height;
	private double offsetX, offsetY;

	public Display() {
		// TODO: change defaults
		this("resources/RhonduSmithwick.JPG", 20.0);

	}

	public Display(String imagePath, double width) {
		this.imagePath = new SingleProperty<>("imagePath", imagePath);
		this.width = width;
		Image img = new Image(imagePath);
		this.height = img.getHeight(); // assuming img is a linear filmstrip
		// TODO
		this.offsetX = 0.0;
		this.offsetY = 0.0;
	}

}
