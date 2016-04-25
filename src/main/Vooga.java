package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import datamanagement.XMLReader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import view.Authoring;
import view.Utilities;
import view.beginningmenus.StartUpMenu;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.enums.Indexes;
import view.gameplaying.GamePlayer;

public class Vooga extends StartUpMenu {

	private Stage myStage;
	private Scene myScene;
	private ResourceBundle myResources;
	private ComboBox<String> languages;
	private ScrollPane root;
	private ComboBox<HBox> gameChooser;
	private Authoring authEnv;

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
		gameChooseDialog();
		createButtons();
		return root;
	}

	private void titleText() {
		Text text = new Text(myResources.getString("titleScreen"));
		text.getStyleClass().add("title-text");
		Image image = new Image(new File(DefaultStrings.RHONDU.getDefault()).toURI().toString());
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(GUISize.INTRO_PIC.getSize());
		imageView.setPreserveRatio(true);
		super.addNodesToVBox(Arrays.asList(text, imageView));
	}

	private void createButtons() {
		Button makeGame = Utilities.makeButton(myResources.getString("makeGame"), e->createEditor());
		Button playGame = Utilities.makeButton(myResources.getString("playGame"), e->createPlayer());
		super.addNodesToVBox(Arrays.asList(makeGame,playGame));
	}



	private void createPlayer() {
		String path = getFile();
		if (path!= null){
			GamePlayer gamePlayer = new GamePlayer(myStage, getLanguage());
			gamePlayer.init(path);
		}
	}

	private String getFile() {
		HBox choosen = gameChooser.getSelectionModel().getSelectedItem();
		if(choosen == null){
			return null;
		}
		return ((Label) choosen.getChildren().get(1)).getText();
	}


	private void setLanguage() {
		languages = Utilities.makeComboBox(myResources.getString("dispLang"), Arrays.asList("english", "arabic"), null);
		super.addNodesToVBox(Arrays.asList(languages));

	}

	private String getLanguage(){
		String temp = languages.getSelectionModel().getSelectedItem();
		if(temp == null){
			temp = DefaultStrings.DEFAULT_LANGUAGE.getDefault();
		}
		return DefaultStrings.LANG_LOC.getDefault() + temp;

	}

	private void createEditor() {
		String choosen =  getFile();
		setUpAuthoring();
		if(choosen == null){
			authEnv = new Authoring(getLanguage());
		}else{
			authEnv = new Authoring(getLanguage(), choosen);
		}
		showAuthoring();

	}


	private void setUpAuthoring() {
		myStage.hide();
		myStage.setWidth(GUISize.AUTHORING_WIDTH.getSize());
		myStage.setHeight(GUISize.AUTHORING_HEIGHT.getSize());

	}

	private void showAuthoring() {
		myScene = authEnv.init(myStage.widthProperty(), myStage.heightProperty());
		myStage.setScene(myScene);
		myStage.show();
	}




	private void gameChooseDialog(){
		List<String> games = new ArrayList<>(Utilities.getAllFromDirectory(DefaultStrings.CREATE_LOC.getDefault()));
		List<HBox> gameDisp = new ArrayList<>();
		games.forEach(e->createHBox(gameDisp, e));
		gameChooser = new ComboBox<HBox>();
		gameChooser.setPromptText(myResources.getString("chooseGame"));
		gameChooser.getItems().addAll(gameDisp);
		super.addNodesToVBox(Arrays.asList(gameChooser));
	}


	private void createHBox(List<HBox> gameDisp, String gameName) {
		HBox disp = new HBox();
		List<String> list = new XMLReader<List<String>>().readSingleFromFile(DefaultStrings.CREATE_LOC.getDefault() + gameName + DefaultStrings.METADATA_LOC.getDefault());
		Label title = new Label(list.get(Indexes.GAME_NAME.getIndex()));
		Label desc = new Label(list.get(Indexes.GAME_DESC.getIndex()));
		File file = new File(list.get(Indexes.GAME_ICON.getIndex()));
		ImageView image = new ImageView( new Image(file.toURI().toString()) );
		disp.getChildren().addAll(image, title, desc);
		gameDisp.add(disp);


	}





}
