package gui;

import java.util.ResourceBundle;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;


/**
 * This is part of my masterpiece code. I reused this from my CellSociety project. This class introduces polymorphism and abstraction to the GUI elements. 
 * This Factory class creates GuiObjects that are linked to an agent. 
 * @author Melissa Zhang
 *
 */


public class GuiObjectFactory {
	private static final String GUI_RESOURCES = "guiObject/guiComponents";
	private static final String GUI_FACTORY = "guiObject/GuiObjectFactory";
	private String myLanguage;
	private ResourceBundle myBundle = ResourceBundle.getBundle(GUI_FACTORY);

	public GuiObjectFactory(String language){
		myLanguage=  language;
	}
	@SuppressWarnings("unchecked")
	public GuiObject createNewGuiObject(String type, SimpleObjectProperty<?> property, Object object){ 
		if(myBundle.containsKey(type)) {
			String obj = myBundle.getString(type);
			switch(obj){
				case("GuiObjectSlider"):{
					return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
				}
				case("GuiObjectImageDisplay"):{
					return new GuiObjectImageDisplay(type, GUI_RESOURCES, myLanguage, (Property<String>) property, object);
				}

			}
		}
		return null;
	}
}