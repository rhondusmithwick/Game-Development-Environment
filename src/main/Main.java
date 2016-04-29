
package main;

import javafx.application.Application;
import javafx.stage.Stage;
import utility.FilePathRelativizer;
import view.enums.GUISize;

import java.net.URISyntaxException;

public class Main extends Application{

		private static final String RELATIVIZER_TESTER = "/Users/rhondusmithwick/Documents/Classes/CS308/voogasalad/resources/spriteSheets";
		private Stage myStage;

		/**
		 * Sets up a stage to launch our window and initializes the splash screen.
		 * @param stage
		 */

		@Override
		public void start (Stage stage) {
			try {
				String relative = FilePathRelativizer.getInstance().relativize(RELATIVIZER_TESTER);
				System.out.println(relative);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			myStage = stage;
			myStage.setTitle("VOOGA");
			myStage.setWidth(GUISize.MAIN_SIZE.getSize());
			myStage.setHeight(GUISize.MAIN_SIZE.getSize());
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