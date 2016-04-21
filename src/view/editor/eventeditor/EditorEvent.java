package view.editor.eventeditor;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import view.Authoring;
import view.Utilities;
import view.editor.Editor;
import api.IEditor;
import api.ISerializable;
import enums.DefaultStrings;
import enums.FileExtensions;
import enums.GUISize;
import enums.ViewInsets;
import events.Action;
import events.Trigger;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.entity.Entity;

/**
 * 
 * @author Alankmc
 *
 */

/*
 * TODO: Clean this shit up
 */
public class EditorEvent extends Editor
{
	private final Pane pane;
	private final TabPane tabPane;
	private final ResourceBundle myResources;
	
	
	
	private final Tab propertyTab;
	private final PropertyEventEditor propertyEventEditor;
	private final KeyBindingEditor keyBindingEditor;
	
	public EditorEvent(String language, ISerializable toEdit, ObservableList<ISerializable> masterList, ObservableList<ISerializable> addToList)
	{
		
		propertyEventEditor = new PropertyEventEditor(language, toEdit, masterList, addToList);
		keyBindingEditor = new KeyBindingEditor(language);
		
		pane = new Pane();
		tabPane = new TabPane();
		propertyTab = new Tab();
		
		pane.getChildren().add(tabPane);
		
		
		myResources = ResourceBundle.getBundle(language);

		// TODO: Put editors in map and use cool for loop for this
		populateEditorTab(propertyEventEditor);
		populateEditorTab(keyBindingEditor);
	}

	private void populateEditorTab(Editor editor)
	{
		Tab newTab = new Tab();
		
		newTab.setContent(editor.getPane());
		newTab.setClosable(false);
		tabPane.getTabs().add(newTab);
		newTab.setText(myResources.getString(editor.getClass().toString().split(" ")[1]));
	}
	
	public void populateLayout() 
	{
	}

	@Override
	public void loadDefaults() {}

	@Override
	public Pane getPane() 
	{
		return pane;
	}

	@Override
	public void addSerializable(ISerializable serialize) {}

	@Override
	public void updateEditor() {}

}