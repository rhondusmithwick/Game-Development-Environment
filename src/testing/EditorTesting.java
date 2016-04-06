package testing;

import api.Editor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import api.EditorEntity;

public class EditorTesting extends Application {
	private Stage myStage;

	/**
	 * Sets up a stage to launch our window and initializes the splash screen.
	 * @param stage
	 */

	public void start (Stage stage) {

		myStage = stage;
		myStage.setTitle("main screen");
		Editor editorEntity = (Editor) new EditorEntity();
		Scene scene = new Scene(editorEntity.getGroup());
		myStage.setScene(scene);
		myStage.show();
	}

	/**
	 * Launches our program.
	 * @param args
	 */

	public static void main (String[] args) {
		launch(args);
	}

}

