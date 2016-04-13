package view.editor.eventeditor;

import api.IComponent;
import api.ISerializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.Entity;

public class ComponentTable 
{
	private TableView<ComponentEntry> table;
	private TableColumn<ComponentEntry, String> column;
	private Entity entity;
	private ObservableList<ComponentEntry> entries;
	TableManager manager;
	
	public ComponentTable(TableManager manager)
	{
		table = new TableView<ComponentEntry>();
		table.setEditable(true);
		column = new TableColumn<ComponentEntry, String>("Pick Component");	// TODO resource file
		column.setCellValueFactory( new PropertyValueFactory<ComponentEntry,String>("name") );
		this.manager = manager;
		
		entries = FXCollections.observableArrayList();
		
		table.getColumns().add(column);
		
		//Add change listener
		table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
		{
			manager.componentWasClicked(observableValue.getValue().getComponent());
		}
				);

		table.setItems(entries);
	}
	
	public void fillEntries(Entity entity)
	{	
		for (IComponent component: entity.getAllComponents())
		{
			entries.add(new ComponentEntry(component));
		}
	}
	
	public TableView<ComponentEntry> getTable()
	{
		return table;
	}
}
