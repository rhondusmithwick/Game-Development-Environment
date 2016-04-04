package api;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface IEditor { //This will be an abstract class that all editors will extend.
	

    void loadDefaults();

    Group getGroup(); // This is a method all subclasses should implement.

    void populateLayout(Pane pane); // This is a method all subclasses should implement

}
