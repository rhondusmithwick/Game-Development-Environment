package api;

import java.util.List;
import java.util.Properties;

import entitytesting.Component;

public interface EntityEditorInterface {
	
	List<Component> getComponents();
	
	Component editComponent(Component myComp); // Class specific method, might not be public 
	
	Component getSingleComponent(Component myComp); // Class specific method, might not be public 
	
	void show(); //add the editors contents to a tab in the tab pane and display it to the user
	void close(); //close editor tab 
	
	void writeToFile(String filename);
	
}
