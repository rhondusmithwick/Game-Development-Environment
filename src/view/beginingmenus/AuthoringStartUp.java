package view.beginingmenus;

import java.io.File;
import java.util.Arrays;
import java.util.ResourceBundle;
import enums.GUISize;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.Authoring;
import view.Utilities;

public class AuthoringStartUp extends StartUpMenu {

	private Stage myStage;
	private Group root;
	private Authoring authEnv;
	private Scene myScene;
	private ResourceBundle myResources;
	private ComboBox<String> games;
	private String language;

	public AuthoringStartUp(Stage stage, String language) {
		super(stage);
		myStage = stage;
		this.language=language;
		myResources = ResourceBundle.getBundle(language);
	}

	@Override
	protected Group createDisplay() {
		root = super.createDisplay();
		addImage();
		createGame();
		loadGame();
		return root;
	}
	
	private void addImage() {
		Image image = new Image(new File("resources/trump.jpg").toURI().toString());
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(300);
		imageView.setPreserveRatio(true);
		super.addNodesToVBox(Arrays.asList(imageView));
	}
	
	private void loadGame(){
		HBox container = new HBox(GUISize.ORIG_MENU_PADDING.getSize());
		container.setAlignment(Pos.CENTER);
		games = Utilities.makeComboBox(myResources.getString("chooseGame"), Utilities.getAllFromDirectory("resources/createdGames"), null);
		Button loadGame = Utilities.makeButton(myResources.getString("loadGame"), e->createAuthoringFromFile());
		container.getChildren().addAll(games, loadGame);
		super.addNodesToVBox(Arrays.asList(container));
	}

	private void createGame() {
		Button createGame = Utilities.makeButton(myResources.getString("createGame"), null);
		createGame.setOnAction(e -> createAuthoring());
		super.addNodesToVBox(Arrays.asList(createGame));
	}

	private void createAuthoring() {
		setUpAuthoring();
		authEnv = new Authoring(language);
		showAuthoring();
	}
	
	private void createAuthoringFromFile() {
		setUpAuthoring();
		String fileName = games.getSelectionModel().getSelectedItem();
		if(fileName == null){
			createAuthoring();
		}
		authEnv = new Authoring(language, fileName);
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



}
