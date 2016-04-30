package guiObjects;

import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

public class GuiObjectBundleChooser extends GuiObject {
	
	private Button setBundle;
	private ResourceBundle myPropertiesNames, myResources;
	private SimpleObjectProperty<String> property;
	private TextField text = new TextField();
	
	
	@SuppressWarnings("unchecked")
	public GuiObjectBundleChooser(String name, String resourceBundle, String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		myPropertiesNames= ResourceBundle.getBundle(language+DefaultStrings.PROPERTIES.getDefault());
		this.myResources= ResourceBundle.getBundle(language);
		setBundle = ButtonFactory.makeButton(myPropertiesNames.getString(name), e->getBundle());
		this.property=(SimpleObjectProperty<String>) property;
		text.setEditable(false);
	}

	private void getBundle() {
		property.set( (FileUtilities.promptAndGetFile(FileUtilities.getPropertiesFilters(),
				myResources.getString("ChooseFile"), DefaultStrings.ANIMATION_LOC.getDefault()).getPath()));
		text.setText(property.get());
	}





	@Override
	public Object getCurrentValue() {
		return property.getValue();
	}

	@Override
	public Object getGuiNode() {
		HBox hbox = new HBox(GUISize.ENTITY_EDITOR_PADDING.getSize());
		hbox.getChildren().addAll(text, setBundle);
		return hbox;
	}
}
