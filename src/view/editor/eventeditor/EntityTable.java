package view.editor.eventeditor;
import java.util.HashMap;

import api.ISerializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.Entity;


public class EntityTable
{
	private TableView<EntityEntry> table;
	private TableColumn<EntityEntry, String> column;
	private ObservableList<ISerializable> entities;
	private ObservableList<EntityEntry> entityEntries;
	private TableManager manager;
	
	public EntityTable(ObservableList<ISerializable> entities, TableManager manager)
	{
		this.entities = entities;
		this.manager = manager;
		table = new TableView<EntityEntry>();
		table.setEditable(true);
		column = new TableColumn<EntityEntry, String>("Pick Entity");	// TODO resource file
		column.setCellValueFactory( new PropertyValueFactory<EntityEntry,String>("name") );
		
		
		entityEntries = FXCollections.observableArrayList();
		fillEntries();
		table.getColumns().add(column);
		
	    //Add change listener
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		manager.entityWasClicked(observableValue.getValue().getEntity());
        	}
        	);
        
		table.setItems(entityEntries);
	}
	
	private void fillEntries()
	{	
		for (ISerializable entity: entities)
		{
			entityEntries.add(new EntityEntry((Entity)entity));
		}
	}
	
	public TableView<EntityEntry> getTable()
	{
		return table;
	}
	
	
}
