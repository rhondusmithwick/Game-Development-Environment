package view.editor.eventeditor.tables;

import api.IEntity;
import api.ISerializable;
import javafx.collections.ObservableList;
import model.entity.Entity;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Entity table.
 *
 * @author Alankmc
 */
public class EntityTable extends Table {
    private ArrayList<String> entityNames;

    /**
     * Constructor. Throws the exceptions in case the given method is not present in the manager class.
     *
     * @param ObservableList<IEntity> entities
     * @param TableManger             manager
     * @param String                  language
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public EntityTable (ObservableList<IEntity> entities, TableManager manager, String language) throws NoSuchMethodException, SecurityException {
        // Passes the manager's pickEntity
        super(manager, ResourceBundle.getBundle(language).getString("pickEntity"),
                manager.getClass().getMethod("entityWasClicked", Entity.class),
                Entity.class);

        entityNames = new ArrayList<String>();

        fillEntries(entities);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void fillEntries (Object dataHolder) {
        for (ISerializable entity : (ObservableList<ISerializable>) dataHolder) {
            if (entityNames.contains(((Entity) entity).getName()))
                continue;
            else {
                entityNames.add(((Entity) entity).getName());
                getEntries().add(new Entry(entity, ((Entity) entity).getName()));
            }
        }
    }

    /**
     * Updates the entities being shown, given the Levels.
     * The selectedEntities are chosen from the selected level list in the manager.
     *
     * @param ObservableList<IEntity> selectedEntities
     */
    public void levelWasPicked (ObservableList<IEntity> selectedEntities) {
        refreshTable();
        entityNames.clear();
        if (!selectedEntities.isEmpty())
            fillEntries(selectedEntities);
    }
}
