package gui;
import javafx.beans.property.SimpleObjectProperty;


/**
 * This is part of my masterpiece code. I reused this from my CellSociety project. This class introduces polymorphism and abstraction to the GUI elements. 
 * This Factory class creates GuiObjects that are linked to an agent. 
 * @author Melissa Zhang
 *
 */


public class GuiObjectFactory {
	private static final String GUI_RESOURCES = "guiComponents";
	private String myLanguage;

	public GuiObjectFactory(String language){
		myLanguage=  language;
	}
	public GuiObject createNewGuiObject(String type, SimpleObjectProperty<?> property, Object object){

		switch(type){
			case("Attack"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}
			case("Defense"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}
			case("Beta"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}
			case("Health"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}
			case("Lives"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}
			case("Mass"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}
			case("Orientation"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}
			case("Score"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}
			case("Speed"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, property, object);
			}

		}
		
		return null;
	}
}


/*package gui;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import api.ISerializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

*//**
 * This is part of my masterpiece code. I reused this from my CellSociety project. This class introduces polymorphism and abstraction to the GUI elements. 
 * This Factory class creates GuiObjects that are linked to an agent. 
 * @author Melissa Zhang
 *
 *//*

public class GuiObjectFactory {
	private static final String GUI_RESOURCES = "gui";

	public GuiObject createNewGuiObject(String className, String name, EventHandler<ActionEvent> event, Property<?> property, ListProperty<?> list, ISerializable serial){
		GuiObject guiObject = null;
		try{
		String guiObjectClass = className;
		String guiObjectClassName = "gui." + guiObjectClass;
		Class<?> c = Class.forName(guiObjectClassName);
		Constructor<?> cons = c.getConstructor(String.class, String.class, EventHandler.class, Property.class,ListProperty.class,ISerializable.class);
		guiObject = (GuiObject) cons.newInstance(name, GUI_RESOURCES, event, property, list, serial);
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException| NoSuchMethodException|SecurityException|IllegalArgumentException|InvocationTargetException e) {
	        System.out.println("Unable to create GuiObject");
		}
		return guiObject;
	}

}

switch(type){
case("Position"):{
	return new GuiObjectInputBox(type, GUI_RESOURCES,null, ((Position) serial).xProperty());
}
case("Velocity"):{
	return new GuiObjectSlider(type, GUI_RESOURCES, null, ((Velocity) serial).speedProperty());
}
}*/