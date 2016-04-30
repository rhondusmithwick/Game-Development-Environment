package view.editor.eventeditor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

import api.IComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.enums.GUISize;

public abstract class Table 
{
	private TableView<Entry> table;
	private TableColumn<Entry, String> column;
	private ObservableList<Entry> entries;
	private TableManager manager;
	
	public Table(TableManager manager, String name, Method clickHandler, Class handlerArgumentClass)
	{
		this.manager = manager;
		table = new TableView<Entry>();
		table.setEditable(true);
		table.setPrefWidth(GUISize.EVENT_EDITOR_TABLE_WIDTH.getSize());
		table.setMaxHeight(250);	// TODO magic value
		
		table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
    	{
    		try
    		{
    			clickHandler.invoke(manager, handlerArgumentClass.cast(observableValue.getValue().getData()));
    		} catch (Exception e)
    		{
    			// TODO BAAAAAAD
    		}
    	}
    	);
		
		column = new TableColumn<Entry, String>(name);
		column.setCellValueFactory( new PropertyValueFactory<Entry,String>("name") );
		column.minWidthProperty().bind(table.prefWidthProperty());
		column.maxWidthProperty().bind(table.prefWidthProperty());
		column.setSortable(false);
		
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
