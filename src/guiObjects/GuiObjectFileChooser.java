package guiObjects;

import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import view.enums.DefaultStrings;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

public class GuiObjectFileChooser extends GuiObject {
	
	private Button setBundle;
	private ResourceBundle myPropertiesNames, myResources;
	private SimpleObjectProperty<String> property;
	
	
	@SuppressWarnings("unchecked")
	public GuiObjectFileChooser(String name, String resourceBundle, String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		myPropertiesNames= ResourceBundle.getBundle(language+DefaultStrings.PROPERTIES.getDefault());
		this.myResources= ResourceBundle.getBundle(language);
		setBundle = ButtonFactory.makeButton(myPropertiesNames.getString(name), e->getBundle());
		this.property=(SimpleObjectProperty<String>) property;
	}

	private void getBundle() {
		property.set( (FileUtilities.promptAndGetFile(FileUtilities.getImageFilters(),
				myResources.getString("ChooseFile"), DefaultStrings.ANIMATION_LOC.getDefault()).getPath()));
	}





	@Override
	public Object getCurrentValue() {
		return property.getValue();
	}

	@Override
	public Object getGuiNode() {
		return setBundle;
	}
}
