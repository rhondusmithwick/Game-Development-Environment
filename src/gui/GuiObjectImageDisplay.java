package gui;

import java.io.File;
import java.util.ResourceBundle;

import enums.GUISize;
import javafx.beans.property.Property;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import view.Utilities;

public class GuiObjectImageDisplay extends GuiObject {
	
	private ImageView preview;
	private Button setImage;
	private ResourceBundle myResources;
	private Property<String> property;
	
	
	public GuiObjectImageDisplay(String name, String resourceBundle, String language, Property<String> property, Object object) {
		super(name, resourceBundle);
		myResources= ResourceBundle.getBundle(language);
		setImage = Utilities.makeButton(name, e->changeImage());
		this.property=property;
		this.preview=new ImageView();
		setImage(new File(property.getValue()));
	}

	private void changeImage(){
		File file = getImage();
		setImage(file);
	}

	private File getImage() {
		return Utilities.promptAndGetFile(Utilities.getImageFilters(), myResources.getString("ChooseFile"));
		
	}

	private void setImage(File file) {
		property.setValue(file.getPath());
		preview.setImage(new Image(file.toURI().toString()));
		preview.setFitHeight(100);
		preview.setPreserveRatio(true);
	}



	@Override
	public Object getCurrentValue() {
		return property.getValue();
	}

	@Override
	public Control getControl() {
		return null;
	}

	@Override
	public Object getGuiNode() {
		HBox container = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
		container.getChildren().addAll(preview, setImage);
		return container;
	}

}