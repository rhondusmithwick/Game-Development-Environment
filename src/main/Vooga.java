package main;

import java.io.File;
import java.util.Arrays;
import java.util.ResourceBundle;

import enums.DefaultStrings;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Utilities;
import view.beginningmenus.AuthoringStartUp;
import view.beginningmenus.StartUpMenu;
import view.gameplaying.GamePlayer;

public class Vooga extends StartUpMenu {
	
	private Stage myStage;
	private ResourceBundle myResources;
	private ComboBox<String> languages;
	private ScrollPane root;

	public Vooga(Stage stage) {
		super(stage);
		myStage = stage;
		myResources = ResourceBundle.getBundle(DefaultStrings.LANG_LOC.getDefault()+DefaultStrings.DEFAULT_LANGUAGE.getDefault());
	}

	
	@Override
	protected ScrollPane createDisplay() {
		root = super.createDisplay();
		titleText();
		setLanguage();
		createButtons();
		return root;
	}
	
	private void titleText() {
		Text text = new Text("MakeGamesGreatAgain Presents:\nVOOGASalad");
		text.getStyleClass().add("title-text");
		Image image = new Image(new File("resources/testing/RhonduSmithwick.JPG").toURI().toString());
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(300);
		imageView.setPreserveRatio(true);
		super.addNodesToVBox(Arrays.asList(text, imageView));
	}

	private void createButtons() {
		Button makeGame = Utilities.makeButton(myResources.getString("makeGame"), e->createEditor());
		Button playGame = Utilities.makeButton(myResources.getString("playGame"), e->createPlayer());
		super.addNodesToVBox(Arrays.asList(makeGame,playGame));
	}



	private void createPlayer() {
		File file = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("XML","*.xml"), "Choose a saved game");
		if (file!= null){
			GamePlayer gamePlayer = new GamePlayer(myStage, getLanguage());
			gamePlayer.init(file.getPath());
		}
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
