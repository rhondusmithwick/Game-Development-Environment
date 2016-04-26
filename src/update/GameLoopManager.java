package update;

import api.ISystemManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Utilities;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.enums.ViewInsets;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameLoopManager {
	
	private ResourceBundle myResources;
	private Stage stage = new Stage();
	private GridPane gridPane = new GridPane();
	private VBox pane = createVBox();
	private ScrollPane scrollPane = new ScrollPane(pane);
	private ComboBox<String> comboBox;
	private Scene scene;
	private TextField textField;
	private ISystemManager systemManager;
	
	public GameLoopManager(String language, ISystemManager game) {
		myResources = ResourceBundle.getBundle(language);
		scene = new Scene(scrollPane, GUISize.LOOP_MANAGER_WIDTH.getSize(), GUISize.LOOP_MANAGER_HEIGHT.getSize());
		scene.getStylesheets().add(new File(DefaultStrings.CSS_LOCATION.getDefault() + DefaultStrings.MAIN_CSS.getDefault()).toURI().toString());
		stage.setScene(scene);
		stage.setTitle(myResources.getString("loopManager"));
		pane.setPadding(ViewInsets.LOOP_EDIT.getInset());
		textField = Utilities.makeTextArea(myResources.getString("valueText"));
		systemManager = game;
		populateStage();
	}

	private void populateStage() {
		VBox left = populateLeft();
		VBox right = populateRight();
		left.prefWidthProperty().bind(scrollPane.widthProperty().divide(GUISize.HALF.getSize()));
		right.prefWidthProperty().bind(scrollPane.widthProperty().divide(GUISize.HALF.getSize()));
		HBox container = createHBox();
		container.getChildren().addAll(left, right);
		pane.getChildren().add(container);
	}
	
	private VBox populateLeft() {
		VBox vBox = createVBox();
		
		comboBox = Utilities.makeComboBox(myResources.getString("selectKey"), Arrays.asList(myResources.getString("keyDefault")), null);
		vBox.getChildren().add(comboBox);
		return vBox;
	}
	
	private VBox populateRight() {
		VBox vBox = createVBox();
		Button button = Utilities.makeButton(myResources.getString("saveMeta"), e -> saveMetadata());
		vBox.getChildren().addAll(textField, button);
		return vBox;
	}
	
	public void show() {
		stage.show();
	}
	
	private Map<String, String> getMetadata() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(comboBox.getValue(), textField.getText());
		return map;
	}
	
	private void saveMetadata() {
		String key = comboBox.getValue();
		String value = textField.getText();
		textField.clear();
		systemManager.getEntitySystem().addMetadata("Script", value); // TODO: key doesn't work (key==null)
	}
	
	private VBox createVBox() {
		return new VBox(GUISize.LOOP_MANAGER_PADDING.getSize());
	}
	
	private HBox createHBox() {
		return new HBox(GUISize.LOOP_MANAGER_PADDING.getSize());
	}
}
