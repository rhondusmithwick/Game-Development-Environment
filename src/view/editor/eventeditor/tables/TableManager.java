package view.editor.eventeditor.tables;

import api.ILevel;
import model.entity.Entity;

import java.util.List;

/**
 * Table manager abstract. All managers will react a certain way when an entity is clicked, or a level is picked.
 *
 * @author Alankmc
 */
public abstract class TableManager {
    public abstract void entityWasClicked (Entity entity);

    public abstract void levelWasPicked (List<ILevel> levels);
}
