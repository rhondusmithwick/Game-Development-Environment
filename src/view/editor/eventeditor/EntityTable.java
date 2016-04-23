package view.editor.eventeditor;
import java.util.ResourceBundle;

import api.ISerializable;
import javafx.collections.ObservableList;
import model.entity.Entity;


public class EntityTable extends Table
{
	
	public EntityTable(ObservableList<ISerializable> entities, TableManager manager, String language)
	{
		super(manager, ResourceBundle.getBundle(language).getString("pickEntity"));	// TODO resource file
		
		// Add changeImage listener
		getTable().
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		manager.entityWasClicked((Entity)observableValue.getValue().getData());
        	}
        	);
		
		fillEntries(entities);
   	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void fillEntries(Object dataHolder) 
	{
		for (ISerializable entity: (ObservableList<ISerializable>)dataHolder)
		{
			getEntries().add(new Entry(entity, ((Entity)entity).getName()));
		}
		
	
	}
	
	
}
