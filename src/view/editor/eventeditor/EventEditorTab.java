package view.editor.eventeditor;

import java.util.*;

import api.IEntity;
import com.sun.java.accessibility.util.GUIInitializedListener;

import api.ILevel;
import events.EventFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import view.editor.Editor;
import view.enums.GUISize;

/**
 * Author: Alan, Carolyn
 * Integr
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
	
	public EventEditorTab(String language, ObservableList<ILevel> levelList)
	{
		myResources = ResourceBundle.getBundle(language);
		
		this.levelList = levelList;
		levelPicker = new LevelPicker(language, levelList, this);
		chosenLevels = new ArrayList<ILevel>(levelList);
		createdEventText = new Text(myResources.getString("eventMade"));
		createdEventText.setOpacity(0);
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
	
	public Text getCreatedEventText()
	{
		return createdEventText;
	}
	
	public ArrayList<ILevel> getChosenLevels() {
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
		level.getEventSystem().registerEvent(
				eventFactory.createEvent(triggerClassName, groovyPath+scriptPath,
                        makeEntityParamMap(entities) , args)
		);
	}

    public Map<String, String> makeEntityParamMap(List<IEntity> entities) {
        Map<String, String> entityParams = new HashMap<>();
        entities.stream().forEach(entity -> {
            entityParams.put("entityID", entity.getID());
        });
        return entityParams;
    }

	public abstract void actionOnChosenLevels(List<ILevel> levels);
}
