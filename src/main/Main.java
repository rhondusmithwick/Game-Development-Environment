package main;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
		private ResourceBundle myResources;

		private Stage myStage;
		
		/**
		 * Sets up a stage to launch our window and initializes the splash screen.
		 * @param stage
		 */

		public void start (Stage stage) {
			myResources = ResourceBundle.getBundle("authoring");
			myStage = stage;
			myStage.setTitle(myResources.getString("title"));
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
