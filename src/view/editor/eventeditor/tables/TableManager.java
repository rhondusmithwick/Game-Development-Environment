package view.editor.eventeditor.tables;

import java.util.List;

import api.ILevel;
import model.entity.Entity;

/**
 * Table manager abstract. All managers will react a certain way when an entity is clicked, or a level is picked.
 * @author Alankmc
 *
 */
public abstract class TableManager 
{
	public abstract void entityWasClicked(Entity entity);
	public abstract void levelWasPicked(List<ILevel> levels);
}
