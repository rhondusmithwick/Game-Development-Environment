package view.editor.eventeditor;

import java.util.ResourceBundle;

import enums.GUISize;
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
		table.setPrefWidth(GUISize.EVENT_EDITOR_TABLE_WIDTH.getSize());
		
		column = new TableColumn<Entry, String>(name);
		column.setCellValueFactory( new PropertyValueFactory<Entry,String>("name") );
		column.minWidthProperty().bind(table.prefWidthProperty());
		column.maxWidthProperty().bind(table.prefWidthProperty());
		
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
	
	public void refreshTable()
	{
		entries.clear();
		table.refresh();
	}
}
