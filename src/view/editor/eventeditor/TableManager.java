package view.editor.eventeditor;

import api.IComponent;
import api.ISerializable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import model.entity.Entity;

public class TableManager 
{
	private HBox container;
	private EntityTable entityTable;
	private ComponentTable componentTable;
	private PropertyTable propertyTable;
	
	public TableManager(ObservableList<ISerializable> entityList)
	{
		
		container = new HBox();
		entityTable = new EntityTable(entityList, this);
		componentTable = new ComponentTable(this);
		propertyTable = new PropertyTable(this);
		
		fillLayout();
	}
	
	public void entityWasClicked(Entity entity)
	{
		componentTable.fillEntries(entity);
	}
	
	public void componentWasClicked(IComponent component)
	{
		propertyTable.fillEntries(component);
	}
	
	public void propertyWasClicked(SimpleObjectProperty property)
	{
		
	}
	
	private void fillLayout()
	{
		container.getChildren().addAll(entityTable.getTable(), componentTable.getTable(), propertyTable.getTable());
	}
	
	public HBox getContainer()
	{
		return container;
	}
	
	
}
