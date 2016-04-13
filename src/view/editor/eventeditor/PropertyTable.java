package view.editor.eventeditor;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.Entity;

public class PropertyTable 
{
	private TableView<PropertyEntry> table;
	private TableColumn<PropertyEntry, String> column;
	private Entity entity;
	private ObservableList<PropertyEntry> entries;
	private final TableManager manager;
	
	public PropertyTable(TableManager manager)
	{
		this.manager = manager;
		table = new TableView<PropertyEntry>();
		table.setEditable(true);
		column = new TableColumn<PropertyEntry, String>("Pick Property");	// TODO resource file
		column.setCellValueFactory( new PropertyValueFactory<PropertyEntry,String>("name") );
		
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
	
	public void fillEntries(IComponent component)
	{	
		for (SimpleObjectProperty property: component.getProperties())
		{
			entries.add(new PropertyEntry(property));
		}
	}
	
	public TableView<PropertyEntry> getTable()
	{
		return table;
	}
}
