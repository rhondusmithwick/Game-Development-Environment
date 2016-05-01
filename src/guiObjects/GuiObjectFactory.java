package guiObjects;

import java.util.ResourceBundle;
import view.enums.DefaultStrings;
import voogasalad.util.reflection.Reflection;


/**
 * This Factory class creates GuiObjects that are linked to an agent. 
 * @author Melissa Zhang, Ben Zhang, Cali Nelson
 *
 */


public class GuiObjectFactory {
	private ResourceBundle myBundle = ResourceBundle.getBundle(DefaultStrings.GUI_FACTORY.getDefault());

	/**
	 * creates a gui object of type args[0] and passes it args[1] to end
	 * @param args arguments for constructor
	 * @return guiobjectx
	 */
	public GuiObject createNewGuiObject( Object... args){ 
		if(myBundle.containsKey((String) args[0])) {
			return (GuiObject) Reflection.createInstance(myBundle.getString((String) args[0]), args);
		}
		return null;
	}
}