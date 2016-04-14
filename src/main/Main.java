
package main;

import enums.GUISize;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

		private Stage myStage;

		/**
		 * Sets up a stage to launch our window and initializes the splash screen.
		 * @param stage
		 */

		@Override
		public void start (Stage stage) {
			myStage = stage;
			myStage.setTitle("VOOGA");
			//myStage.setWidth(GUISize.MAIN_SIZE.getSize());
			//myStage.setHeight(GUISize.MAIN_SIZE.getSize());
			myStage.setWidth(GUISize.TWO_THIRDS_OF_SCREEN.getSize());
			myStage.setHeight(GUISize.HEIGHT_MINUS_TAB.getSize());
			Vooga vooga = new Vooga(myStage);
			vooga.init();
		}

		/**
		 * Launches our program.
		 * @param args
		 */

		public static void main (String[] args) {
			launch(args);
		}

}