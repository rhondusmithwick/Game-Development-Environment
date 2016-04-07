package main;

import java.util.ResourceBundle;

import enums.DefaultStrings;
import enums.GUISize;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Authoring;
import view.Utilities;

public class Vooga {

	private Stage myStage;
	private VBox myVBox;
	private Group root;
	private Authoring authEnv;
	private Scene myScene;
	private ResourceBundle myResources;
	/**
	 * Constructor that takes in a stage to display the graphics.
	 * @param stage
	 */
	
	public Vooga(Stage stage) {
		myStage = stage;
		myResources = ResourceBundle.getBundle(DefaultStrings.DEFAULT_LANGUAGE.getDefault());
	}

	/**
	 * Initializes the scene which is displayed in the window.
	 * @return the splash screen scene
	 */
	
	public Scene init(){
		myScene = new Scene(createDisplay(), GUISize.MAIN_SIZE.getSize(), GUISize.MAIN_SIZE.getSize());
		return myScene;
	}
	
	public void draw(ImageView image) {
		root.getChildren().add(image);
	}

	private Group createDisplay() {
		root = new Group();
		Button createGame = Utilities.makeButton(myResources.getString("createGame"), null);
		createGame.setOnAction(e->createAuthoring());
		myVBox = new VBox(GUISize.ORIG_MENU_PADDING.getSize());
		myVBox.setAlignment(Pos.CENTER);
		myVBox.getChildren().add(createGame);
		root.getChildren().add(myVBox);
		return root;
	}
	
	private void createAuthoring(){
		myStage.hide();
		myStage.setWidth(GUISize.AUTHORING_WIDTH.getSize());
		myStage.setHeight(GUISize.AUTHORING_HEIGHT.getSize());
		authEnv = new Authoring(DefaultStrings.DEFAULT_LANGUAGE.getDefault());
		myScene = authEnv.init(myStage.widthProperty(), myStage.heightProperty());
		myStage.setScene(myScene);
		myStage.show();
	}

}
