package view.beginingmenus;

import java.util.ResourceBundle;
import enums.GUISize;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Authoring;
import view.Utilities;

public class AuthoringStartUp {

	private Stage myStage;
	private VBox myVBox;
	private Group root;
	private Authoring authEnv;
	private Scene myScene;
	private ResourceBundle myResources;
	private ComboBox<String> games;
	private String language;

	public AuthoringStartUp(Stage stage, String language) {
		myStage = stage;
		this.language=language;
		myResources = ResourceBundle.getBundle(language);
	}

	public void init() {
		myScene = new Scene(createDisplay(), GUISize.MAIN_SIZE.getSize(), GUISize.MAIN_SIZE.getSize());
		myStage.setScene(myScene);
		myStage.show();
	}

	private Group createDisplay() {
		root = new Group();
		setVBox();
		createGame();
		loadGame();
		return root;
	}
	
	private void loadGame(){
		HBox container = new HBox(GUISize.ORIG_MENU_PADDING.getSize());
		container.setAlignment(Pos.CENTER);
		games = Utilities.makeComboBox(myResources.getString("chooseGame"), Utilities.getAllFromDirectory("resources/createdGames"), null);
		Button loadGame = Utilities.makeButton(myResources.getString("loadGame"), e->createAuthoringFromFile());
		container.getChildren().addAll(games, loadGame);
		myVBox.getChildren().add(container);
	}

	private void createGame() {
		Button createGame = Utilities.makeButton(myResources.getString("createGame"), null);
		createGame.setOnAction(e -> createAuthoring());
		myVBox.getChildren().add(createGame);
	}

	private void setVBox() {
		myVBox = new VBox(GUISize.ORIG_MENU_PADDING.getSize());
		myVBox.prefHeightProperty().bind(myStage.heightProperty());
		myVBox.prefWidthProperty().bind(myStage.widthProperty());
		myVBox.setAlignment(Pos.CENTER);
		root.getChildren().add(myVBox);
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
