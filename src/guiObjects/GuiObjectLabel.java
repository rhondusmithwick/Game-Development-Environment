package guiObjects;


import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import view.enums.DefaultStrings;


public class GuiObjectLabel extends GuiObject{
	private Label myLabel;
	private ResourceBundle myResources, myPropertiesNames;
	
	public GuiObjectLabel(String name, String resourceBundle,String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		this.myPropertiesNames=ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
		myResources = ResourceBundle.getBundle(language);
		myLabel = new Label(myPropertiesNames.getString(name) + myResources.getString("true"));

	}



	@Override
	public Object getCurrentValue() {
		return myLabel.getText();
	}



	@Override
	public Object getGuiNode() {
		return myLabel;
	}



}