package view.editor.eventeditor;

import api.IComponent;
import api.ISerializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.Entity;

public class ComponentTable extends Table
{
	public ComponentTable(TableManager manager)
	{
		super(manager, "Pick Component");	// TODO resource file

		// Add change listener
		getTable().
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		manager.componentWasClicked((IComponent)observableValue.getValue().getData());
        	}
        	);
   	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void fillEntries(Object dataHolder) 
	{
		for (IComponent component: ((Entity)dataHolder).getAllComponents())
		{
			String[] splitClassName = component.getClass().toString().split("\\.");
			getEntries().add(new Entry(component, splitClassName[splitClassName.length - 1]));
		}
	}
}
