package view.editor.eventeditor;
import api.ISerializable;
import javafx.collections.ObservableList;
import model.entity.Entity;


public class EntityTable extends Table
{
	public EntityTable(ObservableList<ISerializable> entities, TableManager manager)
	{
		super(manager, "Pick Entity");	// TODO resource file
		
		// Add change listener
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
