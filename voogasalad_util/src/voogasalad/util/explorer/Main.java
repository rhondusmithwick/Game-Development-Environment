package voogasalad.util.explorer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String DEFAULT_PROJECT_NAME = "My Project";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		TabPane pane = new TabPane();
		pane.getTabs().add(new ResourceUI(DEFAULT_PROJECT_NAME));
		Scene scene = new VoogaScene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String args[]) {
		launch(args);
	}

}
