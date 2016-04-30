package view.editor.eventeditor;

import java.util.List;

import api.ILevel;
import model.entity.Entity;

public abstract class TableManager 
{
	public abstract void entityWasClicked(Entity entity);
	public abstract void levelWasPicked(List<ILevel> levels);
}
