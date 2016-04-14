package view.editor.eventeditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class Table 
{
	private TableView<Entry> table;
	private TableColumn<Entry, String> column;
	private ObservableList<Entry> entries;
	private TableManager manager;
	
	public Table(TableManager manager, String name)
	{
		this.manager = manager;
		table = new TableView<Entry>();
		table.setEditable(true);
		column = new TableColumn<Entry, String>(name);
		column.setCellValueFactory( new PropertyValueFactory<Entry,String>("name") );
		entries = FXCollections.observableArrayList();
		table.getColumns().add(column);
		table.setItems(entries);
	}
	
	public abstract void fillEntries(Object dataHolder);
	
	public TableManager getManager()
	{
		return manager;
	}
	
	public TableView<Entry> getTable()
	{
		return table;
	}
	
	public ObservableList<Entry> getEntries()
	{
		return entries;
	}
	
}
