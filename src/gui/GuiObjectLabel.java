package gui;


import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;


public class GuiObjectLabel extends GuiObject{
	private Label myLabel;
	private ResourceBundle myResources;
	
	public GuiObjectLabel(String name, String resourceBundle,String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		myResources = ResourceBundle.getBundle(language);
		myLabel = new Label(myResources.getString(name) + myResources.getString("true"));

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