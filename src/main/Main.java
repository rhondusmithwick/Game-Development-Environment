
package main;

import enums.GUISize;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.editor.EditorEnvironment;

public class Main extends Application{

		private Stage myStage;
		private EditorEnvironment myEditorEnvironment;
		private Scene myScene;

		/**
		 * Sets up a stage to launch our window and initializes the splash screen.
		 * @param stage
		 */

		public void start (Stage stage) {
			myStage = stage;
			myStage.setTitle("VOOGA");
			myStage.setWidth(GUISize.MAIN_SIZE.getSize());
			myStage.setHeight(GUISize.MAIN_SIZE.getSize());
			Vooga vooga = new Vooga(myStage);
			myStage.setScene(vooga.init());
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