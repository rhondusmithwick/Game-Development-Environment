package view.editor.eventeditor.tabs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.java.accessibility.util.GUIInitializedListener;
import com.sun.webkit.Utilities;

import api.ILevel;
import events.EventFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.entity.Entity;
import view.editor.Editor;
import view.editor.eventeditor.AnimationChooser;
import view.editor.eventeditor.LevelPicker;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.ComboFactory;
import view.utilities.FileUtilities;

/**
 * Author: Alankmc, Carolyn
 * Integration portion of the frontend for events system. 
 */

public abstract class EventEditorTab extends Editor
{
	private ObservableList<ILevel> levelList;
	private ArrayList<ILevel> chosenLevels;
	private LevelPicker levelPicker;
	private Text createdEventText;
	private Timer timer;
	private ResourceBundle myResources;
	private final EventFactory eventFactory = new EventFactory();
    private final String groovyPath = "resources/groovyScripts/";
    private final String language;
    // Things for ActionPane
    private Text actionText;
    private VBox actionPane;
	private ComboBox<String> actionTypes;
	private String actionScriptPath;
	private Button getActionButton;
	private boolean actionReady;
	private AnimationChooser animationChooser;
	private Entity entityForAnimation;
	private String animationName;
	private boolean animationView;
	public EventEditorTab(String language, ObservableList<ILevel> levelList)
	{
		myResources = ResourceBundle.getBundle(language);
		this.language = language;
		this.levelList = levelList;
		levelPicker = new LevelPicker(language, levelList, this);
		chosenLevels = new ArrayList<ILevel>(levelList);
		actionReady = false;
		animationView = false;
		makeActionPane();
	}

	public void flashCreatedEventText()
	{
		createdEventText.setOpacity(1);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				disappearText();
			}
		}, GUISize.EVENT_EDITOR_DISAPPEAR_SPEED.getSize(), GUISize.EVENT_EDITOR_DISAPPEAR_SPEED.getSize());
	}
	
	private void disappearText()
	{
		createdEventText.setOpacity(createdEventText.getOpacity() - 0.02);
		if ( createdEventText.getOpacity() <= 0 )
		{
			timer.cancel();
		}
	}
	
	public ScrollPane getLevelPickerPane()
	{
		return levelPicker.getPane();
	}
	
	public void choseLevels(List<ILevel> levels)
	{
		actionOnChosenLevels(levels);
		this.chosenLevels = (ArrayList<ILevel>) levels;
	}
	
	public ArrayList<ILevel> getChosenLevels()
	{
		return chosenLevels;
	}

	public void addEventToLevels(List<ILevel> levels, String triggerClassName, String scriptPath, Object... args) {
		if (getChosenLevels().isEmpty()) {
			return;
		}
		levels.stream().forEach(level -> {
			level.getEventSystem().registerEvent(
					eventFactory.createEvent(triggerClassName, groovyPath+scriptPath, args)
			);
		});
	}

	public void addEventToLevel(ILevel level, String triggerClassName, String scriptPath, Object... args) {
		level.getEventSystem().registerEvent(
				eventFactory.createEvent(triggerClassName, groovyPath+scriptPath, args)
		);
	}

	private void actionSet(String actionScriptPath)
	{
		this.actionScriptPath = actionScriptPath;
		actionText.setText(myResources.getString("action") + actionScriptPath);
	}
	
	public void getFile()
	{
		File groovyFile = null;
		
		groovyFile = FileUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("groovy", "*.groovy"),
				myResources.getString("selectGroovy"), DefaultStrings.RESOURCES.getDefault());
		if ( groovyFile != null )
		{
			String[] splits = groovyFile.getPath().split("voogasalad_MakeGamesGreatAgain/");			
			actionReady = true;
			actionSet(groovyFile.getName());
		}
	}
	
	public boolean getActionReady()
	{
		return actionReady;
	}
	
	private void makeActionPane()
	{
		actionPane = new VBox(GUISize.EVENT_EDITOR_SUBPADDING.getSize());
		actionTypes = ComboFactory.makeComboBox(myResources.getString("chooseActionType"), 
				new ArrayList<String>(Arrays.asList(myResources.getString("getFromGroovy"), myResources.getString("getFromAnimation"))),
				e -> choseActionType(actionTypes.getValue()));
		
		// Might break here?
		getActionButton = ButtonFactory.makeButton(myResources.getString("noAction"), null);
		getActionButton.setDisable(true);
		
		actionText = new Text(ResourceBundle.getBundle(language).getString("notYetDefined"));

		createdEventText = new Text(myResources.getString("eventMade"));
		createdEventText.setOpacity(0);
		
		// actionPane.getChildren().addAll(actionTypes, getActionButton, actionText, createdEventText);
		actionPane.getChildren().add(actionTypes);
		actionPane.getChildren().add(getActionButton);
		actionPane.getChildren().add(actionText);
		actionPane.getChildren().add(createdEventText);
	}
	
	private void choseActionType(String type)
	{
		if ( type.equals(myResources.getString("getFromGroovy")) )
		{
			getActionButton.setText(myResources.getString("chooseGroovy"));
			getActionButton.setOnAction(e -> getFile());
			getActionButton.setDisable(false);
			animationView = false;
		}
		else if (type.equals(myResources.getString("getFromAnimation")))
		{
			animationView = true;
			getActionButton.setText(myResources.getString("chooseAnimation"));
			getActionButton.setOnAction(e -> getAnimation());
			getActionButton.setDisable(false);
		}
	}
	
	private void getAnimation()
	{
		animationChooser = new AnimationChooser(entityForAnimation);
		animationName = animationChooser.initChooser();
		// flashCreatedEventText();
	}
	
	public VBox getActionPane()
	{
		return actionPane;
	}
	
	public String getActionScriptPath()
	{
		return actionScriptPath;
	}
	
	public void setEntityForAnimation(Entity entity)
	{
		this.entityForAnimation = entity;

		// TODO: better if
		if (animationView)
		{
			getActionButton.setText("Get Animation for\n" + entity.getName());	// TODO resource
		}		
	}
	public abstract void actionOnChosenLevels(List<ILevel> levels);
}
