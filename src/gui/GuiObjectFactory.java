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
	private static final String GUI_RESOURCES = "guiComponents";
	private String myLanguage;

	public GuiObjectFactory(String language){
		myLanguage=  language;
	}
	@SuppressWarnings("unchecked")
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
			case("ImagePath"):{
				return new GuiObjectImageDisplay(type, GUI_RESOURCES, myLanguage, (Property<String>) property, object);
			}

		}
		
		return null;
	}
}