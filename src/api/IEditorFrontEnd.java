package api;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface IEditorFrontEnd { //This will be an abstract class that all editors will extend.
	
	Button createButton();
	
	void writeToFile(String fileName);
	
	void readFromFile(String fileName);
	
	void loadDefaults();
	
	Group getGroup(); // This is a method all subclasses should implement. 
	
	void populateLayout(Pane pane); // This is a method all subclasses should implement 
	
}
