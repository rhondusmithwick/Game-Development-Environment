package view.editor.eventeditor.tabs;

import java.io.File;
import java.util.*;

import api.IEntity;

import api.ILevel;
import events.EventFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

public abstract class EventEditorTab extends Editor {
	private ObservableList<ILevel> levelList;
	private ArrayList<ILevel> chosenLevels;
	private LevelPicker levelPicker;
	private Text createdEventText;
    private Text addedParametersText;
	private Timer timer;
	private ResourceBundle myResources;
	private final EventFactory eventFactory = new EventFactory();
    private final String groovyPath = "resources/groovyScripts/";
    private final String language;
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
    private HBox parametersPane;
    private Map<String, Object> parameters;

	public EventEditorTab(String language, ObservableList<ILevel> levelList)
	{
		myResources = ResourceBundle.getBundle(language);
		this.language = language;
		this.levelList = levelList;
		entityForAnimation = null;
		levelPicker = new LevelPicker(language, levelList, this);
		chosenLevels = new ArrayList<ILevel>(levelList);
		actionReady = false;
		animationView = false;
		makeActionPane();
        parameters = new HashMap<>();
        addedParametersText = new Text("Groovy parameter added!");
	}

	public void flashCreatedEventText() {
		createdEventText.setOpacity(1);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				disappearText();
			}
		}, GUISize.EVENT_EDITOR_DISAPPEAR_SPEED.getSize(), GUISize.EVENT_EDITOR_DISAPPEAR_SPEED.getSize());
	}

    public void flashText(Text text) {
        text.setOpacity(1);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                disappearText(text);
            }
        }, GUISize.EVENT_EDITOR_DISAPPEAR_SPEED.getSize(), GUISize.EVENT_EDITOR_DISAPPEAR_SPEED.getSize());
    }

    private void disappearText(Text text) {
        text.setOpacity(text.getOpacity() - 0.02);
        if (text.getOpacity() <= 0 ) {
            timer.cancel();
        }
    }

	private void disappearText() {
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

	public void addEventToLevels(List<ILevel> levels, List<IEntity> entities, String triggerClassName,
								 String scriptPath, Object... args) {
		if (getChosenLevels().isEmpty()) {
			return;
		}
		levels.stream().forEach(level -> {
			addEventToLevel(level, entities, triggerClassName, scriptPath, args);
		});
	}

	public void addEventToLevel(ILevel level, List<IEntity> entities, String triggerClassName, String scriptPath,
								Object... args) {
        entities.stream().forEach(entity-> {
            parameters.put("entityID", entity.getID());
        });
		level.getEventSystem().registerEvent(
				eventFactory.createEvent(triggerClassName, groovyPath+scriptPath,
						parameters, args)
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
	
	public boolean getActionReady() {
        return actionReady;
	}

    public void addToMap(String a, String b) {
        parameters.put(a, b);
        flashText(addedParametersText);

        System.out.println(parameters);
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
		
		actionPane.getChildren().addAll(actionTypes, getActionButton, actionText, createdEventText);
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
			if ( entityForAnimation == null )
				getActionButton.setText(myResources.getString("chooseAnimation"));
			else
				getActionButton.setText("Get Animation for\n" + entityForAnimation.getName());	// TODO resource

			getActionButton.setOnAction(e -> getAnimation());
			getActionButton.setDisable(false);
		}
	}
	
	private void getAnimation() {
		System.out.println(entityForAnimation.getName());
		animationChooser = new AnimationChooser(entityForAnimation);
		animationName = animationChooser.initChooser();
		
		// flashCreatedEventText();
	}

    public void addParametersPane(VBox pane) {
        HBox parametersPane = new HBox(GUISize.EVENT_EDITOR_SUBPADDING.getSize());
        TextField keyField = new TextField();
        TextField valueField = new TextField();
        parametersPane.getChildren().add(keyField);
        parametersPane.getChildren().add(valueField);
        parametersPane.getChildren().add(ButtonFactory.makeButton("Add Groovy Parameter",
                e-> addToMap(keyField.getText(), valueField.getText())));
        parametersPane.getChildren().add(ButtonFactory.makeButton("Restart Groovy Parameters",
                e-> clearParameters()));
        pane.getChildren().add(parametersPane);
        addedParametersText = new Text(myResources.getString("eventMade"));
        addedParametersText.setOpacity(0);
    }

    public void clearParameters() {
        parameters.keySet().clear();
    }

    public Map<String, Object> getParams() {
        return parameters;
    }
	
	public VBox getActionPane()
	{
		return actionPane;
	}
	
	public String getActionScriptPath()
	{
		return actionScriptPath;
	}
	
	public void setEntityForAnimation(Entity entity) {
		this.entityForAnimation = entity;
		if (animationView) {
			getActionButton.setText("Get Animation for\n" + entity.getName());	// TODO resource
		}		
	}
	public abstract void actionOnChosenLevels(List<ILevel> levels);
}
