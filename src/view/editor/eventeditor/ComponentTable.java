package view.editor.eventeditor;

import java.util.ResourceBundle;

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
	public ComponentTable(TableManager manager, String language)
	{
		super(manager, ResourceBundle.getBundle(language).getString("pickComponent"));	// TODO resource file

		// Add changeImage listener
		getTable().
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		manager.componentWasClicked((IComponent)observableValue.getValue().getData());
        	}
        	);
   	}
	
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
