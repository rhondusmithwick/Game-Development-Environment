package voogasalad.util.hud.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import voogasalad.util.hud.source.IAuthoringHUDController;
import voogasalad.util.hud.source.PopupSelector;

/**
 * Initializes a screen with a button that will open a popup selector.
 * Shows basic functionality of popup selecor and is an example of how
 * to possibly implement popup selector in your own authoring environment
 * 
 * @author bobby
 * 
 */


public class ExampleMain extends Application implements IAuthoringHUDController{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage myStage = primaryStage;
		myStage.setResizable(false);
		Group root = new Group();
		Scene s = new Scene(root, 50, 50);
		Button b = new Button("OPEN");
		b.setOnAction(e->new PopupSelector(this));
		root.getChildren().add(b);
		
		myStage.setScene(s);
		
		myStage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void setHUDInfoFile(String location) {
		System.out.println(location);
	}

}