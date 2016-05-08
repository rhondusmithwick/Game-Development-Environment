package update;

import api.ILevel;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.ButtonFactory;
import view.utilities.ComboFactory;
import view.utilities.TextFieldFactory;
import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * GUI for managing game loop updates.
 * 
 * @author Ben Zhang
 */
public class GameLoopManager {
    private final int padding = GUISize.LOOP_MANAGER_PADDING.getSize();
    private final ResourceBundle myResources;
    private final Stage stage = new Stage();
    private final VBox pane = new VBox(padding);
    private final ScrollPane scrollPane = new ScrollPane(pane);
    private final TextField keyField;
    private final TextField valueField;
    private final ILevel level;
    private final Map<String, String> valueMap;
    private ComboBox<String> comboBox;
    private ObservableList<Integer> valueList = FXCollections.observableArrayList();
    private ListView<Integer> listView = new ListView<>(valueList);

    public GameLoopManager (String language, ISystemManager game) {
        myResources = ResourceBundle.getBundle(language);
        Scene scene = new Scene(scrollPane, GUISize.LOOP_MANAGER_WIDTH.getSize(), GUISize.LOOP_MANAGER_HEIGHT.getSize());
        scene.getStylesheets().add(new File(DefaultStrings.CSS_LOCATION.getDefault() + DefaultStrings.MAIN_CSS.getDefault()).toURI().toString());
        stage.setScene(scene);
        stage.setTitle(myResources.getString("loopManager"));
        pane.setPadding(ViewInsets.LOOP_EDIT.getInset());
        level = game.getLevel();
        valueMap = level.getMetadata();
        keyField = TextFieldFactory.makeTextArea(myResources.getString("keyText"));
        valueField = TextFieldFactory.makeTextArea(myResources.getString("valueText"));
        setupList();
        populateStage();
    }

    private void setupList () {
        listView.setCellFactory(e -> new DragDropCell<>());
        listView.setEditable(true);
        listView.setMaxWidth(GUISize.LIST_VIEW_WIDTH.getSize());
        listView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                valueList.remove(listView.getSelectionModel().getSelectedIndex());
            }
        });
    }

    private void populateStage () {
        VBox left = populateLeft();
        VBox right = populateRight();
        ObservableValue<? extends Number> halfPane = scrollPane.widthProperty().divide(GUISize.HALF.getSize());
        left.prefWidthProperty().bind(halfPane);
        right.prefWidthProperty().bind(halfPane);
        HBox container = createContainer(left, right);
        Button saveButton = ButtonFactory.makeButton(myResources.getString("saveMeta"), e -> saveMetadata());
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(container, saveButton);
    }

    private VBox populateLeft () {
        VBox vBox = new VBox(padding);
        String script = myResources.getString("keyDefault");
        comboBox = ComboFactory.makeComboBox(myResources.getString("selectKey"), Collections.singletonList(script), null);
        comboBox.setValue(script);
        populateList(script);
        Button button = ButtonFactory.makeButton(myResources.getString("addKey"), e -> addKey());
        vBox.getChildren().addAll(createContainer(keyField, button), comboBox);
        return vBox;
    }

    private VBox populateRight () {
        VBox vBox = new VBox(padding);
        Button button = ButtonFactory.makeButton(myResources.getString("addValue"), e -> addValue());
        vBox.getChildren().addAll(createContainer(valueField, button), listView);
        return vBox;
    }

    public void show () {
        stage.show();
    }

    private void saveMetadata () {
        String key = comboBox.getValue();
        if (key != null && valueList.size() > 0) {
            String commaList = "";
            for (Integer i : valueList) {
                commaList += i + ",";
            }
            commaList = commaList.substring(0, commaList.length() - 1);
            valueMap.put(key, commaList);
            level.setMetadata(valueMap);
            stage.close();
        }
    }

    private HBox createContainer (Node a, Node b) {
        HBox container = new HBox(padding);
        container.getChildren().addAll(a, b);
        return container;
    }

    private void addKey () {
        String key = keyField.getText();
        if (!key.isEmpty()) {
            comboBox.getItems().add(key);
            keyField.clear();
        }
    }

    private void populateList (String key) {
        String val = valueMap.get(key);
        if (val != null) {
        	ObservableList<String> temp = FXCollections.observableArrayList(val.split(","));
        	for(String str: temp) {
        		valueList.add(Integer.parseInt(str));
        	}
            listView = new ListView<>(valueList);
            setupList();
        }
    }

    private void addValue () {
        String value = valueField.getText();
        if (!value.isEmpty()) {
            valueList.add(Integer.parseInt(value));
            valueField.clear();
        }
    }
}
