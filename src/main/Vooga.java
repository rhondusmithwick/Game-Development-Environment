package main;

import java.util.Arrays;
import java.util.ResourceBundle;

import enums.DefaultStrings;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import view.Utilities;
import view.beginingmenus.AuthoringStartUp;
import view.beginingmenus.StartUpMenu;
import view.gameplaying.GamePlayer;

public class Vooga extends StartUpMenu {
	
	private Stage myStage;
	private ResourceBundle myResources;
	private ComboBox<String> languages;
	private Group root;

	public Vooga(Stage stage) {
		super(stage);
		myStage = stage;
		myResources = ResourceBundle.getBundle(DefaultStrings.LANG_LOC.getDefault()+DefaultStrings.DEFAULT_LANGUAGE.getDefault());
	}

	
	@Override
	protected Group createDisplay() {
		root = super.createDisplay();
		setLanguage();
		createButtons();
		return root;
	}

	private void createButtons() {
		Button makeGame = Utilities.makeButton(myResources.getString("makeGame"), e->createEditor());
		Button playGame = Utilities.makeButton(myResources.getString("playGame"), e->createPlayer());
		super.addNodesToVBox(Arrays.asList(makeGame,playGame));
	}



	private void createPlayer() {
		GamePlayer gamePlayer = new GamePlayer(myStage, getLanguage());
		gamePlayer.init();
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
		super.addNodesToVBox(Arrays.asList(languages));
		
	}
	
	
	
}
