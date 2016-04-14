
package testing.demo;

import enums.GUISize;
import javafx.application.Application;
import javafx.stage.Stage;

public class DemoMain extends Application {

	private Stage myStage;

	/**
	 * Sets up a stage to launch our window and initializes the splash screen.
	 * 
	 * @param stage
	 */
	public void start(Stage stage) {
		myStage = stage;
		myStage.setTitle("VOOGA");
		myStage.setWidth(GUISize.MAIN_SIZE.getSize());
		myStage.setHeight(GUISize.MAIN_SIZE.getSize());
		View view = new View(myStage);
	}

	/**
	 * Launches our program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}