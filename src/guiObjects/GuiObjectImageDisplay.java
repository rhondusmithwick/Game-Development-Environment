package guiObjects;

import java.io.File;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;
/**
 * 
 * @author calinelson
 *
 */

public class GuiObjectImageDisplay extends GuiObjectFileGetter {
	
	private ImageView preview;
	private Button setImage;
	private ResourceBundle myPropertiesNames, myResources;
	private SimpleObjectProperty<String> property;
	
	
	@SuppressWarnings("unchecked")
	public GuiObjectImageDisplay(String name, String resourceBundle, String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		myPropertiesNames= ResourceBundle.getBundle(language+DefaultStrings.PROPERTIES.getDefault());
		this.myResources= ResourceBundle.getBundle(language);

		this.property=(SimpleObjectProperty<String>) property;
		this.preview=new ImageView();
		setImage = ButtonFactory.makeButton(myPropertiesNames.getString(name), e->changeValue(this.property, myResources, DefaultStrings.GUI_IMAGES.getDefault(), FileUtilities.getImageFilters()));
		setFile(new File(this.property.getValue()), this.property);
	}



	@Override
	protected void setPreview(File file){
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
