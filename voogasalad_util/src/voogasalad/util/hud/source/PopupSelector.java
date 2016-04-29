package voogasalad.util.hud.source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Simple text area that pops up with a button in the authoring environment.
 * Saves a list of fields that the user desires to be shown in the HUD,
 * in the order that they appear on the text area.
 * 
 * @author bobby
 *
 */
public class PopupSelector {
	
	private Group root;
	private Scene scene;
	private Stage stage;
	private TextArea input;
	private IAuthoringHUDController controller;
	
	private static final double DEFAULT_WIDTH = 200;
	private static final double DEFAULT_HEIGHT = 200;
	private static final String HELP_TEXT = "Enter your desired HUD fields in order below. \nWhen finished, click 'save'.";
	
	
	public PopupSelector(double width, double height, IAuthoringHUDController controller) {
		this.controller = controller;
		this.root = new Group();
		this.scene = new Scene(root);
		this.stage = new Stage();
		stage.setScene(scene);
		input = new TextArea();
		input.setPrefSize(width, height);
		init();
		stage.show();
	}
	
	public PopupSelector(IAuthoringHUDController controller) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, controller);
	}
	
	/**
	 * Initializes the view of the popup selector
	 */
	
	
	public void init() {
		BorderPane top = new BorderPane();
		Button save = new Button("Save");
		top.setLeft(new Text(HELP_TEXT));
		top.setRight(save);
		BorderPane all = new BorderPane();
		all.setTop(top);
		all.setBottom(input);
		this.root.getChildren().add(all);
		save.setOnAction(e->saveInputs(input.getText()));	
	}
	
	
	/**
	 * On click of the save button, saves the inputs to an external file of
	 * the user's choosing 
	 * @param user input of text area
	 */
	
	public void saveInputs(String input) {
		File file = new FileChooser().showSaveDialog(stage);
		if (file != null) {
			try {
				String path = file.getPath() + ".hud";
				file = new File(path);
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write(input);
				writer.close();
				controller.setHUDInfoFile(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Scene getScene() {
		return scene;
	}
}