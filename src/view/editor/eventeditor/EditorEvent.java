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
import javafx.scene.control.ScrollPane;
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
	private final VBox pane;
	private final ResourceBundle myResources;
	
	private final HashMap<String, Button> actionButtons;
	private Text triggerText;
	private Text actionText;
	
	private Button chooseFileButton;
	private Button makeEventButton;
	private TableManager tableManager;
	private ScrollPane scrollPane;
	private Trigger trigger;
	private Action action;
	
	public EditorEvent(String language, ISerializable toEdit, ObservableList<ISerializable> masterList, ObservableList<ISerializable> addToList)
	{
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		myResources = ResourceBundle.getBundle(language);
		scrollPane = new ScrollPane(pane);
		triggerText = new Text();
		actionText = new Text();
		chooseFileButton = new Button();
		makeEventButton = new Button();
		
		actionButtons = new HashMap<String, Button>();
		
		tableManager = new TableManager(masterList, language, this );
		
		trigger = null;
		action = null;
	}

	public void setActions(ObservableList<String> actions)
	{
		/*
		this.actions = actions;

		((TableView<String>)entityCustomizables.get(myResources.getString("actionsPane"))).setItems(actions);
		*/
	}

	private VBox makeGroovySide()
	{
		VBox container = new VBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		// Adding now the Groovy Table
		chooseFileButton = Utilities.makeButton("Choose Groovy Script", e -> getFile());	// TODO resource
		
		container.getChildren().addAll(chooseFileButton);
		return container;
	}

	private void getFile()
	{
		File groovyFile = null;
		
		groovyFile = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("groovy", "*.groovy"), "Select your groovy script!");
		if ( groovyFile != null )
		{
			actionSet(groovyFile.getName(), new Action(groovyFile.toString()));
		}
	}

	private void makeTables()
	{
		
		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());

		container.getChildren().add(tableManager.getContainer());	
		container.getChildren().add(makeGroovySide());
		
		pane.getChildren().add(container);
	}
	
	private void makeBottomPart()
	{
		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		
		triggerSet("Not yet Defined", null);	// TODO resource
		actionSet("Not yet Defined", null);
		
		makeEventButton = Utilities.makeButton("MAKE EVENT!", e -> makeEvent());	// TODO resource
		makeEventButton.setDisable(true);
		
		container.getChildren().addAll(triggerText, actionText, makeEventButton);
		
		pane.getChildren().add(container);
	}
	
	private void makeEvent()
	{
		
	}
	
	public void populateLayout() 
	{
		makeTables();
		makeBottomPart();
	}

	public void triggerSet(String triggerString, Trigger trigger)
	{
		this.trigger = trigger;
		triggerText.setText("TRIGGER: \n" + triggerString);	// TODO resource
		makeEventButton.setDisable( (this.trigger == null) || (this.action == null) );
	}
	
	private void actionSet(String actionString, Action action)
	{
		this.action = action;
		actionText.setText("ACTION:\n" + actionString);	// TODO resource
		makeEventButton.setDisable( (this.trigger == null) || (this.action == null) );
	}
	
	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub

	}

	@Override
	public ScrollPane getPane() 
	{
		return scrollPane;
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