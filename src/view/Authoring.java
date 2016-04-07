package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

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
		public Authoring(){
			myResources = ResourceBundle.getBundle("english");
		}

		/**
		 * Initializes the scene which is displayed in the window.
		 * @return the splash screen scene
		 */
		
		public  Scene init(ReadOnlyDoubleProperty height, ReadOnlyDoubleProperty width){
			display = new TabPane();
			display.prefHeightProperty().bind(height);
			display.prefWidthProperty().bind(width);
			GameEditor gEdit = new GameEditor();
			createTab(gEdit.getPane(), "gDeets");
			myScene = new Scene(display,GUISize.AUTHORING_START.getSize(), GUISize.AUTHORING_START.getSize());
			return myScene;
		}
		
		public void createTab(Pane tabContent, String key){
			Tab tab = new Tab(myResources.getString(key));
			tab.setContent(tabContent);
			tabContent.prefHeightProperty().bind(display.heightProperty().subtract(GUISize.TOP_TAB.getSize()));
			tabContent.prefWidthProperty().bind(display.widthProperty());
			display.getTabs().add(tab);
	
		}
		

	}

