package view.editor.eventeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import events.Action;
import events.PropertyTrigger;
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
	
	private Text triggerText;
	private Text actionText;
	
	private Button chooseFileButton;
	private Button makeEventButton;
	private TableManager tableManager;
	
	EditorEvent masterEditor;
	
	private Action action;
	
	private String chosenEntityName;
	private IComponent chosenComponent;
	private SimpleObjectProperty<?> property;
	
	private boolean triggerOK, actionOK;
	
	private final String language;
	
	public PropertyEventEditor(String language, ObservableList<IEntity> masterList, ObservableList<ILevel> levelList)
	{
		super(language, levelList);
		
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
		
		tableManager = new TableManager(masterList, language, this);
		
		action = null;
		
		populateLayout();
		
		choseLevels(new ArrayList<ILevel>(levelList));
		
		scrollPane = new ScrollPane(pane);
	}

	private VBox makeGroovySide()
	{
		VBox container = new VBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		// Adding now the Groovy Table
		chooseFileButton = Utilities.makeButton(myResources.getString("chooseGroovy"), e -> getFile());
		
		container.getChildren().addAll(chooseFileButton);
		return container;
	}

	private void getFile()
	{
		File groovyFile = null;
		
		groovyFile = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("groovy", "*.groovy"), myResources.getString("selectGroovy"));
		if ( groovyFile != null )
		{
			actionSet(groovyFile.getName(), new Action(groovyFile.toString()));
		}
	}

	private void makeTables()
	{
		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());

		container.getChildren().add(getLevelPickerPane());
		container.getChildren().add(tableManager.getContainer());	
		
		pane.getChildren().add(container);
	}
	
	private void makeBottomPart()
	{
		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		
		resetTrigger();
		resetAction();
		
		makeEventButton = Utilities.makeButton(myResources.getString("makeEvent"), e -> createEvent());
		makeEventButton.setDisable(true);
		
		container.getChildren().addAll(triggerText, actionText, makeEventButton);
		container.getChildren().add(makeGroovySide());
		container.getChildren().add(getCreatedLevelText());
		pane.getChildren().add(container);
	}
	
	private void createEvent()
	{
		// I think the Entity table now only shows entities through names
		// So the trigger has to be created here.
		
		// Cycle through all levels that were chosen, get their Event System
		// Make Triggers, and map them with action, on each of the Event Systems
		
		if (getChosenLevels().isEmpty())
			return;
		
		for ( ILevel level: getChosenLevels() )
		{
			for (IEntity entity: level.getAllEntities())
			{
				if ( entity.getName().equals(chosenEntityName) )
				{
					level.getEventSystem().registerEvent(
							new PropertyTrigger(entity.getID(), chosenComponent.getClass(), property.getName()), 
							action);
				}
			}
		}
		
		flashCreatedEventText();
		
		triggerOK = false;
		actionOK = false;
	}
	
	public void populateLayout() 
	{
		makeTables();
		makeBottomPart();
	}

	public void triggerSet(String entityName, IComponent component, SimpleObjectProperty<?> property)
	{
		String[] splitClassName = component.getClass().toString().split("\\.");
		
		triggerText.setText(myResources.getString("trigger") + 
				entityName + " - " + 
				splitClassName[splitClassName.length - 1] + " - " + 
				property.getName());	
		
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
		actionText.setText(myResources.getString("action") + actionString);
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
	public void actionOnChosenLevels(List<ILevel> levels) 
	{
		tableManager.levelWasPicked(levels);
	}
	
}
