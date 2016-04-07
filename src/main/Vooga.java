package main;

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
	/**
	 * Constructor that takes in a stage to display the graphics.
	 * @param stage
	 */
	
	public Vooga(Stage stage) {
		myStage = stage;
	}

	/**
	 * Initializes the scene which is displayed in the window.
	 * @return the splash screen scene
	 */
	
	public Scene init(){
		myScene = new Scene(createDisplay(), 500, 500);
		return myScene;
	}
	
	public void draw(ImageView image) {
		root.getChildren().add(image);
	}

	private Group createDisplay() {
		root = new Group();
		Button createGame = Utilities.makeButton("Create Game", null);
		createGame.setOnAction(e->createAuthoring());
		myVBox = new VBox(30);
		myVBox.setAlignment(Pos.CENTER);
		myVBox.getChildren().add(createGame);
		root.getChildren().add(myVBox);
		return root;
	}
	
	private void createAuthoring(){
		authEnv = new Authoring();
		myScene = authEnv.init(myStage.widthProperty(), myStage.heightProperty());
		myStage.setScene(myScene);
	}

}
