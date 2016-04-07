package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;

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
			TabPane display = new TabPane();
			display.prefHeightProperty().bind(height);
			display.prefWidthProperty().bind(width);
			myScene = new Scene(display,500, 500);
			return myScene;
		}
		

	}

