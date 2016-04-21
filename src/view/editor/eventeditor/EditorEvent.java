package view.editor.eventeditor;

import javafx.scene.layout.Pane;
import java.util.ResourceBundle;
import view.editor.Editor;
import api.IEntity;
import api.ISerializable;
import enums.GUISize;
import enums.ViewInsets;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
	private final ScrollPane scrollPane;
	private final TabPane tabPane;
	private ResourceBundle myResources;
	
	private final Tab propertyTab;
	private final PropertyEventEditor propertyEventEditor;
	private final KeyBindingEditor keyBindingEditor;
	
	public EditorEvent(String language, ObservableList<IEntity> masterList)
	{	
		propertyEventEditor = new PropertyEventEditor(language, masterList);
		keyBindingEditor = new KeyBindingEditor(language);

		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		// pane.setAlignment(Pos.TOP_LEFT);
		myResources = ResourceBundle.getBundle(language);
		scrollPane = new ScrollPane(pane);
		
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
	
	public void populateLayout() {}


	public ScrollPane getPane() 
	{
		return scrollPane;
	}
}