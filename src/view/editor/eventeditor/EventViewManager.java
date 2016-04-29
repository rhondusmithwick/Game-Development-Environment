package view.editor.eventeditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import api.ILevel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.entity.Entity;

public class EventViewManager extends TableManager
{
	private HBox pane;
	private SimpleStringTable levelTable;
	private SimpleStringTable keyTable;
	private SimpleStringTable actionTable;
	private EventParser parser;

	private ArrayList<String> levelStrings;
	private ArrayList<String> keyStrings;
	private ArrayList<String> actionStrings;
	private List<ILevel> levels;


	public EventViewManager()
	{
		levels = new ArrayList<ILevel>();
		levelTable = new SimpleStringTable(this, "Levels");	// TODO resource
		keyTable = new SimpleStringTable(this, "Trigger");	// TODO resource
		actionTable = new SimpleStringTable(this, "Action");	// TODO resource
		
		levelStrings = new ArrayList<String>();
		keyStrings = new ArrayList<String>();
		actionStrings = new ArrayList<String>();
		parser = new EventParser();
		pane = new HBox();

		pane.getChildren().addAll(levelTable.getTable(), keyTable.getTable(), actionTable.getTable());
	}
	// TODO Other table manager. More abstracts
	@Override
	public void entityWasClicked(Entity entity) 
	{
	}
	
	public void updateTable()
	{
		levelWasPicked(levels);
	}

	@Override
	public void levelWasPicked(List<ILevel> levels) 
	{
		this.levels = levels;
		
		HashMap<String, String> events;

		levelStrings.clear();
		keyStrings.clear();
		actionStrings.clear();
		levelTable.refreshTable();
		keyTable.refreshTable();
		actionTable.refreshTable();
		
		System.out.println("===== In EventViewManager");
		
		for (ILevel level: levels)
		{
			System.out.println(level.getName() + " events:");
			events = parser.parse(level.getEventSystem().getEventsAsString());

			if ( events == null )
			{
				continue;
			}

			for (String keyTrigger: events.keySet())
			{
				levelStrings.add(level.getName());
				keyStrings.add(keyTrigger);
				actionStrings.add(events.get(keyTrigger));
			}
		}
		
		levelTable.fillEntries(levelStrings);
		keyTable.fillEntries(keyStrings);
		actionTable.fillEntries(actionStrings);
	}
	
	public Pane getPane()
	{
		return pane;
	}
	
	
}
