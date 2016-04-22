package gui;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;
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
	public GuiObject createNewGuiObject(String type, SimpleObjectProperty<?> property, Object object){ 
		if(myBundle.containsKey(type)) {
			String obj = myBundle.getString(type);			
			try {
				Class<?> newClass = Class.forName(obj);
				Constructor<?> constructor = newClass.getConstructor(String.class, String.class, String.class, SimpleObjectProperty.class, Object.class);
				 return (GuiObject) constructor.newInstance(type, GUI_RESOURCES, myLanguage, property, object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		return null;
	}
}