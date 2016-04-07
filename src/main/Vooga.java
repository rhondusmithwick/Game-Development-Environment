package main;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Authoring;
import view.Utilities;

public class Vooga {

	private Stage myStage;
	private VBox myVBox;
	private Group root;
	private ResourceBundle myResources;
	private Authoring myAuthor;
	/**
	 * Constructor that takes in a stage to display the graphics.
	 * @param stage
	 */
	
	public Vooga(Stage stage) {
		myResources = ResourceBundle.getBundle("authoring");
		myStage = stage;
	}

	/**
	 * Initializes the scene which is displayed in the window.
	 * @return the splash screen scene
	 */
	
	public Scene init(){
		BorderPane display = createDisplay();
		Scene myScene = new Scene(display, Integer.parseInt(myResources.getString("splashWidth")), Integer.parseInt(myResources.getString("splashHeight")));
		return myScene;
	}
	
	public void draw(ImageView image) {
		root.getChildren().add(image);
	}
	
	/**
	 * Creates the splash screen display independently of initializing the scene.
	 * @return BorderPane in which the contents are the splash screen
	 */
	private BorderPane createDisplay() {
		root = new Group();
		Button createGameButton = Utilities.makeButton(myResources.getString("createGameButtonLabel"), e -> myStage.setScene(myAuthor.init(myStage.widthProperty(), myStage.heightProperty())));
		Button playGameButton = Utilities.makeButton(myResources.getString("playGameButtonLabel"), null);
		root.getChildren().add(createGameButton);
		//root.getChildren().add(playGameButton);
		BorderPane splash = new BorderPane();
		splash.setCenter(root);
		return splash;
	}

}
