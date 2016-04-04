package model.vooga;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

		private Stage myStage;

		/**
		 * Sets up a stage to launch our window and initializes the splash screen.
		 * @param stage
		 */

		public void start (Stage stage) {

			myStage = stage;
			myStage.setTitle("main screen");

			SplashScreen splash = new SplashScreen(myStage);
			myStage.setScene(splash.init());
			myStage.setResizable(false);
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
