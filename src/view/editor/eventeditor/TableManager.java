package view.editor.eventeditor;

import api.ISerializable;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import model.entity.Entity;

public class TableManager 
{
	private HBox container;
	private EntityTable entityTable;
	private ComponentTable componentTable;
	
	public TableManager(ObservableList<ISerializable> entityList)
	{
		container = new HBox();
		entityTable = new EntityTable(entityList, this);
		componentTable = new ComponentTable();
		
		fillLayout();
	}
	
	public void entityWasClicked(Entity entity)
	{
		componentTable.fillEntries(entity);
	}
	
	private void fillLayout()
	{
		container.getChildren().addAll(entityTable.getTable(), componentTable.getTable());
	}
	
	public HBox getContainer()
	{
		return container;
	}
	
	
}
