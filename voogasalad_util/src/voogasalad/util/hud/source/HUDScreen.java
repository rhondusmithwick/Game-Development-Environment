package voogasalad.util.hud.source;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

/**
 * Implementation of AbstractHUDScreen. Feel free to use
 * this or to write your own implementation of AbstractHUDScreen
 * for the view of this package
 * 
 * @author bobby
 *
 */

public class HUDScreen extends AbstractHUDScreen {

	
	private static final int DEFAULT_HEIGHT = 100;
	private static final int DEFAULT_WIDTH = 1000;
	
	private Map<String, Property> status;
	Map<String, Integer> valueToRowMap;
	Map<Integer, String> rowToValueMap;
	private SubScene myScene;
    ObservableList<String> keys;
    ObservableList<String> values;
    private Group mySubGroup;
    
	public HUDScreen(double width, double height, Map<String, Property> status, Map<Integer, String> rowToValueMap) {
		mySubGroup = new Group();
		myScene = new SubScene(mySubGroup, width, height);
		myScene.setFocusTraversable(false);
		this.status = status;
		this.rowToValueMap = rowToValueMap;
		this.valueToRowMap = new HashMap<String, Integer>();
		for (int i = 0; i<rowToValueMap.size(); i++) {
			valueToRowMap.put(rowToValueMap.get(i), i);
		}
		init();
	}
	
	public HUDScreen(Map<String, Property> status, Map<Integer, String> rowToValueMap) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, status, rowToValueMap);
	}
	
	public HUDScreen(double width, double height, Map<String, Property> status) {
		this(width, height, status, new HashMap<Integer, String>());
		int i = 0;
		for (String value : status.keySet()) {
			rowToValueMap.put(i, value);
			valueToRowMap.put(value, i);
			i++;
		}
	}
	
	public HUDScreen(Map<String, Property> status) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, status);
	}
	
	
	public void init() {
		keys = FXCollections.observableArrayList();
		values = FXCollections.observableArrayList();
        ListView<String> keyView = new ListView<>(keys);
        ListView<String> valueView = new ListView<>(values);
        keyView.setMaxWidth(myScene.getWidth()/2);
        valueView.setMaxWidth(myScene.getWidth()/2);
        
        BorderPane container = new BorderPane();
        container.setFocusTraversable(false);
        container.setLeft(keyView);
        container.setRight(valueView);
		
        for (int i = 0; i<status.size(); i++) {
        	keys.add(rowToValueMap.get(i));
        	values.add(status.get(rowToValueMap.get(i)).toString());
        }
        
        ScrollPane superContainer = new ScrollPane();
        superContainer.setContent(container);
        superContainer.setPrefSize(myScene.getWidth(), myScene.getHeight());
        
        mySubGroup.getChildren().add(superContainer);
        
	}
	
	public SubScene getScene() {
		return myScene;
	}
	
	public void handleChange(ValueChange change) {
		int rownum = valueToRowMap.get(change.getFieldName());
		values.set(rownum, change.getNewValue().toString());
	}
}
