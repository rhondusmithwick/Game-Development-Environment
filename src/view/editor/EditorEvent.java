package view.editor;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import view.Authoring;
import view.Utilities;
import api.IEditor;
import api.ISerializable;
import enums.DefaultStrings;
import enums.FileExtensions;
import enums.GUISize;
import enums.ViewInsets;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Alankmc
 *
 */
public class EditorEvent extends Editor
{
	private VBox pane;
	private ResourceBundle myResources;
	private HashMap<String, TableView<String>> tableMap; 
	private HashMap<String, Button> buttonMap;


	/*
	 *  Constructor is made based on new Editor Factory.
	 *  I tried to pass an event system, but it deprecated.
	 *  TODO: Change this, because it's temporary
	 */
	public EditorEvent(String language, ISerializable toEdit, ObservableList<ISerializable> masterList, ObservableList<ISerializable> addToList)
	{
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		myResources = ResourceBundle.getBundle(language);
		tableMap = new HashMap<String, TableView<String>>();
		buttonMap = new HashMap<String, Button>();
	}

	private void makeButtons()
	{
		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());

		// Create Trigger Button
		buttonMap.put(myResources.getString("createTrigger"), Utilities.makeButton(myResources.getString("createTrigger"), 
				e-> createTrigger()));


		// Create Event Button
		buttonMap.put(myResources.getString("createEvent"), Utilities.makeButton(myResources.getString("createEvent"), 
				e-> createEvent()));

		for ( Button button: buttonMap.values() )
		{
			container.getChildren().add(button);
		}

		pane.getChildren().add(container);
	}

	private void createTrigger()
	{
		/*
		 * Go through list of possible triggers?
		 * Have to understand how the triggers are going to work, and how they will be stored.
		 * They could be done by some user input, timer action, or Entity parameter change.
		 * For user ease of use, try to separate these into their according lists.
		 */

		System.out.println("Creating Trigger!");
	}

	private void createEvent()
	{
		/*
		 * What is an event...?
		 * Something that a certain Entity or Game element will execute. 
		 * Maybe separate these into lists, like the triggers.
		 * 
		 * Events are characterized by a certain subject, executing a certain action, I think.
		 * So maybe also separate the Event into Executioner/Action.
		 *
		 */

		/*
		 * So, need some further explaining, but Events could be broken down to:
		 * When =TRIGGER= does/hits =ACTION/VALUE=, =EXECUTIONER= will =ACTION=.
		 * 
		 *  I see this better organized into 4 TableViews.
		 */

		System.out.println("Creating Event!");
	}

	private void createTables()
	{

		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());

		// Creates the Tables
		tableMap.put("Triggers", Utilities.makeSingleColumnTable("Triggers")); // TODO: RESOURCE FILE BITCHHHHHH
		tableMap.put("Trigger Actions", Utilities.makeSingleColumnTable("Triger Actions")); // TODO: RESOURCE FILE BITCHHHHHH
		tableMap.put("Actors", Utilities.makeSingleColumnTable("Actors")); // TODO: RESOURCE FILE BITCHHHHHH
		tableMap.put("Actor Actions", Utilities.makeSingleColumnTable("Actor Actions")); // TODO: RESOURCE FILE BITCHHHHHH

		for ( TableView<String> table: tableMap.values() )
		{
			container.getChildren().add(table);
			HBox.setHgrow(table, Priority.SOMETIMES);
		}

		pane.getChildren().add(container);
	}
	
	public void populateLayout() 
	{
		makeButtons();
		createTables();
	}

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub

	}

	@Override
	public Pane getPane() 
	{
		return pane;
	}

	@Override
	public void addSerializable(ISerializable serialize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateEditor() {
		// TODO Auto-generated method stub

	}

}
