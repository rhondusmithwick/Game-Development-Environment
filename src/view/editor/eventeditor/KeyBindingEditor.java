package view.editor.eventeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import api.IEntity;
import api.ILevel;
import events.Action;
import events.KeyTrigger;
import events.PropertyTrigger;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import view.Utilities;
import view.editor.Editor;
import view.enums.GUISize;
import view.enums.ViewInsets;

// TODO put more things into the eventeditor abstract, and refactor this into better code plz
public class KeyBindingEditor extends EventEditorTab 
{
	private boolean keyListenerIsActive;
	private ScrollPane scrollPane;
	private HBox pane;
	
	private VBox inputBox;
	private KeyCode currentKey;
	private Button listenToKey;
	private Button chooseFileButton;
	private Text keyInputText;
	private final ResourceBundle myResources;
	private Action currentAction;
	private Button createEventButton;
	private LevelPicker levelPicker;
	private ObservableList<ILevel> levelList;
	private ArrayList<ILevel> selectedLevelList;
	
	private Text createdEventText;
	private Timer timer;
	
	
	public KeyBindingEditor(String language, ObservableList<ILevel> levelList)
	{
		levelPicker = new LevelPicker(language, levelList, this);
		this.levelList = levelList;
		scrollPane = new ScrollPane();
		myResources = ResourceBundle.getBundle(language);
		keyListenerIsActive = false;
		pane = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		inputBox = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		
		currentKey = null;
		pane.setOnKeyPressed(e -> keyWasPressed(e.getCode()));
		selectedLevelList = new ArrayList<ILevel>(levelList);
		timer = new Timer();
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
	
	private void makeInputBox()
	{
		listenToKey = Utilities.makeButton(myResources.getString("pressKey"), e -> listenButtonPress());
		
		keyInputText = new Text(myResources.getString("noKeyPressed"));	
		
		inputBox.getChildren().addAll(listenToKey, keyInputText);
		
		pane.getChildren().add(levelPicker.getPane());	// Make levelPicker
		pane.getChildren().add(inputBox);
	}
	
	private void makeGroovyBox()
	{
		VBox container = new VBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		// Adding now the Groovy Table
		chooseFileButton = Utilities.makeButton(myResources.getString("chooseGroovy"), e -> getFile());
		
		container.getChildren().addAll(chooseFileButton);
		
		pane.getChildren().add(container);
	}
	
	
	private void makeEventButton()
	{
		createEventButton = new Button("Create EVENT");	// TODO resource
		createdEventText = new Text("Event Created!");	// TODO resource
		
		createEventButton.setOnAction(e -> createEvent());
		createdEventText.setOpacity(0);
		
		pane.getChildren().add(createEventButton);
		pane.getChildren().add(createdEventText);
	}
	
	private void createEvent()
	{
		// I think the Entity table now only shows entities through names
		// So the trigger has to be created here.
		
		// Cycle through all levels that were chosen, get their Event System
		// Make Triggers, and map them with action, on each of the Event Systems
		
		for ( ILevel level: selectedLevelList )
		{
			level.getEventSystem().registerEvent(
					new KeyTrigger(currentKey.getName()), currentAction);
		}
		
		createdEventText.setOpacity(1);
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				disappearText();
			}
		}, 50, 50);		// TODO magic values
	}

	private void getFile()
	{
		File groovyFile = null;
		
		groovyFile = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("groovy", "*.groovy"), myResources.getString("selectGroovy"));
		if ( groovyFile != null )
		{
			currentAction = new Action(groovyFile.getAbsolutePath());
		}
	}
	
	private void disappearText()
	{
		createdEventText.setOpacity(createdEventText.getOpacity() - 0.02);
		if ( createdEventText.getOpacity() <= 0 )
		{
			timer.cancel();
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

	@Override
	public void populateLayout() 
	{
		makeInputBox();
		makeGroovyBox();
		makeEventButton();
		
		scrollPane.setContent(pane);
	}


	@Override
	public void updateEditor() {}

	@Override
	public void choseLevels(List<ILevel> levels) 
	{
		// TODO Auto-generated method stub
		
	}

}
