package guiObjects;

import java.io.File;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import utility.FilePathRelativizer;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

public class GuiObjectImageDisplay extends GuiObject {
	
	private ImageView preview;
	private Button setImage;
	private ResourceBundle myPropertiesNames, myResources;
	private SimpleObjectProperty<String> property;
	
	
	@SuppressWarnings("unchecked")
	public GuiObjectImageDisplay(String name, String resourceBundle, String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		myPropertiesNames= ResourceBundle.getBundle(language+DefaultStrings.PROPERTIES.getDefault());
		this.myResources= ResourceBundle.getBundle(language);
		setImage = ButtonFactory.makeButton(myPropertiesNames.getString(name), e->changeImage());
		this.property=(SimpleObjectProperty<String>) property;
		this.preview=new ImageView();
		setImage(new File(this.property.getValue()));
	}

	private void changeImage(){
		File file = getImage();
		setImage(file);
	}

	private File getImage() {
		return FileUtilities.promptAndGetFile(FileUtilities.getImageFilters(),
				myResources.getString("ChooseFile"), DefaultStrings.GUI_IMAGES.getDefault());
	}

	private void setImage(File file) {
		if(file==null){
			return;
		}
		property.setValue(FilePathRelativizer.relativize(file.getPath()));
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
		HBox container = new HBox(GUISize.GUI_IM_DISP.getSize());
		container.getChildren().addAll(preview, setImage);
		return container;
	}

}
