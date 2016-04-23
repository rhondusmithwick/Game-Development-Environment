package view.editor.eventeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import events.Action;
import events.InputSystem;
import events.Trigger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import view.Utilities;
import view.enums.GUISize;
import view.enums.ViewInsets;

public class PropertyEventEditor extends EventEditorTab
{
	private final ScrollPane scrollPane;
	private final VBox pane;
	private final ResourceBundle myResources;
	private final LevelPicker levelPicker;
	
	private Text triggerText;
	private Text actionText;
	
	private Button chooseFileButton;
	private Button makeEventButton;
	private TableManager tableManager;
	
	EditorEvent masterEditor;
	
	private Trigger trigger;
	private Action action;
	private final InputSystem inputSystem;
	
	private String chosenEntityName;
	private IComponent chosenComponent;
	private SimpleObjectProperty property;
	
	private boolean triggerOK, actionOK;
	private ArrayList<ILevel> selectedLevelList;
	
	private final String language;
	
	public PropertyEventEditor(String language, ObservableList<IEntity> masterList, ObservableList<ILevel> levelList)
	{
		// this.levelList = levelList;
		levelPicker = new LevelPicker(language, levelList, this);
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		this.language = language;
		
		myResources = ResourceBundle.getBundle(language);
		
		triggerOK = false;
		actionOK = false;
		
		triggerText = new Text();
		actionText = new Text();
		chooseFileButton = new Button();
		makeEventButton = new Button();
		
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
		
		resetTrigger();
		resetAction();
		
		makeEventButton = Utilities.makeButton("MAKE EVENT!", e -> makeEvent());	// TODO resource
		makeEventButton.setDisable(true);
		
		container.getChildren().addAll(triggerText, actionText, makeEventButton);
		container.getChildren().add(makeGroovySide());
		pane.getChildren().add(container);
	}
	
	private void makeEvent()
	{
		// TODO: well, make Event
		// I think the Entity table now only shows entities through names
		// So the trigger has to be created here.
		
		// Cycle through all levels that were chosen, get their Event System
		// Make Triggers, and map them with action, on each of the Event Systems
	}
	
	public void populateLayout() 
	{
		makeTables();
		makeBottomPart();
	}

	public void triggerSet(String entityName, IComponent component, SimpleObjectProperty property)
	{
		String[] splitClassName = component.getClass().toString().split("\\.");
		
		triggerText.setText("TRIGGER: \n" + entityName + " - " + 
				splitClassName[splitClassName.length - 1] + " - " + 
				property.getName());	// TODO resource
		
		triggerOK = true;
		makeEventButton.setDisable( !triggerOK || !actionOK );
	}
	
	public void resetTrigger()
	{
		triggerText.setText(ResourceBundle.getBundle(language).getString("notYetDefined"));
		triggerOK = false;
	}
	
	public void resetAction()
	{
		actionText.setText(ResourceBundle.getBundle(language).getString("notYetDefined"));
		actionOK = false;
	}
	
	private void actionSet(String actionString, Action action)
	{
		this.action = action;
		actionText.setText("ACTION:\n" + actionString);	// TODO resource
		actionOK = true;
		
		makeEventButton.setDisable( !triggerOK || !actionOK );
	}
	
	@Override
	public ScrollPane getPane() 
	{
		return scrollPane;
	}

	@Override
	public void updateEditor() {}

	@Override
	public void choseLevels(List<ILevel> levels) 
	{
		tableManager.levelWasPicked(levels);
		this.selectedLevelList = (ArrayList<ILevel>) levels;
	}
	
}
