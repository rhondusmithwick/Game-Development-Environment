package update;

import api.ISystemManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Utilities;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.enums.ViewInsets;
import java.io.File;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GameLoopManager {
	private int padding = GUISize.LOOP_MANAGER_PADDING.getSize();
	private ResourceBundle myResources;
	private Stage stage = new Stage();
	private VBox pane = new VBox(padding);
	private ScrollPane scrollPane = new ScrollPane(pane);
	private ComboBox<String> comboBox;
	private Scene scene;
	private TextField keyField, valueField;
	private ISystemManager systemManager;
	private ListView<String> valueList = new ListView<String>();
	
	public GameLoopManager(String language, ISystemManager game) {
		myResources = ResourceBundle.getBundle(language);
		scene = new Scene(scrollPane, GUISize.LOOP_MANAGER_WIDTH.getSize(), GUISize.LOOP_MANAGER_HEIGHT.getSize());
		File file = new File(DefaultStrings.CSS_LOCATION.getDefault() + DefaultStrings.MAIN_CSS.getDefault());
		scene.getStylesheets().add(file.toURI().toString());
		stage.setScene(scene);
		stage.setTitle(myResources.getString("loopManager"));
		pane.setPadding(ViewInsets.LOOP_EDIT.getInset());
		keyField = Utilities.makeTextArea(myResources.getString("addKey"));
		valueField = Utilities.makeTextArea(myResources.getString("valueText"));
		systemManager = game;
		populateStage();
	}

	private void populateStage() {
		VBox left = populateLeft();
		VBox right = populateRight();
		ObservableValue<? extends Number> halfPane = scrollPane.widthProperty().divide(GUISize.HALF.getSize());
		left.prefWidthProperty().bind(halfPane);
		right.prefWidthProperty().bind(halfPane);
		HBox container = createContainer(left, right);
		Button saveButton = Utilities.makeButton(myResources.getString("saveMeta"), e -> saveMetadata());
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(container, saveButton);
	}
	
	private VBox populateLeft() {
		VBox vBox = new VBox(padding);
		comboBox = Utilities.makeComboBox(myResources.getString("selectKey"), Arrays.asList(myResources.getString("keyDefault")), null);
		Button button = Utilities.makeButton(myResources.getString("addKey"), e -> addKey());
		vBox.getChildren().addAll(createContainer(keyField, button), comboBox);
		return vBox;
	}
	
	private VBox populateRight() {
		VBox vBox = new VBox(padding);
		Button button = Utilities.makeButton(myResources.getString("addValue"), e -> addKey());
		ObservableList<String> names = FXCollections.observableArrayList("Hi");
		valueList.setItems(names);
		vBox.getChildren().addAll(createContainer(valueField, button), valueList);
		return vBox;
	}
	
	public void show() {
		stage.show();
	}
	
//	private Map<String, String> getMetadata() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put(comboBox.getValue(), textField.getText());
//		return map;
//	}
	
	private void saveMetadata() {
		String key = comboBox.getValue();
		String value = valueField.getText();
//		System.out.println(key);
//		System.out.println(value);
		valueField.clear();
		if(key != null) {
			systemManager.getEntitySystem().addMetadata(key, value);
		}
	}
	
//	private VBox createVBox() {
//		return new VBox(GUISize.LOOP_MANAGER_PADDING.getSize());
//	}
	
	private HBox createContainer(Node a, Node b) {
		HBox container = new HBox(padding);
		container.getChildren().addAll(a, b);
		return container;
	}
	
	private void addKey() {
		String key = keyField.getText();
		comboBox.getItems().add(key);
		keyField.clear();
		comboBox.getSelectionModel().clearSelection();
	}
}
