package view.editor.eventeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

public class PropertyEventEditor extends EventEditorTab
{
	private final ScrollPane scrollPane;
	private final VBox pane;
	private final ResourceBundle myResources;
	private EventViewManager eventViewManager;
	private Text triggerText;
	private Text actionText;
	private String actionScriptPath;
	private Button chooseFileButton;
	private Button makeEventButton;
	private PropertyTableManager tableManager;
	private List<IEntity> chosenEntities;
	
	EditorEvent masterEditor;
	
	private String chosenEntityName;
	private IComponent chosenComponent;
	private SimpleObjectProperty<?> chosenProperty;
	
	private boolean triggerOK, actionOK;
	
	private final String language;
	
	public PropertyEventEditor(String language, ObservableList<ILevel> levelList)
	{
		super(language, levelList);
		
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		this.language = language;
		eventViewManager = new EventViewManager();
		
		myResources = ResourceBundle.getBundle(language);
		
		triggerOK = false;
		actionOK = false;
		
		triggerText = new Text();
		actionText = new Text();
		
		chooseFileButton = new Button();
		makeEventButton = new Button();
		
		tableManager = new PropertyTableManager(language, this);
		
		populateLayout();
		
		choseLevels(new ArrayList<ILevel>(levelList));
		eventViewManager.levelWasPicked(new ArrayList<ILevel>(levelList));
		scrollPane = new ScrollPane(pane);
	}

	private VBox makeGroovySide()
	{
		VBox container = new VBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		// Adding now the Groovy Table
		chooseFileButton =ButtonFactory.makeButton(myResources.getString("chooseGroovy"), e -> getFile());
		
		container.getChildren().addAll(chooseFileButton);
		return container;
	}

	private void getFile()
	{
		File groovyFile = null;
		
		groovyFile = FileUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("groovy", "*.groovy"), myResources.getString("selectGroovy"));
		if ( groovyFile != null )
		{
			String[] splits = groovyFile.getPath().split("voogasalad_MakeGamesGreatAgain/");			
			
			actionSet(groovyFile.getName());
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
		
		makeEventButton = ButtonFactory.makeButton(myResources.getString("makeEvent"), e -> createEvent());
		makeEventButton.setDisable(true);
		
		container.getChildren().addAll(triggerText, actionText, makeEventButton);
		container.getChildren().add(makeGroovySide());
		container.getChildren().add(getCreatedEventText());
		pane.getChildren().add(container);
		pane.getChildren().add(eventViewManager.getPane());
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
					addEventToLevel(level, chosenEntities, "PropertyTrigger", actionScriptPath, entity.getID(),
									chosenComponent.getClass(), chosenProperty);
				}
			}
		}


		// Carolyn's refactoring
//		if (getChosenLevels().isEmpty()) return;
//		getChosenLevels().stream().forEach(level ->
//				level.getAllEntities().parallelStream().
//						filter(entity-> entity.getName().equals(chosenEntityName)).
//						forEach(entity-> {
//							addEventToLevel(level, "events.PropertyTrigger", entity.getID(),
//									chosenComponent.getClass(), property.get());
//						})
//		);

		
		flashCreatedEventText();
		eventViewManager.updateTable();
		triggerOK = false;
		actionOK = false;
	}

	public void choseEntity(List<IEntity> entities)
	{
		this.chosenEntities = entities;
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
		
		chosenEntityName = entityName;
		chosenComponent = component;
		chosenProperty = property;
		
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
	
	private void actionSet(String actionScriptPath)
	{
		this.actionScriptPath = actionScriptPath;
		actionText.setText(myResources.getString("action") + actionScriptPath);
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
		eventViewManager.levelWasPicked(levels);
	}
	
}
