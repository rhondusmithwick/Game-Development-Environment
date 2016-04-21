
package testing.demo;

import enums.GUISize;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.View;

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

		View view = new View();
		Pane pane = view.getPane();
		Scene scene = new Scene(pane, 500, 500);
		stage.setScene(scene);
		stage.show();
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