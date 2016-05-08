// This entire file is part of my masterpiece.
// Alan Cavalcanti

/*
 * This was chosen as part of my masterpiece. I noticed that both KeyBindingEditor and 
 * TimeEventEditor were very similar. So I renamed some classes and methods to make them
 * more generic, polished them up a bit, and created a new level of the Editor Hierarchy,
 * So to lessen the size of both classes, and make them have less repeated code :)
 */
package view.editor.eventeditor.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import api.IEntity;
import api.ILevel;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.editor.eventeditor.tables.EventViewManager;
import view.editor.eventeditor.tables.TabWithViewerManager;
import view.utilities.ButtonFactory;

/**
 * 2nd Level of the Editor hierarchy. It groups up the Event Editor Tabs 
 * that contain a simple Trigger creation, and an Event Viewer.
 * 
 * @author Alankmc
 *
 */
public abstract class EventEditorWithViewer extends EventEditorTab 
{
	private static final int FONT_SIZE = 20;
	private List<IEntity> chosenEntities;
	private final String triggerType;
	private final EventViewManager eventViewManager;
	private final TabWithViewerManager tableManager;
	private Text chosenEntityText;
	private final Text chosenEntityTitle;
	private final ResourceBundle myResources;
	private ScrollPane chosenEntityBox;
	private Button createEventButton;
	
	public EventEditorWithViewer(String language, ObservableList<ILevel> levelList, String triggerType) 
	{
		super(language, levelList);
		myResources = ResourceBundle.getBundle(language);
		this.triggerType = triggerType;
		this.eventViewManager = new EventViewManager(language);   

		eventViewManager.levelWasPicked(new ArrayList<>(levelList));
		chosenEntityTitle = new Text(myResources.getString("pickedEntities"));
		chosenEntityText = new Text(myResources.getString("noEntities"));
        chosenEntityTitle.setFont(new Font(FONT_SIZE));    
        chosenEntities = new ArrayList<>();
        
		chosenEntityBox = new ScrollPane(new VBox(chosenEntityTitle, chosenEntityText));
		createEventButton = new Button(myResources.getString("makeEvent"));
		tableManager = new TabWithViewerManager(language, this);
		
		fillChosenEntityBox();
	}
	
	/* Will pass down these JavaFX elements so they can be added to the panes
	 * in the implementations of this class */
	/**
	 * Returns the button that creates Event. 
	 * Important!: Its handler has to be specified in the implementation classes.
	 * @return Button createEventButton
	 */
	public Button getCreateEventButton()
	{
		return createEventButton;
	}
	/**
	 * Returns the ScrollPane that contains the Text showing
	 * all events affected by the ChoiceBox entity picker
	 * @return ScrollPane chosenEntityBox
	 */
	public ScrollPane getEntityPickerPane()
	{
		return chosenEntityBox;
	}
	/**
	 * @return Pane eventViewer
	 */
	public Pane getEventViewer()
	{
		return eventViewManager.getPane();
	}
	/**
	 * @return HBox tableManager's container
	 */
	public HBox getTable()
	{
		return tableManager.getContainer();
	}
	/* ---------*/
	
	
    public void createEvent(Object ... args) 
    {    	
    	addEventToLevels(getChosenLevels(), chosenEntities, triggerType, args);

        flashText(getEventCreatedText());
        eventViewManager.updateTable();
    }
    
    /**
     * Takes in the chosenEntities, and updates the text in the chosenEntityBox
     */
    private void fillChosenEntityBox () {
        if (chosenEntities.isEmpty()) {
            chosenEntityText.setText(myResources.getString("noEntities"));    
        } else {
            String entityString = "";
            for (IEntity entity : chosenEntities) {
                entityString += entity.getName() + "\n";
            }

            chosenEntityText.setText(entityString);
        }
    }
    
    
    @Override
    public void actionOnChosenLevels (List<ILevel> levels) {
        tableManager.levelWasPicked(levels);
        eventViewManager.levelWasPicked(levels);
    }
    
    @Override
    public void choseEntity (List<IEntity> entities) {
        this.chosenEntities = entities;

        fillChosenEntityBox();
    }

    
}
