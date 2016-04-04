package model.vooga;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SplashScreen {

	private Stage myStage;
	private VBox myVBox;
	/**
	 * Constructor that takes in a stage to display the graphics.
	 * @param stage
	 */
	
	public SplashScreen(Stage stage) {
		myStage = stage;
	}

	/**
	 * Initializes the scene which is displayed in the window.
	 * @return the splash screen scene
	 */
	
	public Scene init(){
		BorderPane display = createDisplay();
		Scene myScene = new Scene(display, 500, 500);
		return myScene;
	}
	
	/**
	 * Creates the splash screen display independently of initializing the scene.
	 * @return BorderPane in which the contents are the splash screen
	 */
	private BorderPane createDisplay() {
		Group root = new Group();
		Button createGame = Utilities.makeButton("Create Game", e -> myStage.setScene(Authoring.init()));

		myVBox = new VBox(30);
		myVBox.setAlignment(Pos.CENTER);
		myVBox.getChildren().add(createGame);
		root.getChildren().add(myVBox);

		BorderPane splash = new BorderPane();
		splash.setCenter(root);
		return splash;
	}

}
