package view.editor.eventeditor;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

import api.IEntity;
import api.ILevel;
import api.ISerializable;
import enums.GUISize;
import enums.ViewInsets;
import events.Action;
import events.InputSystem;
import events.Trigger;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import view.Utilities;
import view.editor.Editor;

public class PropertyEventEditor extends Editor
{
	private final ScrollPane scrollPane;
	private final VBox pane;
	private final ResourceBundle myResources;
	private final LevelPicker levelPicker;
	
	private final HashMap<String, Button> actionButtons;
	private Text triggerText;
	private Text actionText;
	
	private Button chooseFileButton;
	private Button makeEventButton;
	private TableManager tableManager;
	
	EditorEvent masterEditor;
	
	private Trigger trigger;
	private Action action;
	private final InputSystem inputSystem;
	// private final ObservableList<ILevel> levelList;
	
	public PropertyEventEditor(String language, ObservableList<IEntity> masterList)//, ObservableList<ILevel> levelList)
	{
		// this.levelList = levelList;
		levelPicker = new LevelPicker(language, null);
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		myResources = ResourceBundle.getBundle(language);
		
		triggerText = new Text();
		actionText = new Text();
		chooseFileButton = new Button();
		makeEventButton = new Button();
		
		actionButtons = new HashMap<String, Button>();
		
		inputSystem = new InputSystem();
		tableManager = new TableManager(masterList, language, this, inputSystem );
		
		trigger = null;
		action = null;
		
		populateLayout();
		
		scrollPane = new ScrollPane(pane);
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

		container.getChildren().add(levelPicker.getPane());
		container.getChildren().add(tableManager.getContainer());	
		
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
		container.getChildren().add(makeGroovySide());
		pane.getChildren().add(container);
	}
	
	private void makeEvent()
	{
		// TODO: well, make Event
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
	public ScrollPane getPane() 
	{
		return scrollPane;
	}

	@Override
	public void loadDefaults() {}

	@Override
	public void addSerializable(ISerializable serialize) {}

	@Override
	public void updateEditor() {}
	
}
