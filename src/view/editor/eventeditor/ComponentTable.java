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
	public ComponentTable(PropertyTableManager manager, String language) throws NoSuchMethodException, SecurityException
	{
		super(manager, ResourceBundle.getBundle(language).getString("pickComponent"), 
				manager.getClass().getMethod("componentWasClicked", IComponent.class),
				IComponent.class);	

		/*
		// Add changeImage listener
		getTable().
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		try
        		{
        			manager.componentWasClicked((IComponent)observableValue.getValue().getData());
        		} catch (Exception e)
        		{
        			// TODO BAAAAAAD
        		}
        	}
        	);
        	*/
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
