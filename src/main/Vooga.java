package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import datamanagement.XMLReader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Authoring;
import view.View;
import view.beginningmenus.StartUpMenu;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.ComboFactory;
import view.utilities.FileUtilities;

public class Vooga extends StartUpMenu {

	private Stage myStage;
	private Scene myScene;
	private ResourceBundle myResources;
	private ComboBox<String> languages;
	private ScrollPane root;
	private ChoiceDialog<HBox> gameChooser;
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
		Button makeGame = ButtonFactory.makeButton(myResources.getString("makeGame"), e->createEditor());
		Button editGame = ButtonFactory.makeButton(myResources.getString("editGame"), e->loadCreated());
		Button playGame = ButtonFactory.makeButton(myResources.getString("playGame"), e->createPlayer());
		super.addNodesToVBox(Arrays.asList(makeGame,editGame, playGame));
	}



	private String chooseGame() {
		List<String> games = new ArrayList<>(FileUtilities.getAllFromDirectory(DefaultStrings.CREATE_LOC.getDefault()));
		List<HBox> hboxes = new ArrayList<>();
		games.stream().forEach(game->makeHBox(game, hboxes));
		gameChooser = new ChoiceDialog<HBox>(null,hboxes);
		myStage.hide();
		gameChooser.showAndWait();
		String chosen = ( (Label) gameChooser.getSelectedItem().getChildren().get(0)).getText();
		return chosen;
	}


	private void makeHBox(String game, List<HBox> hboxes) {
		List<String> details = new XMLReader<List<String>>().readSingleFromFile(DefaultStrings.CREATE_LOC.getDefault()+ game + DefaultStrings.METADATA_LOC.getDefault());
		Label title = new Label(details.get(0));
		Label desc = new Label(details.get(1));
		HBox temp = new HBox();
		temp.getChildren().addAll(title,desc);
		hboxes.add(temp);
		
	}


	private void createPlayer() {
		String path = chooseGame();
		if (path!= null){
			View view = new View(getLanguage());
			Pane pane = view.getPane();
			Scene scene = new Scene(pane, 500, 500);
			myStage.setScene(scene);
		}
		myStage.show();
	}


	private void setLanguage() {
		languages = ComboFactory.makeComboBox(myResources.getString("displayLanguage"), Arrays.asList("english", "arabic"), null);
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
		setUpAuthoring();
		authEnv = new Authoring(getLanguage());
		showAuthoring();

	}
	
	private void loadCreated() {
		setUpAuthoring();
		String path = chooseGame();
		if (path!= null){
			authEnv = new Authoring(getLanguage(), path);
			showAuthoring();
		}else{
			createEditor();
		}
		
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

}
