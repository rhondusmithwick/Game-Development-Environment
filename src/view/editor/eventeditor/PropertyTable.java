package view.editor.eventeditor;

import java.util.ResourceBundle;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.Entity;

public class PropertyTable extends Table
{
	public PropertyTable(TableManager manager, String language)
	{
		super(manager, ResourceBundle.getBundle(language).getString("pickProperty"));	// TODO resource file

		// Add change listener
		getTable().
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		manager.propertyWasClicked((SimpleObjectProperty)observableValue.getValue().getData());
        	}
        	);
   	}
	
	@Override
	public void fillEntries(Object dataHolder) 
	{
		for (SimpleObjectProperty property: ((IComponent)dataHolder).getProperties())
		{
			getEntries().add(new Entry(property, property.getName()));
		}
	}
}
