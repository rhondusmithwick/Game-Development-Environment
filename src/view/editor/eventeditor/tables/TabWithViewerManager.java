package view.editor.eventeditor.tables;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import model.entity.Entity;
import view.editor.eventeditor.tabs.EventEditorTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Table Manager that manages the table in the KeyBindingEditor tab.
 *
 * @author Alankmc
 */
public class TabWithViewerManager extends TableManager {
    private final HBox container;
    private final EventEditorTab editor;
    private final ArrayList<IEntity> pickedEntitiesForEvent;
    private final ObservableList<IEntity> selectedEntitiesFromLevel;
    private EntityTable entityTable;
    private Entity entity;
    private IComponent component;

    public TabWithViewerManager (String language, EventEditorTab editor) {
        container = new HBox();
        String language1 = language;
        selectedEntitiesFromLevel = FXCollections.observableArrayList();


        try {
            entityTable = new EntityTable(selectedEntitiesFromLevel, this, language);
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        pickedEntitiesForEvent = new ArrayList<>();

        this.editor = editor;

        fillLayout();
    }

    /**
     * When Entity is clicked, add it into the list of chosen Entities. 
     * The Events are created for every entity that is selected.
     * 
     * @param Entity entity
     */
    public void entityWasClicked (Entity entity) {
        editor.setEntityForAnimation(entity);

        if (pickedEntitiesForEvent.contains(entity)) {
            pickedEntitiesForEvent.remove(entity);
        } else {
            pickedEntitiesForEvent.add(entity);
        }

        editor.choseEntity(pickedEntitiesForEvent);
    }

    /**
     * Choose the Entities that are on the selected Levels, and display them on the Table.
     * 
     * @param List<ILevel> levels
     */
    public void levelWasPicked (List<ILevel> levels) {
        selectedEntitiesFromLevel.clear();

        for (ILevel level : levels) {
            selectedEntitiesFromLevel.addAll(level.getAllEntities());
        }

        entityTable.levelWasPicked(selectedEntitiesFromLevel);
    }

    
    public void allBoxChanged (String event) {
        // System.out.println(event);
    }
    
    private void fillLayout () {
        container.getChildren().addAll(entityTable.getTable());
    }

    public HBox getContainer () {
        return container;
    }
}
