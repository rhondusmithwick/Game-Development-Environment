package api;

import javafx.scene.layout.Pane;

public interface IEditor { //This will be an abstract class that all editors will extend.
	
    void loadDefaults();

    Pane getPane(); // This is a method all subclasses should implement.

    void populateLayout(); // This is a method all subclasses should implement

}
