package guiObjects;

import java.util.ResourceBundle;
import view.enums.DefaultStrings;
import voogasalad.util.reflection.Reflection;


/**
 * This is part of my masterpiece code. I reused this from my CellSociety project. This class introduces polymorphism and abstraction to the GUI elements. 
 * This Factory class creates GuiObjects that are linked to an agent. 
 * @author Melissa Zhang, Ben Zhang, Cali Nelson
 *
 */


public class GuiObjectFactory {
	private ResourceBundle myBundle = ResourceBundle.getBundle(DefaultStrings.GUI_FACTORY.getDefault());

	public GuiObject createNewGuiObject( Object... args){ 
		if(myBundle.containsKey((String) args[0])) {
			return (GuiObject) Reflection.createInstance(myBundle.getString((String) args[0]), args);
		}
		return null;
	}
}