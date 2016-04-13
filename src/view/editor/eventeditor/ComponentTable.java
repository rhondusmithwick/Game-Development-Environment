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
	
	public ComponentTable()
	{
		table = new TableView<ComponentEntry>();
		table.setEditable(true);
		column = new TableColumn<ComponentEntry, String>("Pick Component");	// TODO resource file
		column.setCellValueFactory( new PropertyValueFactory<ComponentEntry,String>("name") );
		
		
		entries = FXCollections.observableArrayList();
		
		table.getColumns().add(column);
		
	    //Add change listener
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (table.getSelectionModel().getSelectedItem() != null) {
                System.out.println(table.getSelectionModel().getSelectedItem().getName());
            }
        });
        
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
