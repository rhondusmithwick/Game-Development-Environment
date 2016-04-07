package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ResourceBundle;

import enums.GUISize;

public class Authoring {
		
		private ResourceBundle myResources;
		private Scene myScene;
		private TabPane display;

		/**
		 * Constructor that takes in the language choice of the user.
		 * @param language that the user chooses the program from.
		 */
		public Authoring(String lang){
			myResources = ResourceBundle.getBundle("resources/english");
		}

		/**
		 * Initializes the scene which is displayed in the window.
		 * @return the splash screen scene
		 */
		
		public Scene init(ReadOnlyDoubleProperty height, ReadOnlyDoubleProperty width){
			display = new TabPane();
			display.prefHeightProperty().bind(height);
			display.prefWidthProperty().bind(width);
			myScene = new Scene(display,GUISize.AUTHORING_START.getSize(), GUISize.AUTHORING_START.getSize());
			return myScene;
		}
		
		public void createTab(Group tabContent, String key){
			Tab tab = new Tab(myResources.getString(key));
			tab.setContent(tabContent);
			display.getTabs().add(tab);
	
		}
		

	}

