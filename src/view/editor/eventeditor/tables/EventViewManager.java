package view.editor.eventeditor.tables;

import api.ILevel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.entity.Entity;
import view.editor.eventeditor.EventParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Table manager that updates the table that contains the madeEvents.
 *
 * @author Alankmc
 */
public class EventViewManager extends TableManager {
    private final HBox pane;
    private final SimpleStringTable levelTable;
    private final SimpleStringTable keyTable;
    private final SimpleStringTable actionTable;
    private final EventParser parser;

    private final ArrayList<String> levelStrings;
    private final ArrayList<String> keyStrings;
    private final ArrayList<String> actionStrings;
    private List<ILevel> levels;


    public EventViewManager () {
        levels = new ArrayList<>();
        levelTable = new SimpleStringTable(this, "Levels");    // TODO resource
        keyTable = new SimpleStringTable(this, "Trigger");    // TODO resource
        actionTable = new SimpleStringTable(this, "Action");    // TODO resource

        levelStrings = new ArrayList<>();
        keyStrings = new ArrayList<>();
        actionStrings = new ArrayList<>();
        parser = new EventParser();
        pane = new HBox();

        pane.getChildren().addAll(levelTable.getTable(), keyTable.getTable(), actionTable.getTable());
    }

    // TODO Other table manager. More abstracts
    @Override
    public void entityWasClicked (Entity entity) {
    }

    public void updateTable () {
        levelWasPicked(levels);
    }

    @Override
    public void levelWasPicked (List<ILevel> levels) {
        this.levels = levels;

        HashMap<String, String> events;

        levelStrings.clear();
        keyStrings.clear();
        actionStrings.clear();
        levelTable.refreshTable();
        keyTable.refreshTable();
        actionTable.refreshTable();

        System.out.println("===== In EventViewManager");

        for (ILevel level : levels) {
            System.out.println(level.getName() + " events:");
            events = parser.parse(level.getEventSystem().getEventsAsString());

            if (events == null) {
                continue;
            }

            for (String keyTrigger : events.keySet()) {
                levelStrings.add(level.getName());
                keyStrings.add(keyTrigger);
                actionStrings.add(events.get(keyTrigger));
            }
        }

        levelTable.fillEntries(levelStrings);
        keyTable.fillEntries(keyStrings);
        actionTable.fillEntries(actionStrings);
    }

    public Pane getPane () {
        return pane;
    }


}
