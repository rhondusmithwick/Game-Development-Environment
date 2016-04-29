package view.editor.eventeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import api.IEntity;
import api.ILevel;
import events.Action;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

// TODO put Action setting and file picker on abstract
public class KeyBindingEditor extends EventEditorTab 
{
	private boolean keyListenerIsActive;
	private ScrollPane scrollPane;
	private ScrollPane chosenEntityBox;
	private Text chosenEntityText;
	private Text chosenEntityTitle;
	
	private VBox pane;
	
	private KeyCode currentKey;
	private Button listenToKey;
	private Button chooseFileButton;
	private Text keyInputText;
	private ResourceBundle myResources;
	private Action action;
	private Button createEventButton;
	private String language;
	private String actionScriptPath;
	private KeyBindingTableManager tableManager;
	private EventViewManager eventViewManager;
	
	// TODO test
	private Button getEventsString;

	private Text actionText;
	private ArrayList<IEntity> chosenEntities;
	
	public KeyBindingEditor(String language, ObservableList<ILevel> levelList)
	{
		super(language, levelList);
		this.language = language;
		
		eventViewManager = new EventViewManager();
		
		chosenEntityTitle = new Text("== PICKED ENTITIES ==\n");	// TODO resource
		chosenEntityTitle.setFont(new Font(20));	// TODO enum...?
		
		chosenEntities = new ArrayList<IEntity>();
		scrollPane = new ScrollPane();
		myResources = ResourceBundle.getBundle(language);
		keyListenerIsActive = false;
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);

		tableManager = new KeyBindingTableManager(language, this);
		
		action = null;
		currentKey = null;
		actionText = new Text(ResourceBundle.getBundle(language).getString("notYetDefined"));
		pane.setOnKeyPressed(e -> keyWasPressed(e.getCode()));
		
		choseLevels(new ArrayList<ILevel>(levelList));
		eventViewManager.levelWasPicked(new ArrayList<ILevel>(levelList));
		
		populateLayout();
	}
	
	private void keyWasPressed(KeyCode code)
	{
		if (!keyListenerIsActive)
			return;
		
		currentKey = code;
		keyInputText.setText(myResources.getString("key")+ code.getName());	
		keyListenerIsActive = false;
	}

	// TODO test
	private void printEvents()
	{
		for ( ILevel level: getChosenLevels() )
		{
			System.out.println(level.getName());
			System.out.println(level.getEventSystem().getEventsAsString());
		}
	}
	
	private void createEvent()
	{
		if (getChosenLevels().isEmpty())
			return;
		addEventToLevels(getChosenLevels(), "KeyTrigger", actionScriptPath, currentKey.getName());
		flashCreatedEventText();
		eventViewManager.updateTable();
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
	
	private void listenButtonPress()
	{
		keyListenerIsActive = true;
		keyInputText.setText("Listening....");
	}
	
	@Override
	public ScrollPane getPane() 
	{
		return scrollPane;
	}
	
	private void actionSet(String actionScriptPath)
	{
		this.actionScriptPath = actionScriptPath;
		actionText.setText(myResources.getString("action") + actionScriptPath);
	}
	
	public void makeUpperSide()
	{
		HBox container = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		VBox innerContainer = new VBox(GUISize.EVENT_EDITOR_SUBPADDING.getSize());	// TODO magic value
		
		listenToKey = ButtonFactory.makeButton(myResources.getString("pressKey"), e -> listenButtonPress());
		
		keyInputText = new Text(myResources.getString("noKeyPressed"));	
		
		chooseFileButton = ButtonFactory.makeButton(myResources.getString("chooseGroovy"), e -> getFile());
		
		createEventButton = ButtonFactory.makeButton(myResources.getString("makeEvent"), e -> createEvent());
		
		createEventButton.setOnAction(e -> createEvent());
		
		innerContainer.getChildren().addAll(listenToKey, keyInputText, chooseFileButton, actionText, createEventButton, getCreatedEventText());
		
		chosenEntityText = new Text();
		
		chosenEntityBox = new ScrollPane(new VBox(chosenEntityTitle, chosenEntityText));
		
		fillChosenEntityBox();
		container.getChildren().addAll(getLevelPickerPane(), tableManager.getContainer(), chosenEntityBox, innerContainer );
		
		pane.getChildren().add(container);
	}

	
	public void makeBottomSide()
	{
		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		
		container.getChildren().add(eventViewManager.getPane());
		/*
		// TODO test
		getEventsString = Utilities.makeButton("TEST", e -> printEvents());
		*/
		
		pane.getChildren().add(container);
	}
	
	@Override
	public void populateLayout() 
	{
		makeUpperSide();
		makeBottomSide();
		
		scrollPane.setContent(pane);
	}

	
	private void fillChosenEntityBox()
	{
		if (chosenEntities.isEmpty())
		{
			chosenEntityText.setText("No Entities Selected!");	// TODO resource
		}
		else
		{
			String entityString = "";
			for (IEntity entity: chosenEntities)
			{
				entityString += entity.getName() + "\n";
			}
			
			chosenEntityText.setText(entityString);
		}
	}
	
	public void choseEntity(ArrayList<IEntity> entities)
	{
		this.chosenEntities = entities;
	
		fillChosenEntityBox();
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
