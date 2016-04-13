package view.editor.eventeditor;
import javafx.beans.property.SimpleStringProperty;
import model.entity.*;

public class EntityEntry
{
	Entity entity;
	private SimpleStringProperty name;
	
	public EntityEntry(Entity entity)
	{
		this.entity = entity;
		name = new SimpleStringProperty(entity.getName());
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public String getName()
	{
		return name.get();
	}
}
