package view.editor.eventeditor;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import api.ILevel;
import events.Action;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import view.Utilities;
import view.enums.GUISize;
import view.enums.ViewInsets;

// TODO put Action setting and file picker on abstract
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
	private ResourceBundle myResources;
	private Action action;
	private Button createEventButton;
	private String language;
	
	// TODO test
	private Button getEventsString;
	
	private Text actionScriptText;
	
	public KeyBindingEditor(String language, ObservableList<ILevel> levelList)
	{
		super(language, levelList);
		this.language = language;
		scrollPane = new ScrollPane();
		myResources = ResourceBundle.getBundle(language);
		keyListenerIsActive = false;
		pane = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		inputBox = new VBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		
		action = null;
		currentKey = null;
		actionScriptText = new Text(ResourceBundle.getBundle(language).getString("notYetDefined"));
		pane.setOnKeyPressed(e -> keyWasPressed(e.getCode()));
		
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
		
		pane.getChildren().add(getLevelPickerPane());	// Make levelPicker
		pane.getChildren().add(inputBox);
	}
	
	private void makeGroovyBox()
	{
		VBox container = new VBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		// Adding now the Groovy Table
		chooseFileButton = Utilities.makeButton(myResources.getString("chooseGroovy"), e -> getFile());
		
		container.getChildren().addAll(chooseFileButton, actionScriptText);
		
		pane.getChildren().add(container);
	}
	
	
	private void makeEventButton()
	{
		createEventButton = Utilities.makeButton(myResources.getString("makeEvent"), e -> createEvent());
		
		createEventButton.setOnAction(e -> createEvent());
		
		// TODO test
		getEventsString = Utilities.makeButton("TEST", e -> printEvents());
		
		pane.getChildren().addAll(createEventButton, getCreatedLevelText(), getEventsString);
	}
	
	// TODO test
	private void printEvents()
	{
		for ( ILevel level: getChosenLevels() )
		{
			System.out.println(level.getName());
			System.out.println(level.getEventSystem().returnEventsAsString());
		}
	}
	
	private void createEvent()
	{
		if (getChosenLevels().isEmpty())
			return;

		addEventToLevels(getChosenLevels(), "events.KeyTrigger", actionScriptText.getText(), currentKey.getName());
//		for ( ILevel level: getChosenLevels() )
//		{
//			level.getEventSystem().registerEvent(
//					new KeyTrigger(currentKey.getName()), action);
//		}
		
		flashCreatedEventText();
		
		myResources = ResourceBundle.getBundle(language);
	}

	private void getFile()
	{
		File groovyFile = null;
		
		groovyFile = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("groovy", "*.groovy"), myResources.getString("selectGroovy"));
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
	
	private void actionSet(String actionString)
	{
		actionScriptText.setText(myResources.getString("action") + actionString);
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
	public void actionOnChosenLevels(List<ILevel> levels) 
	{
		
	}

}
