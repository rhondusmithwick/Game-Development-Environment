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
		private String language;

		/**
		 * Constructor that takes in the language choice of the user.
		 * @param language that the user chooses the program from.
		 */
		public Authoring(String language){
			this.language = language;
			myResources = ResourceBundle.getBundle(language);
		}

		/**
		 * Initializes the scene which is displayed in the window.
		 * @return the splash screen scene
		 */
		
		public  Scene init(ReadOnlyDoubleProperty height, ReadOnlyDoubleProperty width){
			display = new TabPane();
			GameEditor gEdit = new GameEditor(this, language);
			createTab(gEdit.getPane(), "gDeets", false);
			myScene = new Scene(display,GUISize.AUTHORING_WIDTH.getSize(), GUISize.AUTHORING_HEIGHT.getSize());
			display.prefHeightProperty().bind(height);
			display.prefWidthProperty().bind(width);
			return myScene;
		}
		
		public void createTab(Pane tabContent, String key, boolean closable){
			Tab tab = new Tab(myResources.getString(key));
			tab.setContent(tabContent);
			tab.setClosable(closable);
			tabContent.prefHeightProperty().bind(display.heightProperty().subtract(GUISize.TOP_TAB.getSize()));
			tabContent.prefWidthProperty().bind(display.widthProperty());
			display.getTabs().add(tab);
	
		}
		

	}

