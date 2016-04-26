package view.editor.eventeditor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import api.IEntity;
import api.ISerializable;
import javafx.collections.ObservableList;
import model.entity.Entity;


public class EntityTable extends Table
{
	private ArrayList<String> entityNames;

	public EntityTable(ObservableList<IEntity> entities, TableManager manager, String language)
	{
		super(manager, ResourceBundle.getBundle(language).getString("pickEntity"));	// TODO resource file


		// Add change listener
		getTable().
		getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
		{
			try
			{
				manager.entityWasClicked((Entity)observableValue.getValue().getData());
			} catch (Exception E)
			{
				// TODO bad bad bad
			}
		}
				);
		entityNames = new ArrayList<String>();

		fillEntries(entities);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fillEntries(Object dataHolder) 
	{
		for (ISerializable entity: (ObservableList<ISerializable>)dataHolder)
		{
			if ( entityNames.contains( ((Entity)entity).getName() ))
				continue;
			else
			{
				entityNames.add( ((Entity)entity).getName() );
				getEntries().add(new Entry(entity, ((Entity)entity).getName()));
			}
		}
	}

	public void levelWasPicked(ObservableList<ISerializable> newEntities)
	{
		refreshTable();
		entityNames.clear();
		if ( !newEntities.isEmpty() )
			fillEntries(newEntities);
	}
}
