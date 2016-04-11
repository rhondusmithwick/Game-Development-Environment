package main;

import java.util.Arrays;
import java.util.ResourceBundle;
import enums.DefaultStrings;
import enums.GUISize;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Utilities;

public class Vooga {
	
	private Stage myStage;
	private ResourceBundle myResources;
	private ComboBox<String> languages;
	private VBox myVBox;
	private Scene myScene;
	private Group root;

	public Vooga(Stage stage) {
		myStage = stage;
		myResources = ResourceBundle.getBundle(DefaultStrings.LANG_LOC.getDefault()+DefaultStrings.DEFAULT_LANGUAGE.getDefault());
	}
	
	public void init(){
		myScene = new Scene(createDisplay(), GUISize.MAIN_SIZE.getSize(), GUISize.MAIN_SIZE.getSize());
		myStage.setScene(myScene);
		myStage.show();
	}
	
	private Group createDisplay() {
		root = new Group();
		setVBox();
		setLanguage();
		createButtons();
		return root;
	}


	private void setVBox() {
		myVBox = new VBox(GUISize.ORIG_MENU_PADDING.getSize());
		myVBox.prefHeightProperty().bind(myStage.heightProperty());
		myVBox.prefWidthProperty().bind(myStage.widthProperty());
		myVBox.setAlignment(Pos.CENTER);
		root.getChildren().add(myVBox);
		
	}

	private void createButtons() {
		myVBox.getChildren().add(Utilities.makeButton(myResources.getString("makeGame"), e->createEditor()));
		myVBox.getChildren().add(Utilities.makeButton(myResources.getString("playGame"), e->createPlayer()));
	}



	private void createPlayer() {
		System.out.print("gotta do this still");
	}
	
	private String getLanguage(){
		String temp = languages.getSelectionModel().getSelectedItem();
		if(temp == null){
			temp = DefaultStrings.DEFAULT_LANGUAGE.getDefault();
		}
		return DefaultStrings.LANG_LOC.getDefault() + temp;

	}

	private void createEditor() {

		AuthoringStartUp auth = new AuthoringStartUp(myStage, getLanguage());
		auth.init();
	}

	private void setLanguage() {
		languages = Utilities.makeComboBox(myResources.getString("dispLang"), Arrays.asList("english", "spanish", "arabic"), null);
		myVBox.getChildren().add(languages);
		
	}
	
	
	
}
