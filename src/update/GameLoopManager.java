package update;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.enums.GUISize;

public class GameLoopManager {
	
	private ResourceBundle myResources;
	private Stage stage = new Stage();
	private GridPane gridPane = new GridPane();
	private ScrollPane scrollPane = new ScrollPane(gridPane);
	private Scene scene; 
	private int column = 0;
	private int row = 0;
	
	public GameLoopManager(String language) {
		myResources = ResourceBundle.getBundle(language);
		scene = new Scene(scrollPane, GUISize.LOOP_MANAGER_WIDTH.getSize(), GUISize.LOOP_MANAGER_HEIGHT.getSize());
		stage.setScene(scene);
		stage.setTitle(myResources.getString("loopManager"));
		populate();
	}

	private void populate() {
		
	}
}
