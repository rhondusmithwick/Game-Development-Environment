package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class Authoring {

		private static Scene myScene;

		/**
		 * Constructor that takes in the language choice of the user.
		 * @param language that the user chooses the program from.
		 */
		public Authoring(String lang){
			
		}

		/**
		 * Initializes the scene which is displayed in the window.
		 * @return the splash screen scene
		 */
		
		public static Scene init(ReadOnlyDoubleProperty height, ReadOnlyDoubleProperty width){
			GridPane display = createDisplay();
			display.prefHeightProperty().bind(height);
			display.prefWidthProperty().bind(width);
			myScene = new Scene(display,500, 500);
			return myScene;
		}
		
		/**
		 * Creates the display independently of initializing the scene.
		 * @return BorderPane in which the contents are the splash screen
		 */

		private static GridPane createDisplay() {
			GridPane display = new GridPane();
			display.setHgap(10);
			display.setVgap(10);
			// add children here 
			return display;
		}

	}

