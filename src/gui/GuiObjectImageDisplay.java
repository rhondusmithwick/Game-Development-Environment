package gui;

import java.io.File;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import view.Utilities;
import view.enums.GUISize;

public class GuiObjectImageDisplay extends GuiObject {
	
	private ImageView preview;
	private Button setImage;
	private ResourceBundle myResources;
	private SimpleObjectProperty<String> property;
	
	
	@SuppressWarnings("unchecked")
	public GuiObjectImageDisplay(String name, String resourceBundle, String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		myResources= ResourceBundle.getBundle(language);
		setImage = Utilities.makeButton(myResources.getString(name), e->changeImage());
		this.property=(SimpleObjectProperty<String>) property;
		this.preview=new ImageView();
		setImage(new File(this.property.getValue()));
	}

	private void changeImage(){
		File file = getImage();
		setImage(file);
	}

	private File getImage() {
		return Utilities.promptAndGetFile(Utilities.getImageFilters(), myResources.getString("ChooseFile"));
		
	}

	private void setImage(File file) {
		if(file==null){
			return;
		}
		property.setValue(file.getPath());
		preview.setImage(new Image(file.toURI().toString()));
		preview.setFitHeight(GUISize.PREVIEW_SIZE.getSize());
		preview.setPreserveRatio(true);
	}



	@Override
	public Object getCurrentValue() {
		return property.getValue();
	}

	@Override
	public Object getGuiNode() {
		VBox container = new VBox(GUISize.GUI_IM_DISP.getSize());
		container.getChildren().addAll(preview, setImage);
		return container;
	}

}
