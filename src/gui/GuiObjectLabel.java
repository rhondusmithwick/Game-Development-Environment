package gui;

import java.util.Observable;
import javafx.scene.control.Label;


public class GuiObjectLabel extends GuiObject{
	private Label myLabel;
	public GuiObjectLabel(String name, String resourceBundle, Observable obs) {
		super(name, resourceBundle, obs);
	}

	@Override
	public Object createObjectAndReturnObject() {
		myLabel = new Label(getResourceBundle().getString(getObjectName()+ "LABEL"));
		
		return myLabel;
	}



}