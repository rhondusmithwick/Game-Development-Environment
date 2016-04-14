package gui;

import java.util.ResourceBundle;

import javafx.scene.control.Control;

public abstract class GuiObject {
	private String objectName;
	private ResourceBundle myResources;
	public GuiObject(String name, String resourceBundle) {
		this.objectName = name;
		this.myResources = ResourceBundle.getBundle(resourceBundle);
	}
	
	public String getObjectName(){
		return objectName;
	}


	public ResourceBundle getResourceBundle(){
		return myResources;
	}


	public abstract Object getCurrentValue();
	public abstract Control getControl();
	public abstract Object getGuiNode();
		

	
}